package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.modern_tec.ecommerce.core.models.SellerUserOrders;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.databinding.ActivitySellerAllUsersOrdersBinding;
import com.modern_tec.ecommerce.presentation.adapters.AdminUserOrdersAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.OrderViewModel;

import java.util.ArrayList;
import java.util.List;


public class SellerAllUsersOrdersActivity extends AppCompatActivity {
    public static final String USER_ORDERS_EXTRA = "user orders";
    public static final String USER_ID_EXTRA = "userId";
    private ActivitySellerAllUsersOrdersBinding binding;
    private AdminUserOrdersAdapter adapter;
    private OrderViewModel orderViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();
        initViewModels();

        orderViewModel.getAdminUserOrders();

        orderViewModel.getAdminUserOrdersLiveData().observe(this, new Observer<List<SellerUserOrders>>() {
            @Override
            public void onChanged(List<SellerUserOrders> sellerUserOrders) {
                adapter.submitList(sellerUserOrders);
                if (sellerUserOrders.size() > 0)
                    binding.noOrders.setVisibility(View.GONE);
            }
        });
    }

    private void initViewModels() {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
    }

    private void initAdapter() {
        adapter = new AdminUserOrdersAdapter();
        binding.adminUserOrdersRecycler.setAdapter(adapter);
        binding.adminUserOrdersRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.adminUserOrdersRecycler.setHasFixedSize(true);
        setOnClickItem();

    }

    private void setOnClickItem() {
        adapter.setOnItemClickListener(new AdminUserOrdersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SellerUserOrders sellerUserOrders) {
                startActivity(new Intent(SellerAllUsersOrdersActivity.this, SellerUserOrderActivity.class)
                        .putParcelableArrayListExtra(USER_ORDERS_EXTRA, (ArrayList<Order>) sellerUserOrders.getOrderList())
                        .putExtra(USER_ID_EXTRA, sellerUserOrders.getUserId()));
            }
        });
    }

    private void initBinding() {
        binding = ActivitySellerAllUsersOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}