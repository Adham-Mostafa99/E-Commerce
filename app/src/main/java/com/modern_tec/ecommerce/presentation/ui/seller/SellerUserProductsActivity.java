package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.databinding.ActivitySellerUserProductsBinding;
import com.modern_tec.ecommerce.presentation.adapters.CartAdapter;

import java.util.List;

public class SellerUserProductsActivity extends AppCompatActivity {
    private ActivitySellerUserProductsBinding binding;
    private CartAdapter cartAdapter;

    private List<CartProduct> cartProductList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();

        cartProductList = getIntent().getParcelableArrayListExtra(SellerUserOrderActivity.PRODUCT_LIST);

        if (cartProductList.size() > 0) {
            cartAdapter.submitList(cartProductList);
        }
    }

    private void initAdapter() {
        cartAdapter = new CartAdapter();
        binding.adminUserProductsRecycler.setHasFixedSize(true);
        binding.adminUserProductsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.adminUserProductsRecycler.setAdapter(cartAdapter);
    }

    private void initBinding() {
        binding = ActivitySellerUserProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}