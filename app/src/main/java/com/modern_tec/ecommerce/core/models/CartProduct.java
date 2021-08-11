package com.modern_tec.ecommerce.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class CartProduct implements Parcelable, Serializable {
    private String productId;
    private String productName;
    private double productPrice;
    private String productDate;
    private String productTime;
    private int productQuantity;
    private double productDiscount;
    private double productTotalPrice;

    public CartProduct(String productId, String productName, double productPrice, String productDate,
                       String productTime, int productQuantity, double productDiscount
            , double productTotalPrice) {
        this.productId = productId;
        this.productName = productName;
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
    public String toString() {
        return "CartProduct{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDate='" + productDate + '\'' +
                ", productTime='" + productTime + '\'' +
                ", productQuantity=" + productQuantity +
                ", productDiscount=" + productDiscount +
                ", productTotalPrice=" + productTotalPrice +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productName);
        dest.writeDouble(productPrice);
        dest.writeString(productDate);
        dest.writeString(productTime);
        dest.writeInt(productQuantity);
        dest.writeDouble(productDiscount);
        dest.writeDouble(productTotalPrice);
    }
}
