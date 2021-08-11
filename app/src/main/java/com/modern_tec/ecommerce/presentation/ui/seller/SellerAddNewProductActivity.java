package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.date.DateInfo;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.core.models.Seller;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

public class SellerAddNewProductActivity extends AppCompatActivity {

    private static final int GALLERY_INTENT = 1;

    private String categoryName;
    private ImageView productImage;
    private ImageView chooseImageBtn;
    private EditText inputProductName;
    private EditText inputProductDesc;
    private EditText inputProductPrice;
    private Button addNewProductBtn;

    private String imageUrl;

    private ProductViewModel productViewModel;
    private UserViewModel userViewModel;

    private ProgressDialog progressDialog;
    private Seller seller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_new_product);

        initViews();
        initViewModels();

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
                finish();
                startActivity(new Intent(SellerAddNewProductActivity.this, SellerCategoryActivity.class));
            }
        });

        categoryName = getIntent().getExtras().get(SellerCategoryActivity.CATEGORY_EXTRA).toString();
        if (!categoryName.isEmpty())
            setProductImage(categoryName);


        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addNewProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = inputProductName.getText().toString().trim();
                String productDesc = inputProductDesc.getText().toString().trim();
                String productPrice = inputProductPrice.getText().toString().trim();

                if (seller != null) {
                    Product product = new Product(null, imageUrl
                            , productName
                            , productDesc
                            , Double.parseDouble(productPrice)
                            , null
                            , null
                            , categoryName
                            , seller.getName()
                            , seller.getAddress()
                            , seller.getPhone()
                            , seller.getEmail()
                            , userViewModel.getUserId()
                            , "Not Approved");

                    addNewProduct(product);
                }
            }
        });
    }

    private void initViewModels() {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

    }

    private void initViews() {
        productImage = findViewById(R.id.selected_product_image);
        chooseImageBtn = findViewById(R.id.choose_product_image);
        inputProductName = findViewById(R.id.product_name);
        inputProductDesc = findViewById(R.id.product_desc);
        inputProductPrice = findViewById(R.id.product_price);
        addNewProductBtn = findViewById(R.id.add_new_product);
        progressDialog = new ProgressDialog(this);

    }

    private void setProductImage(String category) {
        switch (category) {
            case "t_shirts":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tshirts));
                break;
            case "sports_t_shirts":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sports));
                break;
            case "female_dresses":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.female_dresses));
                break;
            case "sweather":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sweather));
                break;
            case "glasses":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.glasses));
                break;
            case "purses_bags":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.purses_bags));
                break;
            case "hats":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.hats));
                break;
            case "shoess":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.shoess));
                break;
            case "headphoness":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.headphoness));
                break;
            case "laptops":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.laptops));
                break;
            case "watches":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.watches));
                break;
            case "mobiles":
                productImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.mobiles));
                break;
            default:
                break;
        }
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
            productImage.setImageURI(data.getData());
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