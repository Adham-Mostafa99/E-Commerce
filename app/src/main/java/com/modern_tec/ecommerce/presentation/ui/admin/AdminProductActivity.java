package com.modern_tec.ecommerce.presentation.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.databinding.ActivityAdminProductBinding;
import com.modern_tec.ecommerce.presentation.adapters.ProductAdapter;
import com.modern_tec.ecommerce.presentation.ui.seller.SellerMaintainProductActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;

import java.util.List;

public class AdminProductActivity extends AppCompatActivity {

    ActivityAdminProductBinding binding;
    ProductAdapter adapter;
    ProductViewModel productViewModel;
    public static final String PRODUCT_EXTRA = "product_extra";

    //TODO add logout button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();
        initViewModels();

        productViewModel.getUnApprovedProducts();

        productViewModel.getProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> product) {
                adapter.submitList(product);
            }
        });


    }

    private void initViewModels() {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    }

    private void initAdapter() {
        adapter = new ProductAdapter(this);
        binding.adminProductRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.adminProductRecycler.setHasFixedSize(true);
        binding.adminProductRecycler.setAdapter(adapter);
        setOnClickItem();
    }


    private void initBinding() {
        binding = ActivityAdminProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


    private void setOnClickItem() {
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                startActivity(new Intent(AdminProductActivity.this, AdminUnApprovedProductDetailsActivity.class)
                        .putExtra(PRODUCT_EXTRA, product));
            }
        });
    }
}