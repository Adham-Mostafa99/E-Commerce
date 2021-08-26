package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.databinding.ActivityFeaturiesBinding;
import com.modern_tec.ecommerce.presentation.adapters.ProductAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;

import java.util.List;

import static com.modern_tec.ecommerce.presentation.ui.buyers.HomeActivity.CLICKED_CATEGORY_EXTRA;
import static com.modern_tec.ecommerce.presentation.ui.buyers.HomeActivity.SEARCHED_PRODUCT_EXTRA;

public class FeaturesActivity extends AppCompatActivity {
    public static final String CLICKED_ITEM_EXTRA = "clicked";

    ActivityFeaturiesBinding binding;
    ProductAdapter adapter;
    ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();
        intiViewModels();

        String categoryName = getIntent().getStringExtra(CLICKED_CATEGORY_EXTRA);

        String productSearchedName = getIntent().getStringExtra(SEARCHED_PRODUCT_EXTRA);
        if (productSearchedName != null) {
            productViewModel.getProductByName(productSearchedName);
            openSearch(productSearchedName);
        }


        binding.featureSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch(null);
            }
        });

        binding.closeFeatureSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSearch();
            }
        });


        if (categoryName != null) {
            productViewModel.getProductsByCategory(categoryName).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    adapter.submitList(products);
                }
            });
        } else {
            productViewModel.getProducts();
            productViewModel.getProductLiveData().observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    adapter.submitList(products);
                }
            });
        }


        binding.featureBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void openSearch(String productName) {
        binding.featureSearchBtn.setVisibility(View.INVISIBLE);
        binding.closeFeatureSearchBtn.setVisibility(View.VISIBLE);
        binding.searchProductName.setVisibility(View.VISIBLE);

        if (productName!=null)
            binding.searchProductName.setText(productName);

        binding.searchProductName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                productViewModel.getProductByName(s.toString().trim());
            }
        });
    }

    private void closeSearch() {
        binding.featureSearchBtn.setVisibility(View.VISIBLE);
        binding.closeFeatureSearchBtn.setVisibility(View.INVISIBLE);
        binding.searchProductName.setVisibility(View.INVISIBLE);

        productViewModel.getProducts();

    }


    private void intiViewModels() {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    }

    private void initAdapter() {
        adapter = new ProductAdapter(this);
        binding.recyclerFeature.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerFeature.setAdapter(adapter);
        onClickProductItem();
    }

    private void initBinding() {
        binding = ActivityFeaturiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void onClickProductItem() {
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                startActivity(new Intent(FeaturesActivity.this, ProductDetailsActivity.class)
                        .putExtra(CLICKED_ITEM_EXTRA, product));
            }
        });
    }
}