package com.modern_tec.ecommerce.presentation.ui.buyers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.date.DateInfo;
import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.presentation.adapters.OrderAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.OrderViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShippedOrders extends Fragment {
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

        view.findViewById(R.id.order_back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        orderViewModel.getUserShippedOrders();

        orderViewModel.getUserShippedOrderLiveData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                    adapter.submitList(orders);
                    recyclerView.getLayoutManager().scrollToPosition(0);

            }
        });


        return view;
    }

    private void initViewModels() {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
    }


    private void initAdapter() {
        adapter = new OrderAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        setOnItemClick();
        setOnOrderClick();
    }

    private void setOnOrderClick() {
        adapter.setOnOrderClickListener(new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Order order) {
                startActivity(new Intent(getActivity(), UserOrderProductsActivity.class)
                        .putParcelableArrayListExtra(PRODUCT_LIST, (ArrayList<CartProduct>) order.getCartProductList()));

            }
        });
    }

    private void setOnItemClick() {
        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {

                DateInfo dateInfo = new DateInfo();
                String id = dateInfo.getDate() + dateInfo.getTime();

                order.setOrderId(id);
                order.setOrderDate(dateInfo.getDate());
                order.setOrderTime(dateInfo.getTime());
                order.setState("not shipped");
                orderViewModel.createOrder(order);


            }
        });
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.order_fragment_recycler);
    }
}
