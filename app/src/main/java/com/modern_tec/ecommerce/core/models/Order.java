package com.modern_tec.ecommerce.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Order implements Parcelable {

    private String orderId;
    private List<CartProduct> cartProductList;
    private String userName;
    private String userEmail;
    private Address userAddress;
    private String orderTime;
    private String orderDate;
    private double totalPrice;
    private String state;


    public Order(String orderId, List<CartProduct> cartProductList, String userName, String userEmail, Address userAddress, String orderTime, String orderDate, double totalPrice, String state) {
        this.orderId = orderId;
        this.cartProductList = cartProductList;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.state = state;
    }

    protected Order(Parcel in) {
        orderId = in.readString();
        cartProductList = in.createTypedArrayList(CartProduct.CREATOR);
        userName = in.readString();
        userEmail = in.readString();
        orderTime = in.readString();
        orderDate = in.readString();
        totalPrice = in.readDouble();
        state = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeTypedList(cartProductList);
        dest.writeString(userName);
        dest.writeString(userEmail);
        dest.writeString(orderTime);
        dest.writeString(orderDate);
        dest.writeDouble(totalPrice);
        dest.writeString(state);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Address getUserAddress() {
        return userAddress;
    }

    public Order() {
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setState(String state) {
        this.state = state;
    }

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
                ", userAddress=" + userAddress +
                ", orderTime='" + orderTime + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", totalPrice=" + totalPrice +
                ", state='" + state + '\'' +
                '}';
    }
}


