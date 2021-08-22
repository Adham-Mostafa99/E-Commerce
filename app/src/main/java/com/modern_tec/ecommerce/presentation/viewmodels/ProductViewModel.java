package com.modern_tec.ecommerce.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.data.repository.ProductRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    ProductRepo productRepo;
    LiveData<Boolean> isProductUpload;
    LiveData<Product> productLiveData;

    public ProductViewModel(@NonNull @NotNull Application application) {
        super(application);

        productRepo = new ProductRepo(application);
        isProductUpload = productRepo.getIsProductUpload();
    }

    public void storeProductInformation(Product product, boolean isPhotoUpdated) {
        productRepo.storeProductInformation(product, isPhotoUpdated);
    }

    public void getProductById(String id) {
        productRepo.getProductById(id);
    }

    public void deleteProductById(String id) {
        productRepo.deleteProductById(id);
    }

    public void getProductByName(String productName) {
        productRepo.getProductByName(productName);
    }

    public void approveProduct(String id) {
        productRepo.approveProduct(id);
    }

    public LiveData<Boolean> getIsProductApproved() {
        return productRepo.getIsProductApproved();
    }
    public LiveData<Boolean> getIsProductUpload() {
        return isProductUpload;
    }

    public LiveData<List<Product>> getProductLiveData() {
        return productRepo.getProductLiveData();
    }

    public LiveData<List<Product>> getProductsByCategory(String name) {
        return productRepo.getProductsByCategory(name);
    }

    public LiveData<Product> getSingleProductLiveData() {
        return productRepo.getSingleProductLiveData();
    }

    public LiveData<Boolean> getIsProductDeleted() {
        return productRepo.getIsProductDeleted();
    }

    public void getProducts() {
        productRepo.getProducts();
    }

    public void getUnApprovedProducts() {
        productRepo.getUnApprovedProducts();
    }

    public void getSellerProducts(String sellerId) {
        productRepo.getSellerProducts(sellerId);
    }
}
