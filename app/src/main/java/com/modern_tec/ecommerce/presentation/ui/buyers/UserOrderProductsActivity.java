package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.databinding.ActivityUserProductsBinding;
import com.modern_tec.ecommerce.presentation.adapters.CartAdapter;

import java.util.List;

public class UserOrderProductsActivity extends AppCompatActivity {
    ActivityUserProductsBinding binding;

    private CartAdapter cartAdapter;

    private List<CartProduct> cartProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();

        cartProductList = getIntent().getParcelableArrayListExtra(ShippedOrders.PRODUCT_LIST);

        if (cartProductList.size() > 0) {
            cartAdapter.submitList(cartProductList);
        }


    }

    private void initAdapter() {
        cartAdapter = new CartAdapter();
        binding.userOrderProductsRecycler.setHasFixedSize(true);
        binding.userOrderProductsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.userOrderProductsRecycler.setAdapter(cartAdapter);
    }

    private void initBinding() {
        binding = ActivityUserProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}