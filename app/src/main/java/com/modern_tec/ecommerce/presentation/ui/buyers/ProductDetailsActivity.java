package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.date.DateInfo;
import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.databinding.ActivityProductDetailsBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.CartViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;

public class ProductDetailsActivity extends AppCompatActivity {

    private ActivityProductDetailsBinding binding;
    private ProductViewModel productViewModel;
    private CartViewModel cartViewModel;

    private String typeOfAction = "show details";
    private Product product;
    private CartProduct cartProduct;
    private int quantity = 0;
    private double discount = 0;
    double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();

        product = (Product) getIntent().getSerializableExtra(HomeActivity.CLICKED_ITEM_EXTRA);
        if (product!= null) {
            displayProductInfo();
        } else {
            cartProduct = (CartProduct) getIntent().getSerializableExtra(CartActivity.EDIT_CART_ITEM_EXTRA);

            productViewModel.getProductById(cartProduct.getProductId());
            productViewModel.getSingleProductLiveData().observe(this, new Observer<Product>() {
                @Override
                public void onChanged(Product p) {
                    product = p;
                    quantity = cartProduct.getProductQuantity();
                    total = (product.getProductPrice() * quantity) - (product.getProductPrice() * discount);
                    binding.productTotalPriceDetails.setText(total + " EG");
                    typeOfAction = "edit product";
                    displayProductInfo();
                }
            });

        }
        binding.attProductToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0)
                    addProductToCart(product);
                else
                    Toast.makeText(ProductDetailsActivity.this, "Choose the quantity", Toast.LENGTH_SHORT).show();
            }
        });

        cartViewModel.getIsProductAddedToCart().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (typeOfAction.equals("edit product")) {
                    startActivity(new Intent(ProductDetailsActivity.this, CartActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(ProductDetailsActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });

        binding.productNumberIncreaseBtn.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                quantity = newValue;
                total = (product.getProductPrice() * quantity) - (product.getProductPrice() * discount);
                binding.productTotalPriceDetails.setText(total + " EG");
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
            binding.productNumberIncreaseBtn.setNumber(quantity + "");
        }
    }

    private void addProductToCart(Product product) {

        DateInfo dateInfo = new DateInfo();

        CartProduct cartProduct = new CartProduct(product.getProductId(), product.getProductName()
                , product.getProductPrice(), dateInfo.getDate(), dateInfo.getTime(),
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
    }
}