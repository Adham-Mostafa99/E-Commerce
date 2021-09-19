package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.date.DateInfo;
import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.databinding.ActivityProductDetailsBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.CartViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ActivityProductDetailsBinding binding;
    private ProductViewModel productViewModel;
    private UserViewModel userViewModel;
    private CartViewModel cartViewModel;

    private String typeOfAction = "show details";
    private Product product;
    private CartProduct cartProduct;
    private int quantity = 1;
    private double discount = 0;
    double total = 0;

    private boolean isBuyNow;

    //TODO add more details about product like seller name...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();

        product = (Product) getIntent().getSerializableExtra(HomeActivity.CLICKED_ITEM_EXTRA);
        if (product != null) {
            displayProductInfo();
        }

        binding.productDetailsBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.addProductToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBuyNow = false;
                addProductToCart(product);
            }
        });

        binding.buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBuyNow = true;
                addProductToCart(product);
            }
        });

        cartViewModel.getIsProductAddedToCart().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (isBuyNow) {
                    startActivity(new Intent(ProductDetailsActivity.this, CartActivity.class));
                    finish();
                } else {
                    onBackPressed();
                }

            }
        });


        binding.productDetailsFavBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                storeProductAsFavorite();

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteProductFromFavorite();

            }
        });


        userViewModel.getIsProductFav(product.getProductId()).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    binding.productDetailsFavBtn.setLiked(true);
            }
        });

    }

    private void deleteProductFromFavorite() {
        userViewModel.removeProductFromFavorite(product.getProductId()).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    Toast.makeText(ProductDetailsActivity.this, "Removed from Favorite List", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeProductAsFavorite() {
        userViewModel.storeProductOnFavorite(product.getProductId()).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    Toast.makeText(ProductDetailsActivity.this, "Added to Favorite List", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProductInfo() {
        if (product.getProductId() != null) {
            Glide.with(getApplicationContext())
                    .load(product.getProductImageUrl())
                    .placeholder(R.drawable.place_holder)
                    .into(binding.productImageDetails);

            binding.productNameDetails.setText(product.getProductName());
            binding.productDescDetails.setText(product.getProductDescription());
            binding.productPriceDetails.setText(product.getProductPrice() + " EG");
            binding.productSellerDetails.setText(product.getSellerName());
//            binding.productNumberIncreaseBtn.setNumber(quantity + "");

            binding.productDescDetails.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    private void addProductToCart(Product product) {

        DateInfo dateInfo = new DateInfo();

        CartProduct cartProduct = new CartProduct(product.getProductId(), product.getProductName()
                , product.getProductImageUrl(), product.getSellerName(), product.getProductPrice()
                , dateInfo.getDate(), dateInfo.getTime(),
                quantity, discount, total);

        cartViewModel.addProductToCart(cartProduct);


    }

    private void initBinding() {
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initViewModels() {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

    }
}