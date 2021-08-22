package com.modern_tec.ecommerce.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.data.database.ProductService;

import java.util.List;

public class ProductRepo {

    private ProductService productService;
    LiveData<Boolean> isProductUpload;
    LiveData<Product> productLiveData;

    public ProductRepo(Application application) {
        productService = new ProductService(application);
        isProductUpload = productService.getIsProductUpload();
    }

    public void storeProductInformation(Product product, boolean isPhotoUpdated) {
        productService.storeProductInformation(product, isPhotoUpdated);
    }

    public void getProductByName(String productName) {
        productService.getProductByName(productName);
    }

    public void approveProduct(String id) {
        productService.approveProduct(id);
    }

    public LiveData<Boolean> getIsProductApproved() {
        return productService.getIsProductApproved();
    }


    public void deleteProductById(String id) {
        productService.deleteProductById(id);
    }

    public LiveData<Boolean> getIsProductUpload() {
        return isProductUpload;
    }

    public LiveData<List<Product>> getProductLiveData() {
        return productService.getProductMutableLiveData();
    }

    public LiveData<List<Product>> getProductsByCategory(String name) {
        return productService.getProductsByCategory(name);
    }

    public LiveData<Product> getSingleProductLiveData() {
        return productService.getSingleProductMutableLiveData();
    }

    public void getProducts() {
        productService.getProducts();
    }

    public void getUnApprovedProducts() {
        productService.getUnApprovedProducts();
    }

    public void getProductById(String id) {
        productService.getProductById(id);
    }

    public void getSellerProducts(String sellerId) {
        productService.getSellerProducts(sellerId);
    }

    public LiveData<Boolean> getIsProductDeleted() {
        return productService.getIsProductDeleted();
    }
}
