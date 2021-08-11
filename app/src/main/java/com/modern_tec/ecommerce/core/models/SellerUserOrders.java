package com.modern_tec.ecommerce.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SellerUserOrders implements Parcelable {

    private String userId;
    private List<Order> orderList;

    public SellerUserOrders(String userId, List<Order> orderList) {
        this.userId = userId;
        this.orderList = orderList;
    }

    public SellerUserOrders() {
    }

    protected SellerUserOrders(Parcel in) {
        userId = in.readString();
        orderList = in.createTypedArrayList(Order.CREATOR);
    }

    public static final Creator<SellerUserOrders> CREATOR = new Creator<SellerUserOrders>() {
        @Override
        public SellerUserOrders createFromParcel(Parcel in) {
            return new SellerUserOrders(in);
        }

        @Override
        public SellerUserOrders[] newArray(int size) {
            return new SellerUserOrders[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeTypedList(orderList);
    }
}
