package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.modern_tec.ecommerce.databinding.ActivityOrdersBinding;
import com.modern_tec.ecommerce.presentation.adapters.OrderFragmentAdapter;

public class OrdersActivity extends AppCompatActivity {
    private ActivityOrdersBinding binding;
    private OrderFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initFragmentAdapter();
    }

    private void initFragmentAdapter() {
        adapter = new OrderFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new UnShippedOrders(), "Orders");
        adapter.addFragment(new ShippedOrders(), "Shipped Orders");


        binding.container.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.container);
    }

    private void initBinding() {
        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}