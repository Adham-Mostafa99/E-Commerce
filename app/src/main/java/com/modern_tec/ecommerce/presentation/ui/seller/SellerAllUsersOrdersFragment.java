package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.modern_tec.ecommerce.core.models.SellerUserOrders;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.databinding.FragmentSellerAllUsersOrdersBinding;
import com.modern_tec.ecommerce.presentation.adapters.AdminUserOrdersAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.OrderViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class SellerAllUsersOrdersFragment extends Fragment {
    public static final String USER_ORDERS_EXTRA = "user orders";
    public static final String USER_ID_EXTRA = "userId";
    private FragmentSellerAllUsersOrdersBinding binding;
    private AdminUserOrdersAdapter adapter;
    private OrderViewModel orderViewModel;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        binding = FragmentSellerAllUsersOrdersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        initAdapter();
        initViewModels();

        orderViewModel.getAdminUserOrders();

        orderViewModel.getAdminUserOrdersLiveData().observe(getViewLifecycleOwner(), new Observer<List<SellerUserOrders>>() {
            @Override
            public void onChanged(List<SellerUserOrders> sellerUserOrders) {
                adapter.submitList(sellerUserOrders);
                if (sellerUserOrders.size() > 0)
                    binding.noOrders.setVisibility(View.GONE);
            }
        });

        return view;
    }



    private void initViewModels() {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
    }

    private void initAdapter() {
        adapter = new AdminUserOrdersAdapter();
        binding.adminUserOrdersRecycler.setAdapter(adapter);
        binding.adminUserOrdersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.adminUserOrdersRecycler.setHasFixedSize(true);
        setOnClickItem();

    }

    private void setOnClickItem() {
        adapter.setOnItemClickListener(new AdminUserOrdersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SellerUserOrders sellerUserOrders) {
                startActivity(new Intent(getContext(), SellerUserOrderActivity.class)
                        .putParcelableArrayListExtra(USER_ORDERS_EXTRA, (ArrayList<Order>) sellerUserOrders.getOrderList())
                        .putExtra(USER_ID_EXTRA, sellerUserOrders.getUserId()));
            }
        });
    }


}