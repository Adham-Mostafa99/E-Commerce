package com.modern_tec.ecommerce.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modern_tec.ecommerce.core.models.SellerUserOrders;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.presentation.repository.OrderRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    OrderRepo orderRepo;

    public OrderViewModel(@NonNull @NotNull Application application) {
        super(application);
        orderRepo = new OrderRepo(application);
    }


    public LiveData<Boolean> getIsOrderAdded() {
        return orderRepo.getIsOrderAdded();
    }

    public void createOrder(Order order) {
        orderRepo.createOrder(order);
    }

    public void getUserOrders() {
        orderRepo.getUserOrders();
    }

    public void getAdminUserOrdersById(String userId) {
        orderRepo.getAdminUserOrdersById(userId);
    }

    public void getAdminUserOrders() {
        orderRepo.getAdminUserOrders();
    }

    public void makeOrderShipped(String userId, String orderId) {
        orderRepo.makeOrderShipped(userId, orderId);
    }

    public LiveData<List<Order>> getUserOrderLiveData() {
        return orderRepo.getUserOrderLiveData();
    }

    public LiveData<List<SellerUserOrders>> getAdminUserOrdersLiveData() {
        return orderRepo.getAdminUserOrdersLiveData();
    }

    public LiveData<List<Order>> getUserShippedOrderLiveData() {
        return orderRepo.getUserShippedOrderLiveData();
    }

    public LiveData<List<Order>> getUserUnShippedOrderLiveData() {
        return orderRepo.getUserUnShippedOrderLiveData();
    }

    public LiveData<Boolean> getIsOrderChanged() {
        return orderRepo.getIsOrderChanged();
    }
}
