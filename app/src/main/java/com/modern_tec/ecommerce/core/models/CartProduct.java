package com.modern_tec.ecommerce.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class CartProduct implements Parcelable, Serializable {
    private String productId;
    private String productName;
    private String productImage;
    private String productSeller;
    private double productPrice;
    private String productDate;
    private String productTime;
    private int productQuantity;
    private double productDiscount;
    private double productTotalPrice;


    public CartProduct(String productId, String productName, String productImage, String productSeller, double productPrice, String productDate, String productTime, int productQuantity, double productDiscount, double productTotalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productSeller = productSeller;
        this.productPrice = productPrice;
        this.productDate = productDate;
        this.productTime = productTime;
        this.productQuantity = productQuantity;
        this.productDiscount = productDiscount;
        this.productTotalPrice = productTotalPrice;
    }

    public CartProduct() {
    }

    protected CartProduct(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        productImage = in.readString();
        productSeller = in.readString();
        productPrice = in.readDouble();
        productDate = in.readString();
        productTime = in.readString();
        productQuantity = in.readInt();
        productDiscount = in.readDouble();
        productTotalPrice = in.readDouble();
    }

    public static final Creator<CartProduct> CREATOR = new Creator<CartProduct>() {
        @Override
        public CartProduct createFromParcel(Parcel in) {
            return new CartProduct(in);
        }

        @Override
        public CartProduct[] newArray(int size) {
            return new CartProduct[size];
        }
    };

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductSeller() {
        return productSeller;
    }


    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductDate() {
        return productDate;
    }

    public String getProductTime() {
        return productTime;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public double getProductDiscount() {
        return productDiscount;
    }

    public double getProductTotalPrice() {
        return productTotalPrice;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productName);
        dest.writeString(productImage);
        dest.writeString(productSeller);
        dest.writeDouble(productPrice);
        dest.writeString(productDate);
        dest.writeString(productTime);
        dest.writeInt(productQuantity);
        dest.writeDouble(productDiscount);
        dest.writeDouble(productTotalPrice);
    }
}
