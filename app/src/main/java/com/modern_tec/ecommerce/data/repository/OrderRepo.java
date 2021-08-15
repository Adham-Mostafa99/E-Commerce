package com.modern_tec.ecommerce.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.modern_tec.ecommerce.core.models.SellerUserOrders;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.data.database.OrderServices;

import java.util.List;

public class OrderRepo {
    OrderServices orderServices;

    public OrderRepo(Application application) {
        orderServices = new OrderServices();
    }


    public LiveData<Boolean> getIsOrderAdded() {
        return orderServices.getIsOrderAdded();
    }


    public void createOrder(Order order) {
        orderServices.createOrder(order);
    }

    public void getUserOrders() {
        orderServices.getUserOrders();
    }

    public void getAdminUserOrdersById(String userId) {
        orderServices.getAdminUserOrdersById(userId);
    }

    public void getAdminUserOrders() {
        orderServices.getAdminUserOrders();
    }

    public void makeOrderShipped(String userId, String orderId) {
        orderServices.setStateOfOrder(userId, orderId, "shipped");
    }

    public LiveData<List<Order>> getUserOrderLiveData() {
        return orderServices.getUserOrderLiveData();
    }

    public LiveData<List<SellerUserOrders>> getAdminUserOrdersLiveData() {
        return orderServices.getAdminUserOrdersMutableLiveData();
    }

    public LiveData<List<Order>> getUserShippedOrderLiveData() {
        return orderServices.getUserShippedOrderLiveData();
    }

    public LiveData<List<Order>> getUserUnShippedOrderLiveData() {
        return orderServices.getUserUnShippedOrderLiveData();
    }

    public LiveData<Boolean> getIsOrderChanged() {
        return orderServices.getIsStateChanged();
    }

}
