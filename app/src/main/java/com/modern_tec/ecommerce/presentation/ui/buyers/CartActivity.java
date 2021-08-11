package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.databinding.ActivityCartBinding;
import com.modern_tec.ecommerce.presentation.adapters.CartAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.CartViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    public static final String EDIT_CART_ITEM_EXTRA = "edit item";
    public static final String TOTAL_PRICE_EXTRA = "total price";
    public static final String CART_LIST_PRICE_EXTRA = "cart list";
    private ActivityCartBinding binding;
    private CartViewModel cartViewModel;
    private CartAdapter cartAdapter;
    private AlertDialog.Builder builder;
    private double totalPrice = 0;
    private ArrayList<CartProduct> userProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();
        initAdapter();
        initViews();

        cartViewModel.getUserProducts();
        cartViewModel.getCartUserProducts().observe(this, new Observer<ArrayList<CartProduct>>() {
            @Override
            public void onChanged(ArrayList<CartProduct> cartProducts) {
                if (cartProducts.size() > 0) {
                    binding.emptyCart.setVisibility(View.GONE);
                }
                userProducts = cartProducts;
                cartAdapter.submitList(cartProducts);
                for (CartProduct cartProduct : cartProducts) {
                    totalPrice = totalPrice + cartProduct.getProductTotalPrice();
                }
                binding.cartTotalPrice.setText(totalPrice + " EG");
            }
        });

        cartViewModel.getIsProductDeletedFromCart().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(CartActivity.this, "Product is deleted", Toast.LENGTH_SHORT).show();
            }
        });

        binding.cartNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userProducts.size() > 0) {
                    startActivity(new Intent(CartActivity.this, ConfirmFinalOrderActivity.class)
                            .putExtra(TOTAL_PRICE_EXTRA, totalPrice)
                            .putParcelableArrayListExtra(CART_LIST_PRICE_EXTRA, userProducts));
                } else {
                    Toast.makeText(CartActivity.this, "Cart is empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        builder = new AlertDialog.Builder(this);
    }

    private void initBinding() {
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initViewModels() {
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
    }

    private void initAdapter() {
        cartAdapter = new CartAdapter();
        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecycler.setHasFixedSize(true);
        binding.cartRecycler.setAdapter(cartAdapter);
        onClickOnItem();
    }

    private void onClickOnItem() {
        cartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CartProduct cartProduct) {
                openAlertDialog("Cart Options", cartProduct);
            }
        });
    }

    private void openAlertDialog(String title, CartProduct cartProduct) {
        CharSequence options[] = new CharSequence[]{
                "Edit",
                "Remove"
        };
        builder.setTitle(title);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0://Edit
                        startActivity(new Intent(CartActivity.this, ProductDetailsActivity.class)
                                .putExtra(EDIT_CART_ITEM_EXTRA, (Serializable) cartProduct));
                        break;
                    case 1://Remove
                        cartViewModel.deleteProductFromCart(cartProduct.getProductId());
                        break;
                    default:
                        break;
                }
            }
        });
        builder.show();
    }
}