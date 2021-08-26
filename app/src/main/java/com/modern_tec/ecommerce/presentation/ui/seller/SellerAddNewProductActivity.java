package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.date.DateInfo;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.core.models.Seller;
import com.modern_tec.ecommerce.databinding.ActivitySellerAddNewProductBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

public class SellerAddNewProductActivity extends AppCompatActivity {

    private static final int GALLERY_INTENT = 1;

    ActivitySellerAddNewProductBinding binding;
    private ProductViewModel productViewModel;
    private UserViewModel userViewModel;

    private String categoryName;
    private String imageUrl;
    private String state = "Not Approved";


    private ProgressDialog progressDialog;
    private Seller seller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViews();
        initViewModels();
        EditText f;


        categoryName = getIntent().getExtras().get(SellerCategoryFragment.CATEGORY_EXTRA).toString();


        userViewModel.getSellerInfo();

        userViewModel.getSellerLiveData().observe(this, new Observer<Seller>() {
            @Override
            public void onChanged(Seller s) {
                seller = s;
            }
        });

        productViewModel.getIsProductUpload().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                progressDialog.dismiss();
                Toast.makeText(SellerAddNewProductActivity.this, "Successfully...", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });


        binding.chooseAddProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        binding.itemAddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateInfo dateInfo = new DateInfo();

                String productId = dateInfo.getDate() + dateInfo.getTime();
                String productName = binding.itemAddProductName.getText().toString().trim();
                String productDesc = binding.itemAddProductDesc.getText().toString().trim();
                String productPrice = binding.itemProductPrice.getText().toString().trim();

                if (seller != null) {
                    Product product = new Product(productId
                            , imageUrl
                            , productName
                            , productDesc
                            , Double.parseDouble(productPrice)
                            , dateInfo.getDate()
                            , dateInfo.getTime()
                            , categoryName
                            , seller.getName()
                            , seller.getAddress().get(0)
                            , seller.getPhone()
                            , seller.getEmail()
                            , userViewModel.getUserId()
                            , state);

                    addNewProduct(product);
                }
            }
        });
    }

    private void initBinding() {
        binding = ActivitySellerAddNewProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initViewModels() {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

    }

    private void initViews() {
        progressDialog = new ProgressDialog(this);

    }


    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null) {
            imageUrl = data.getData().toString();
            binding.addProductImage.setImageURI(data.getData());
        }
    }

    private void addNewProduct(Product product) {
        if (isProductValid(product)) {

            showProgress("Add new Product");
            DateInfo dateInfo = new DateInfo();
            String pId = dateInfo.getDate() + dateInfo.getTime();

            product.setProductId(pId);
            product.setProductDate(dateInfo.getDate());
            product.setProductTime(dateInfo.getTime());

            productViewModel.storeProductInformation(product, true);

        }
    }

    private boolean isProductValid(Product product) {
        if (product.getProductImageUrl() == null) {
            Toast.makeText(this, "Please choose product photo...", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(product.getProductName())) {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(product.getProductDescription())) {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
            return false;
        } else if (product.getProductPrice() == 0) {
            Toast.makeText(this, "Please write product price...", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    private void showProgress(String title) {
        progressDialog.setTitle(title);
        progressDialog.setMessage("Please wait, while upload the product.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

}