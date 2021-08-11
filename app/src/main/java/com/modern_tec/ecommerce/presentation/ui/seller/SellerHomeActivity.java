package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.databinding.ActivitySellerHomeBinding;
import com.modern_tec.ecommerce.presentation.adapters.SellerProductAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import java.util.List;

public class SellerHomeActivity extends AppCompatActivity {
    public static final String PRODUCT_EXTRA = "product_extra";

    ActivitySellerHomeBinding binding;
    SellerProductAdapter adapter;
    ProductViewModel productViewModel;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();
        initAdapter();

        productViewModel.getSellerProducts(userViewModel.getUserId());

        productViewModel.getProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.submitList(products);
            }
        });
    }

    private void initViewModels() {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initAdapter() {
        adapter = new SellerProductAdapter(this);
        binding.sellerProductRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.sellerProductRecycler.setHasFixedSize(true);
        binding.sellerProductRecycler.setAdapter(adapter);
        setOnClickItem();
    }

    private void setOnClickItem() {
        adapter.setOnItemClickListener(new SellerProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                startActivity(new Intent(SellerHomeActivity.this, SellerMaintainProductActivity.class)
                        .putExtra(PRODUCT_EXTRA, product));
            }
        });
    }

    private void initBinding() {
        binding = ActivitySellerHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}