package com.modern_tec.ecommerce.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Order implements Parcelable {

    private String orderId;
    private List<CartProduct> cartProductList;
    private String userName;
    private String userEmail;
    private String userAddress;
    private String userCity;
    private String userPhone;
    private String orderTime;
    private String orderDate;
    private double totalPrice;
    private String state;

    public Order(String orderId, List<CartProduct> cartProductList, String userName, String userEmail, String userAddress, String userCity, String userPhone, String orderTime, String orderDate, double totalPrice, String state) {
        this.orderId = orderId;
        this.cartProductList = cartProductList;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userCity = userCity;
        this.userPhone = userPhone;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.state = state;
    }

    public Order() {
    }

    protected Order(Parcel in) {
        orderId = in.readString();
        cartProductList = in.createTypedArrayList(CartProduct.CREATOR);
        userName = in.readString();
        userEmail = in.readString();
        userAddress = in.readString();
        userCity = in.readString();
        userPhone = in.readString();
        orderTime = in.readString();
        orderDate = in.readString();
        totalPrice = in.readDouble();
        state = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public List<CartProduct> getCartProductList() {
        return cartProductList;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getState() {
        return state;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", cartProductList=" + cartProductList +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userCity='" + userCity + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", totalPrice=" + totalPrice +
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeTypedList(cartProductList);
        dest.writeString(userName);
        dest.writeString(userEmail);
        dest.writeString(userAddress);
        dest.writeString(userCity);
        dest.writeString(userPhone);
        dest.writeString(orderTime);
        dest.writeString(orderDate);
        dest.writeDouble(totalPrice);
        dest.writeString(state);
    }
}


