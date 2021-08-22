package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.databinding.ActivitySellerUserOrderBinding;
import com.modern_tec.ecommerce.presentation.adapters.OrderAdapter;
import com.modern_tec.ecommerce.presentation.adapters.SellerOrdersAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.OrderViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class SellerUserOrderActivity extends AppCompatActivity {

    private ActivitySellerUserOrderBinding binding;
    public static final String PRODUCT_LIST = "product list";
    private SellerOrdersAdapter orderAdapter;
    private OrderViewModel orderViewModel;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();
        initViewModels();

        binding.orderBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userId = getIntent().getStringExtra(SellerAllUsersOrdersFragment.USER_ID_EXTRA);


        if (userId != null) {
            orderViewModel.getAdminUserOrdersById(userId);
        }


        orderViewModel.getUserOrderLiveData().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                orderAdapter.submitList(orders);
            }
        });

        orderViewModel.getIsOrderChanged().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(SellerUserOrderActivity.this, "Order is shipped.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter() {
        orderAdapter = new SellerOrdersAdapter();
        binding.sellerUserOrderRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.sellerUserOrderRecycler.setHasFixedSize(true);
        binding.sellerUserOrderRecycler.setAdapter(orderAdapter);
        onCLickItem();
        onOrderClick();
    }

    private void onCLickItem() {
        orderAdapter.setOnItemClickListener(new SellerOrdersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                orderViewModel.makeOrderShipped(userId, order.getOrderId());

            }
        });

    }

    private void onOrderClick() {
        orderAdapter.setOnOrderClickListener(new SellerOrdersAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Order order) {
                startActivity(new Intent(SellerUserOrderActivity.this, SellerUserProductsActivity.class)
                        .putParcelableArrayListExtra(PRODUCT_LIST, (ArrayList<CartProduct>) order.getCartProductList()));
            }
        });
    }


    private void initBinding() {
        binding = ActivitySellerUserOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initViewModels() {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
    }


}