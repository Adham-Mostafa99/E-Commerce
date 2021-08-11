package com.modern_tec.ecommerce.presentation.ui.buyers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.presentation.adapters.OrderAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.OrderViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UnShippedOrders extends Fragment {
    public static final String PRODUCT_LIST = "product list";

    RecyclerView recyclerView;
    OrderAdapter adapter;
    OrderViewModel orderViewModel;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_fragment, container, false);
        initView(view);
        initViewModels();
        initAdapter();
        orderViewModel.getUserOrders();


        orderViewModel.getUserUnShippedOrderLiveData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                adapter.submitList(orders);
            }
        });
        return view;
    }

    private void initAdapter() {
        adapter = new OrderAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        setOnItemClick();
    }

    private void initViewModels() {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
    }

    private void setOnItemClick() {
        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Order order) {
                        startActivity(new Intent(getActivity(), UserOrderProductsActivity.class)
                                .putParcelableArrayListExtra(PRODUCT_LIST, (ArrayList<CartProduct>) order.getCartProductList()));
                    }
                });
            }
        });
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.order_fragment_recycler);
    }


}
