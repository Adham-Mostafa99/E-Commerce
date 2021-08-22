package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.databinding.ActivitySellerUserProductsBinding;
import com.modern_tec.ecommerce.presentation.adapters.CartAdapter;
import com.modern_tec.ecommerce.presentation.adapters.OrderProductsAdapter;

import java.util.List;

public class SellerUserProductsActivity extends AppCompatActivity {
    private ActivitySellerUserProductsBinding binding;
    private OrderProductsAdapter orderProductsAdapter;

    private List<CartProduct> cartProductList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();

        binding.userProductBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cartProductList = getIntent().getParcelableArrayListExtra(SellerUserOrderActivity.PRODUCT_LIST);

        if (cartProductList.size() > 0) {
            orderProductsAdapter.submitList(cartProductList);
        }
    }

    private void initAdapter() {
        orderProductsAdapter = new OrderProductsAdapter();
        binding.userOrderProductsRecycler.setHasFixedSize(true);
        binding.userOrderProductsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.userOrderProductsRecycler.setAdapter(orderProductsAdapter);
    }

    private void initBinding() {
        binding = ActivitySellerUserProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}