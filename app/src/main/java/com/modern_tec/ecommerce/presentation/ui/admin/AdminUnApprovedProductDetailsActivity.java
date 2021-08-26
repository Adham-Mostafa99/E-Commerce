package com.modern_tec.ecommerce.presentation.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.databinding.ActivityAdminProductBinding;
import com.modern_tec.ecommerce.databinding.ActivityAdmintUnApprovedProductDetailsBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;

public class AdminUnApprovedProductDetailsActivity extends AppCompatActivity {
    private ActivityAdmintUnApprovedProductDetailsBinding binding;
    private ProductViewModel productViewModel;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();

        product = (Product) getIntent().getSerializableExtra(AdminProductActivity.PRODUCT_EXTRA);

        if (product.getProductId() != null) {
            setProductDetails();
        }

        binding.adminApproveProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productViewModel.approveProduct(product.getProductId());
            }
        });


        productViewModel.getIsProductApproved().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(AdminUnApprovedProductDetailsActivity.this, "Product Approved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        binding.adminBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initViewModels() {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    }

    private void setProductDetails() {
        Glide.with(getApplicationContext())
                .load(product.getProductImageUrl())
                .placeholder(R.drawable.place_holder)
                .into(binding.adminProductImageDetails);

        binding.adminProductNameDetails.setText(product.getProductName());
        binding.adminProductDescDetails.setText(product.getProductDescription());
        binding.adminProductCategoryDetails.setText(product.getProductCategory());
        binding.adminProductPriceDetails.setText(product.getProductPrice() + " EG");

        binding.adminSellerNameDetails.setText(product.getSellerName());
        binding.adminSellerEmailDetails.setText(product.getSellerEmail());
        binding.adminSellerPhoneDetails.setText(product.getSellerPhone());
        binding.adminSellerAddressDetails.setText(product.getSellerAddress().getAddressName());
        binding.adminSellerAddressCity.setText(product.getSellerAddress().getCity());
        binding.adminSellerAddressPostalCode.setText(product.getSellerAddress().getPostalCode());
    }

    private void initBinding() {
        binding = ActivityAdmintUnApprovedProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}