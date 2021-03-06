package com.modern_tec.ecommerce.data.database;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.google.android.gms.tasks.OnSuccessListener;
import com.modern_tec.ecommerce.core.models.Address;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.core.models.Seller;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.data.shared_pref.UserType;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Account {
    private Context context;
    private final String parentUserDbName = "Users";
    private final String parentSellerDbName = "Sellers";
    private final String parentAdminDbName = "Admins";
    private static final String USERS_IMAGES_FOLDER_NAME = "User Images";

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseUser firebaseUser = auth.getCurrentUser();
    private final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(USERS_IMAGES_FOLDER_NAME);


    private MutableLiveData<Boolean> isCheckedCreated = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCheckedLogged = new MutableLiveData<>();
    private MutableLiveData<String> passwordResetLinkSent = new MutableLiveData<>();

    private MutableLiveData<Boolean> isAccountCreated = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLogin = new MutableLiveData<>();
    private MutableLiveData<Boolean> isPasswordUpdated = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Seller> sellerMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<User> updatedProfile = new MutableLiveData<>();

    public Account(Context context) {
        this.context = context;
    }

    public LiveData<Boolean> getIsLogin() {
        return isLogin;
    }

    public LiveData<Boolean> getIsCheckedCreated() {
        return isCheckedCreated;
    }

    public LiveData<Boolean> getIsCheckedLogged() {
        return isCheckedLogged;
    }

    public LiveData<Boolean> getIsAccountCreated() {
        return isAccountCreated;
    }

    public String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void createAccount(String name, String email, String pass) {

        isCheckedCreated.setValue(true);

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        storeUserInDb(name, email, pass);
                    } else {
                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        isAccountCreated.setValue(false);
                    }
                });


    }

    public void createSellerAccount(Seller seller) {
        auth.createUserWithEmailAndPassword(seller.getEmail(), seller.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        storeSellerInDB(seller);
                    } else {
                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        isAccountCreated.setValue(false);
                    }
                });
    }

    private void storeSellerInDB(Seller seller) {

        rootRef
                .child(parentSellerDbName)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(seller)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            isAccountCreated.setValue(true);
                        } else
                            isAccountCreated.setValue(false);
                    }
                });
    }

    public void loginAdmin(String email, String pass) {
        rootRef
                .child(parentAdminDbName)
                .orderByChild("email")
                .equalTo(email)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            auth.signInWithEmailAndPassword(email, pass)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(context, "Logged in successfully...", Toast.LENGTH_SHORT).show();
                                            isLogin.setValue(true);
                                        } else {
                                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            isLogin.setValue(false);
                                        }
                                    });
                        } else {
                            Toast.makeText(context, "No Admin with this Account", Toast.LENGTH_SHORT).show();
                            isLogin.setValue(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public LiveData<Boolean> loginSeller(String email, String pass) {
        rootRef
                .child(parentSellerDbName)
                .orderByChild("email")
                .equalTo(email)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            auth.signInWithEmailAndPassword(email, pass)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(context, "Logged in successfully...", Toast.LENGTH_SHORT).show();
                                            isLogin.setValue(true);
                                        } else {
                                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            isLogin.setValue(false);
                                        }
                                    });
                        } else {
                            Toast.makeText(context, "No Seller with this Account", Toast.LENGTH_SHORT).show();
                            isLogin.setValue(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        isLogin.setValue(false);
                    }
                });

        return isLogin;
    }


    public void loginUser(String email, String pass) {

        isCheckedLogged.setValue(true);

        rootRef
                .child(parentUserDbName)
                .orderByChild("email")
                .equalTo(email)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            auth.signInWithEmailAndPassword(email, pass)
                                    .addOnCompleteListener(task -> {

                                        isCheckedLogged.postValue(false);

                                        if (task.isSuccessful()) {
                                            Toast.makeText(context, "Logged in successfully...", Toast.LENGTH_SHORT).show();
                                            isLogin.setValue(true);
                                        } else {
                                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            isLogin.setValue(false);
                                        }
                                    });
                        } else {
                            Toast.makeText(context, "No User with this Account", Toast.LENGTH_SHORT).show();
                            isLogin.setValue(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        isLogin.setValue(false);

                    }
                });

    }

    public void logOutAccount() {
        auth.signOut();
        UserType userType = new UserType(context);
        userType.deleteShared();
    }


    private void storeUserInDb(String name, String email, String pass) {
        HashMap<String, Object> userDataMap = new HashMap<>();
        userDataMap.put("email", email);
        userDataMap.put("name", name);

        rootRef
                .child(parentUserDbName)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .updateChildren(userDataMap)
                .addOnCompleteListener(task -> {
                    isCheckedCreated.postValue(false);

                    if (task.isSuccessful()) {
                        isAccountCreated.setValue(true);
                        Toast.makeText(context, "Congratulation Account is created", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Network Error, please try again ", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void getSellerInfo() {
        rootRef.child(parentSellerDbName)
                .child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Seller user = snapshot.getValue(Seller.class);
                            sellerMutableLiveData.setValue(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

    }

    public void getUserInfoById(String id) {
        rootRef.child(parentUserDbName)
                .child(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            User user = snapshot.getValue(User.class);
                            userMutableLiveData.setValue(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public void getUserInfo() {

        rootRef.child(parentUserDbName)
                .child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            User user = snapshot.getValue(User.class);
                            userMutableLiveData.setValue(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }


    private void storeUserInfo(User user) {
        HashMap<String, Object> userDataMap = new HashMap<>();

        userDataMap.put("email", user.getEmail());
        userDataMap.put("name", user.getName());
        userDataMap.put("photoUrl", user.getPhotoUrl());
        userDataMap.put("address", user.getAddress());

        rootRef
                .child(parentUserDbName)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .updateChildren(userDataMap)
                .addOnCompleteListener(task -> {
                    isCheckedCreated.postValue(false);

                    if (task.isSuccessful()) {
                        updatedProfile.setValue(user);
                        Toast.makeText(context, "User Updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Network Error, please try again ", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void updateUserProfile(User user, boolean isPhoto) {
        if (isPhoto) {
            storePhoto(user);
        } else {
            storeUserInfo(user);
        }

    }

    private void storePhoto(User user) {
        StorageReference filePath = storageReference.child(firebaseUser.getUid() + ".jpg");

        UploadTask uploadTask = filePath.putFile(Uri.parse(user.getPhotoUrl()));

        uploadTask
                .addOnSuccessListener(taskSnapshot -> {
                    getImageDownloadImageUrl(uploadTask, filePath, user);
                    Toast.makeText(context, "User Image Upload Successfully...", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void getImageDownloadImageUrl(UploadTask uploadTask, StorageReference filePath, User user) {
        uploadTask
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            user.setPhotoUrl(task.getResult().toString());
                            storeUserInfo(user);
                        }
                    }
                });
    }


    public void updatePassword(String newPass) {

        firebaseUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful())
                    isPasswordUpdated.setValue(true);
            }

        });
    }

    public void updatePasswordByEmail(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            passwordResetLinkSent.setValue("Link is Sent.");
                        } else {
                            passwordResetLinkSent.setValue(task.getException().getMessage());
                        }
                    }
                });
    }

    public LiveData<Boolean> updateUserAddress(Address address) {
        MutableLiveData<Boolean> updated = new MutableLiveData<>();


        rootRef
                .child(parentUserDbName)
                .child(getUserId())
                .child("address")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        List<Address> addressList = new ArrayList<>();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot current : dataSnapshot.getChildren()) {
                                addressList.add(current.getValue(Address.class));
                            }
                        }
                        addressList.add(address);

                        rootRef
                                .child(parentUserDbName)
                                .child(getUserId())
                                .child("address")
                                .setValue(addressList)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        if (task.isSuccessful())
                                            updated.setValue(true);
                                    }
                                });
                    }
                });


        return updated;
    }

    public LiveData<Boolean> updateOriginalAddress(Address address) {
        MutableLiveData<Boolean> updated = new MutableLiveData<>();

        rootRef
                .child(parentUserDbName)
                .child(getUserId())
                .child("address")
                .child("0")
                .setValue(address)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        updated.setValue(true);
                    }
                });

        return updated;
    }

    public LiveData<Boolean> storeProductOnFavorite(String id) {
        MutableLiveData<Boolean> isProductStore = new MutableLiveData<>();

        rootRef
                .child(parentUserDbName)
                .child(getUserId())
                .child("favorites")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> favList = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : task.getResult().getChildren())
                                favList.add(dataSnapshot.getValue(String.class));


                            if (!favList.contains(id)) {
                                favList.add(id);

                                rootRef
                                        .child(parentUserDbName)
                                        .child(getUserId())
                                        .child("favorites")
                                        .setValue(favList)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    isProductStore.setValue(true);
                                                }
                                            }
                                        });

                            }

                        }
                    }
                });

        return isProductStore;
    }

    public LiveData<Boolean> removeProductFromFavorite(String id) {
        MutableLiveData<Boolean> isProductRemove = new MutableLiveData<>();

        rootRef
                .child(parentUserDbName)
                .child(getUserId())
                .child("favorites")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> favList = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : task.getResult().getChildren())
                                favList.add(dataSnapshot.getValue(String.class));

                            if (!favList.isEmpty() && favList.contains(id)) {
                                favList.remove(id);

                                rootRef
                                        .child(parentUserDbName)
                                        .child(getUserId())
                                        .child("favorites")
                                        .setValue(favList)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    isProductRemove.setValue(true);
                                                }
                                            }
                                        });


                            }

                        }
                    }
                });

        return isProductRemove;
    }

    public LiveData<List<Product>> getUserFavProducts() {
        MutableLiveData<List<Product>> productsListLivedata = new MutableLiveData<>();

        rootRef
                .child(parentUserDbName)
                .child(getUserId())
                .child("favorites")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        List<Product> productList = new ArrayList<>();
                        productsListLivedata.setValue(productList);
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String id = dataSnapshot.getValue(String.class);
                            rootRef
                                    .child(ProductService.PRODUCT_REF_NAME)
                                    .orderByChild("productId")
                                    .equalTo(id)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                                if (dataSnapshot1.exists()) {
                                                    productList.add(dataSnapshot1.getValue(Product.class));
                                                    productsListLivedata.postValue(productList);

                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
        return productsListLivedata;
    }


    public LiveData<Boolean> getIsProductFav(String id) {
        MutableLiveData<Boolean> isFav = new MutableLiveData<>();
        rootRef
                .child(parentUserDbName)
                .child(getUserId())
                .child("favorites")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.exists() && dataSnapshot.getValue(String.class).equals(id))
                                isFav.setValue(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        return isFav;
    }

    public MutableLiveData<Seller> getSellerMutableLiveData() {
        return sellerMutableLiveData;
    }

    public LiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public LiveData<User> getUpdatedProfile() {
        return updatedProfile;
    }

    public LiveData<Boolean> getIsPasswordUpdated() {
        return isPasswordUpdated;
    }

    public LiveData<String> getPasswordResetLinkSent() {
        return passwordResetLinkSent;
    }
}
