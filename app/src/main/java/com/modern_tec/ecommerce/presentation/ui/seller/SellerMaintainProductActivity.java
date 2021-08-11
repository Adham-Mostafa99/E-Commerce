package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.databinding.ActivitySellerMantainProductBinding;
import com.modern_tec.ecommerce.presentation.ui.admin.AdminProductActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;

public class SellerMaintainProductActivity extends AppCompatActivity {
    private static final int GALLERY_INTENT = 1;
    ActivitySellerMantainProductBinding binding;
    Product product;
    private String imageUrl = "";
    private ProductViewModel productViewModel;
    private boolean isPhotoUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();

        product = (Product) getIntent().getSerializableExtra(AdminProductActivity.PRODUCT_EXTRA);

        if (product.getProductId() != null) {
            setProductInfo();
        }

        binding.itemMiantainProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.itemMiantainProductName.getText().toString().trim();
                String desc = binding.itemMiantainProductDesc.getText().toString().trim();
                double price = Double.parseDouble(binding.itemProductPrice.getText().toString().trim());
                if (isValid(name, desc, price)) {
                    product.setProductName(name);
                    product.setProductPrice(price);
                    product.setProductDescription(desc);
                    productViewModel.storeProductInformation(product, isPhotoUpdated);
                }
            }
        });

        binding.chooseMaintainProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        binding.itemMiantainDeleteProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productViewModel.deleteProductById(product.getProductId());
            }
        });


        productViewModel.getIsProductUpload().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(SellerMaintainProductActivity.this, "Product is updated successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        productViewModel.getIsProductDeleted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(SellerMaintainProductActivity.this, "Product deleted successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void initViewModels() {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    }


    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_INTENT);
    }

    private boolean isValid(String name, String desc, double price) {

        if (name.isEmpty()) {
            Toast.makeText(this, "write product name.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (desc.isEmpty()) {
            Toast.makeText(this, "write product description.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!(price > 0)) {
            Toast.makeText(this, "write product price.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setProductInfo() {
        binding.itemMiantainProductName.setText(product.getProductName());
        binding.itemMiantainProductDesc.setText(product.getProductDescription());
        binding.itemProductPrice.setText(product.getProductPrice() + "");
        Glide.with(this)
                .load(product.getProductImageUrl())
                .placeholder(R.drawable.place_holder)
                .into(binding.itemMaintainProductImage);
    }

    private void initBinding() {
        binding = ActivitySellerMantainProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null) {
            imageUrl = data.getData().toString();
            product.setProductImageUrl(imageUrl);
            isPhotoUpdated = true;
            binding.itemMaintainProductImage.setImageURI(data.getData());
        }
    }

}