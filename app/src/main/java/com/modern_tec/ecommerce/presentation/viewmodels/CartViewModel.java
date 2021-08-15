package com.modern_tec.ecommerce.presentation.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.data.repository.CartRepo;

import java.util.ArrayList;

public class CartViewModel extends AndroidViewModel {

    LiveData<Boolean> isProductAddedToCart;
    LiveData<ArrayList<CartProduct>> cartUserProducts;
    LiveData<ArrayList<CartProduct>> cartAdminProducts;
    LiveData<Boolean> isProductDeletedFromCart;
    LiveData<Boolean> isAllProductDeletedFromCart;


    CartRepo cartRepo;

    public CartViewModel(Application application) {
        super(application);
        cartRepo = new CartRepo(application);
    }

    public void getUserProducts() {
        cartRepo.getUserProducts();
    }

    public void getAdminProducts(String userId) {
        cartRepo.getAdminProducts(userId);
    }

    public void addProductToCart(CartProduct cartProduct) {
        cartRepo.addProductToCart(cartProduct);
    }

    public void deleteProductFromCart(String productId) {
        cartRepo.deleteProductFromCart(productId);
    }

    public void deleteAllProducts() {
        cartRepo.deleteAllProducts();
    }

    public LiveData<Boolean> getIsProductAddedToCart() {
        return cartRepo.getIsProductAddedToCart();
    }

    public LiveData<ArrayList<CartProduct>> getCartUserProducts() {
        return cartRepo.getCartUserProducts();
    }

    public LiveData<ArrayList<CartProduct>> getCartAdminProducts() {
        return cartRepo.getCartAdminProducts();
    }

    public LiveData<Boolean> getIsProductDeletedFromCart() {
        return cartRepo.getIsProductDeletedFromCart();
    }

    public LiveData<Boolean> getIsAllProductDeletedFromCart() {
        return cartRepo.getIsAllProductDeletedFromCart();
    }
}
