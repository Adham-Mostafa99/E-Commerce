package com.modern_tec.ecommerce.presentation.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.data.database.CartServices;

import java.util.ArrayList;

public class CartRepo {
    CartServices cartServices;
    LiveData<Boolean> isProductAddedToCart;
    LiveData<ArrayList<CartProduct>> cartUserProducts;
    LiveData<ArrayList<CartProduct>> cartAdminProducts;
    LiveData<Boolean> isProductDeletedFromCart;
    LiveData<Boolean> isAllProductDeletedFromCart;


    public CartRepo(Application application) {
        cartServices = new CartServices(application);
    }

    public void getUserProducts() {
        cartServices.getUserProducts();
    }

    public void getAdminProducts(String userId) {
        cartServices.getAdminProducts(userId);
    }

    public void addProductToCart(CartProduct cartProduct) {
        cartServices.addProductToCart(cartProduct);
    }

    public void deleteProductFromCart(String productId) {
        cartServices.deleteProductFromCart(productId);
    }

    public void deleteAllProducts() {
        cartServices.deleteAllProducts();
    }

    public LiveData<Boolean> getIsProductAddedToCart() {
        return cartServices.getIsProductAddedToCart();
    }

    public LiveData<ArrayList<CartProduct>> getCartUserProducts() {
        return cartServices.getCartUserProducts();
    }

    public LiveData<ArrayList<CartProduct>> getCartAdminProducts() {
        return cartServices.getCartAdminProducts();
    }

    public LiveData<Boolean> getIsProductDeletedFromCart() {
        return cartServices.getIsProductDeletedFromCart();
    }

    public LiveData<Boolean> getIsAllProductDeletedFromCart() {
        return cartServices.getIsAllProductDeletedFromCart();
    }
}
