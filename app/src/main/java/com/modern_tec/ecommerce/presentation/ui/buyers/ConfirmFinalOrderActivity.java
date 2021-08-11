package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.modern_tec.ecommerce.core.date.DateInfo;
import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.databinding.ActivityConfirmFinalOrderBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.CartViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.OrderViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import java.util.ArrayList;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private ActivityConfirmFinalOrderBinding binding;
    private double totalPrice = 0;
    private ArrayList<CartProduct> cartProductList;
    private UserViewModel userViewModel;
    private OrderViewModel orderViewModel;
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initVIewModels();

        totalPrice = getIntent().getDoubleExtra(CartActivity.TOTAL_PRICE_EXTRA, 0);
        cartProductList = getIntent().getParcelableArrayListExtra(CartActivity.CART_LIST_PRICE_EXTRA);


        userViewModel.getUserInfo();
        userViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.confirmEditName.setText(user.getName());
                binding.confirmEditEmail.setText(user.getEmail());
                binding.confirmEditEmail.setEnabled(false);
                binding.confirmEditAddress.setText(user.getAddress());
            }
        });

        orderViewModel.getIsOrderAdded().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(ConfirmFinalOrderActivity.this, "Order is created successfully.", Toast.LENGTH_SHORT).show();
                deleteCartContent();
            }
        });

        cartViewModel.getIsAllProductDeletedFromCart().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                startActivity(new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class));
                finish();
            }
        });

        binding.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateInfo dateInfo = new DateInfo();
                String id = dateInfo.getDate() + dateInfo.getTime();
                String name = binding.confirmEditName.getText().toString().trim();
                String email = binding.confirmEditEmail.getText().toString().trim();
                String address = binding.confirmEditAddress.getText().toString().trim();
                String city = binding.confirmEditCity.getText().toString().trim();
                String phone = binding.confirmEditPhone.getText().toString().trim();
                Order order = new Order(id, cartProductList, name, email, address, city, phone
                        , dateInfo.getTime(), dateInfo.getDate(), totalPrice, "not shipped");

                if (isValid(order)) {
                    confirmOrder(order);
                }
            }
        });
    }

    private void deleteCartContent() {
        cartViewModel.deleteAllProducts();
    }

    private void confirmOrder(Order order) {
        orderViewModel.createOrder(order);
    }

    private void initVIewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
    }

    private void initBinding() {
        binding = ActivityConfirmFinalOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private boolean isValid(Order order) {
        if (order.getUserName().isEmpty()) {
            Toast.makeText(this, "write your name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (order.getUserEmail().isEmpty()) {
            Toast.makeText(this, "write your email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (order.getUserAddress().isEmpty()) {
            Toast.makeText(this, "write your address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (order.getUserCity().isEmpty()) {
            Toast.makeText(this, "write your city", Toast.LENGTH_SHORT).show();
            return false;
        } else if (order.getUserPhone().isEmpty()) {
            Toast.makeText(this, "write your phone", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}