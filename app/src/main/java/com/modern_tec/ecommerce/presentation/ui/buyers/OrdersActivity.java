package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.modern_tec.ecommerce.databinding.ActivityOrdersBinding;
import com.modern_tec.ecommerce.presentation.adapters.OrderFragmentAdapter;

import org.jetbrains.annotations.NotNull;

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