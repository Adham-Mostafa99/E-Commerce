package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.databinding.ActivitySearchProductBinding;
import com.modern_tec.ecommerce.presentation.adapters.ProductAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;

import java.util.List;

import static com.modern_tec.ecommerce.presentation.ui.buyers.HomeActivity.CLICKED_ITEM_EXTRA;

public class SearchProductActivity extends AppCompatActivity {
    private ActivitySearchProductBinding binding;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();
        initViewModels();

        productViewModel.getProducts();
        productViewModel.getProductLiveData().observe(this, new Observer<List<Product> >() {
            @Override
            public void onChanged(List<Product> product) {
                productAdapter.submitList(product);
            }
        });


        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = binding.searchEdit.getText().toString().trim();
                if (isValid(productName)) {
                    searchProducts(productName);
                }
            }
        });


    }

    private void searchProducts(String name) {
        productViewModel.getProductByName(name);
    }

    private boolean isValid(String productName) {
        if (productName.isEmpty()) {
            Toast.makeText(this, "Please write product name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void onClickProductItem() {
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                startActivity(new Intent(SearchProductActivity.this, ProductDetailsActivity.class)
                        .putExtra(CLICKED_ITEM_EXTRA, product));
            }
        });
    }

    private void initBinding() {
        binding = ActivitySearchProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initAdapter() {
        productAdapter = new ProductAdapter(this);
        binding.searchRecycler.setAdapter(productAdapter);
        binding.searchRecycler.setHasFixedSize(true);
        binding.searchRecycler.setLayoutManager(new LinearLayoutManager(this));
        onClickProductItem();
    }

    private void initViewModels() {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

    }
}