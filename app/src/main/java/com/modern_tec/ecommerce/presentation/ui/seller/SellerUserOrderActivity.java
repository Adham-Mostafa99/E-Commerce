package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.databinding.ActivitySellerUserOrderBinding;
import com.modern_tec.ecommerce.presentation.adapters.OrderAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class SellerUserOrderActivity extends AppCompatActivity {

    private ActivitySellerUserOrderBinding binding;
    public static final String PRODUCT_LIST = "product list";
    private OrderAdapter orderAdapter;
    private AlertDialog.Builder builder;
    private OrderViewModel orderViewModel;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();
        initViewModels();
        initViews();

        userId = getIntent().getStringExtra(SellerAllUsersOrdersActivity.USER_ID_EXTRA);

        if (userId != null) {
            Log.v("TAG", "id:" + userId);
            orderViewModel.getAdminUserOrdersById(userId);
        }

        orderViewModel.getUserOrderLiveData().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                Log.v("TAG", orders.size() + "");
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
        orderAdapter = new OrderAdapter();
        binding.newOrderRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.newOrderRecycler.setHasFixedSize(true);
        binding.newOrderRecycler.setAdapter(orderAdapter);
        onCLickItem();
        onOrderClick();
    }

    private void onCLickItem() {
        orderAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                startActivity(new Intent(SellerUserOrderActivity.this, SellerUserProductsActivity.class)
                        .putParcelableArrayListExtra(PRODUCT_LIST, (ArrayList<CartProduct>) order.getCartProductList()));
            }
        });

    }

    private void onOrderClick() {
        orderAdapter.setOnOrderClickListener(new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Order order) {
                openAlertDialog("Have you shipped this order?", order);
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

    private void openAlertDialog(String title, Order order) {
        CharSequence options[] = new CharSequence[]{
                "Yes",
                "No"
        };
        builder.setTitle(title);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0://YES
                        orderViewModel.makeOrderShipped(userId, order.getOrderId());
                        break;
                    case 1://NO
                    default:
                        break;
                }
            }
        });
        builder.show();
    }

    private void initViews() {
        builder = new AlertDialog.Builder(this);
    }
}