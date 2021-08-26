package com.modern_tec.ecommerce.presentation.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.databinding.ActivityAdminProductBinding;
import com.modern_tec.ecommerce.presentation.adapters.ProductAdapter;
import com.modern_tec.ecommerce.presentation.ui.MainActivity;
import com.modern_tec.ecommerce.presentation.ui.seller.SellerMaintainProductActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import java.util.List;

public class AdminProductActivity extends AppCompatActivity {

    ActivityAdminProductBinding binding;
    ProductAdapter adapter;
    ProductViewModel productViewModel;
    UserViewModel userViewModel;
    public static final String PRODUCT_EXTRA = "product_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();
        initViewModels();


        binding.adminProductToolbar.setTitle("Admin Panel");
        setSupportActionBar(binding.adminProductToolbar);

        productViewModel.getUnApprovedProducts();

        productViewModel.getProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> product) {
                adapter.submitList(product);
                if (product.size() > 0)
                    binding.adminNoProduct.setVisibility(View.INVISIBLE);
                else
                    binding.adminNoProduct.setVisibility(View.VISIBLE);
            }
        });


    }

    private void initViewModels() {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initAdapter() {
        adapter = new ProductAdapter(this);
        binding.adminProductRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        binding.adminProductRecycler.setHasFixedSize(true);
        binding.adminProductRecycler.setAdapter(adapter);
        setOnClickItem();
    }


    private void initBinding() {
        binding = ActivityAdminProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.admin_logout) {
            userViewModel.logOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
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