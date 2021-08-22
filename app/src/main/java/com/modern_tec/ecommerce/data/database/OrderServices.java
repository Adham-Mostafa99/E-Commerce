package com.modern_tec.ecommerce.data.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.modern_tec.ecommerce.core.models.SellerUserOrders;
import com.modern_tec.ecommerce.core.models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServices {
    private static final String ORDER_REF_NAME = "orders";

    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private final DatabaseReference ordersReference = FirebaseDatabase.getInstance().getReference();

    MutableLiveData<Boolean> isOrderAdded = new MutableLiveData<>();
    MutableLiveData<Boolean> isStateChanged = new MutableLiveData<>();
    MutableLiveData<List<Order>> userOrderLiveData = new MutableLiveData<>();
    MutableLiveData<List<Order>> userShippedOrderLiveData = new MutableLiveData<>();
    MutableLiveData<List<Order>> userUnShippedOrderLiveData = new MutableLiveData<>();
    MutableLiveData<List<SellerUserOrders>> adminUserOrdersMutableLiveData = new MutableLiveData<>();

    public OrderServices() {

    }

    public void createOrder(Order order) {
        ordersReference.child(ORDER_REF_NAME)
                .child(firebaseUser.getUid())
                .child(order.getOrderId())
                .setValue(order, -1 * new Date().getTime())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful())
                            isOrderAdded.setValue(true);
                    }
                });
    }

    public void getUserOrders() {
        //TODO maintain orderby insted if statement
        ordersReference
                .child(ORDER_REF_NAME)
                .child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        List<Order> orderShippedList = new ArrayList<>();
                        List<Order> orderUnShippedList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.exists() && dataSnapshot.getValue(Order.class).getState().equals("not shipped"))
                                orderUnShippedList.add(dataSnapshot.getValue(Order.class));
                            else
                                orderShippedList.add(dataSnapshot.getValue(Order.class));
                        }

                        userShippedOrderLiveData.setValue(orderShippedList);
                        userUnShippedOrderLiveData.setValue(orderUnShippedList);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public void getUserShippedOrders() {
        ordersReference
                .child(ORDER_REF_NAME)
                .child(firebaseUser.getUid())
                .orderByChild("state")
                .equalTo("shipped")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        List<Order> orderShippedList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            orderShippedList.add(dataSnapshot.getValue(Order.class));
                        }
                        userShippedOrderLiveData.setValue(orderShippedList);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public void getUserUnShippedOrders() {
        ordersReference
                .child(ORDER_REF_NAME)
                .child(firebaseUser.getUid())
                .orderByChild("state")
                .equalTo("not shipped")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        List<Order> orderUnShippedList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            orderUnShippedList.add(dataSnapshot.getValue(Order.class));
                        }
                        userUnShippedOrderLiveData.setValue(orderUnShippedList);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public void getAdminUserOrdersById(String userId) {
        ordersReference.child(ORDER_REF_NAME)
                .child(userId)
                .orderByChild("state")
                .equalTo("not shipped")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        List<Order> orderList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            orderList.add(dataSnapshot.getValue(Order.class));
                        }
                        userOrderLiveData.setValue(orderList);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }


    public void getAdminUserOrders() {
        ordersReference.child(ORDER_REF_NAME)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        List<SellerUserOrders> sellerUserOrdersList = new ArrayList<>();
                        for (DataSnapshot user : snapshot.getChildren()) {
                            if (user.exists()) {
                                SellerUserOrders currentUser = new SellerUserOrders();
                                List<Order> orderList = new ArrayList<>();
                                for (DataSnapshot orders : user.getChildren()) {
                                    Order currentOrder = orders.getValue(Order.class);
                                    if (currentOrder.getState().equals("not shipped"))
                                        orderList.add(orders.getValue(Order.class));
                                }
                                if (orderList.size() > 0) {
                                    currentUser.setUserId(user.getKey());
                                    currentUser.setOrderList(orderList);
                                    sellerUserOrdersList.add(currentUser);
                                }
                            }
                        }
                        adminUserOrdersMutableLiveData.setValue(sellerUserOrdersList);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public void setStateOfOrder(String userId, String orderId, String state) {
        ordersReference.child(ORDER_REF_NAME)
                .child(userId)
                .child(orderId)
                .child("state")
                .setValue(state)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        isStateChanged.setValue(true);
                    }
                });
    }


    public LiveData<Boolean> getIsOrderAdded() {
        return isOrderAdded;
    }

    public LiveData<List<Order>> getUserOrderLiveData() {
        return userOrderLiveData;
    }

    public LiveData<List<SellerUserOrders>> getAdminUserOrdersMutableLiveData() {
        return adminUserOrdersMutableLiveData;
    }

    public LiveData<List<Order>> getUserShippedOrderLiveData() {
        return userShippedOrderLiveData;
    }

    public LiveData<List<Order>> getUserUnShippedOrderLiveData() {
        return userUnShippedOrderLiveData;
    }

    public LiveData<Boolean> getIsStateChanged() {
        return isStateChanged;
    }
}
