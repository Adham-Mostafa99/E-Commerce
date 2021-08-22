package com.modern_tec.ecommerce.data.database;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.modern_tec.ecommerce.core.models.CartProduct;
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

public class CartServices {
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private static final String CART_LIST_REF_NAME = "Cart List";
    private static final String CART_LIST_ADMIN_VIEW_REF_NAME = "Admin View";
    private static final String CART_LIST_USER_VIEW_REF_NAME = "User View";
    private final DatabaseReference cartListProductReference = FirebaseDatabase.getInstance().getReference();

    MutableLiveData<Boolean> isProductAddedToCart = new MutableLiveData<>();
    MutableLiveData<Boolean> isProductDeletedFromCart = new MutableLiveData<>();
    MutableLiveData<Boolean> isAllProductDeletedFromCart = new MutableLiveData<>();
    MutableLiveData<ArrayList<CartProduct>> cartUserProducts = new MutableLiveData<>();
    MutableLiveData<ArrayList<CartProduct>> cartAdminProducts = new MutableLiveData<>();
    Context context;

    public CartServices(Context context) {
        this.context = context;
    }

    public void addProductToCart(CartProduct cartProduct) {


        //add to admin view
        cartListProductReference.child(CART_LIST_REF_NAME)
                .child(CART_LIST_ADMIN_VIEW_REF_NAME)
                .child(firebaseUser.getUid())
                .child("Products")
                .child(cartProduct.getProductId())
                .setValue(cartProduct)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful())
                            //add to user view
                            addProductToUserView(cartProduct);
                    }
                });


    }

    private void addProductToUserView(CartProduct cartProduct) {
        cartListProductReference.child(CART_LIST_REF_NAME)
                .child(CART_LIST_USER_VIEW_REF_NAME)
                .child(firebaseUser.getUid())
                .child("Products")
                .child(cartProduct.getProductId())
                .setValue(cartProduct)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            isProductAddedToCart.setValue(true);
                        }
                    }
                });
    }

    public void getUserProducts() {
        cartListProductReference.child(CART_LIST_REF_NAME)
                .child(CART_LIST_USER_VIEW_REF_NAME)
                .child(firebaseUser.getUid())
                .child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<CartProduct> list = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.exists())
                                list.add(dataSnapshot.getValue(CartProduct.class));
                        }
                        cartUserProducts.setValue(list);

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public void getAdminProducts(String userId) {
        cartListProductReference.child(CART_LIST_REF_NAME)
                .child(CART_LIST_ADMIN_VIEW_REF_NAME)
                .child(userId)
                .child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<CartProduct> list = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.exists())
                                list.add(dataSnapshot.getValue(CartProduct.class));
                        }
                        cartAdminProducts.setValue(list);

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public void deleteProductFromCart(String productId) {
        cartListProductReference.child(CART_LIST_REF_NAME)
                .child(CART_LIST_ADMIN_VIEW_REF_NAME)
                .child(firebaseUser.getUid())
                .child("Products")
                .child(productId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            cartListProductReference.child(CART_LIST_REF_NAME)
                                    .child(CART_LIST_USER_VIEW_REF_NAME)
                                    .child(firebaseUser.getUid())
                                    .child("Products")
                                    .child(productId)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                isProductDeletedFromCart.setValue(true);
                                            }

                                        }
                                    });
                        }

                    }
                });
    }

    public void deleteAllProducts(){
        cartListProductReference.child(CART_LIST_REF_NAME)
                .child(CART_LIST_ADMIN_VIEW_REF_NAME)
                .child(firebaseUser.getUid())
                .child("Products")
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            cartListProductReference.child(CART_LIST_REF_NAME)
                                    .child(CART_LIST_USER_VIEW_REF_NAME)
                                    .child(firebaseUser.getUid())
                                    .child("Products")
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                isAllProductDeletedFromCart.setValue(true);
                                            }

                                        }
                                    });
                        }

                    }
                });
    }

    public LiveData<ArrayList<CartProduct>> getCartAdminProducts() {
        return cartAdminProducts;
    }

    public LiveData<ArrayList<CartProduct>> getCartUserProducts() {
        return cartUserProducts;
    }

    public LiveData<Boolean> getIsProductAddedToCart() {
        return isProductAddedToCart;
    }

    public LiveData<Boolean> getIsProductDeletedFromCart() {
        return isProductDeletedFromCart;
    }

    public LiveData<Boolean> getIsAllProductDeletedFromCart() {
        return isAllProductDeletedFromCart;
    }
}
