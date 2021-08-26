package com.modern_tec.ecommerce.core.models;

import java.io.Serializable;

public class Product implements Serializable {

    private String productId;
    private String productImageUrl;
    private String productName;
    private String productDescription;
    private double productPrice;
    private String productDate;
    private String productTime;
    private String productCategory;
    private String sellerName;
    private Address sellerAddress;
    private String sellerPhone;
    private String sellerEmail;
    private String sellerId;
    private String productState;

    public Product() {
    }

    public Product(String productId, String productImageUrl, String productName, String productDescription, double productPrice, String productDate, String productTime, String productCategory, String sellerName, Address sellerAddress, String sellerPhone, String sellerEmail, String sellerId, String productState) {
        this.productId = productId;
        this.productImageUrl = productImageUrl;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productDate = productDate;
        this.productTime = productTime;
        this.productCategory = productCategory;
        this.sellerName = sellerName;
        this.sellerAddress = sellerAddress;
        this.sellerPhone = sellerPhone;
        this.sellerEmail = sellerEmail;
        this.sellerId = sellerId;
        this.productState = productState;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public String getProductDate() {
        return productDate;
    }

    public String getProductTime() {
        return productTime;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getSellerName() {
        return sellerName;
    }

    public Address getSellerAddress() {
        return sellerAddress;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productImageUrl='" + productImageUrl + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productPrice=" + productPrice +
                ", productDate='" + productDate + '\'' +
                ", productTime='" + productTime + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", sellerAddress='" + sellerAddress + '\'' +
                ", sellerPhone='" + sellerPhone + '\'' +
                ", sellerEmail='" + sellerEmail + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", productState='" + productState + '\'' +
                '}';
    }
}
