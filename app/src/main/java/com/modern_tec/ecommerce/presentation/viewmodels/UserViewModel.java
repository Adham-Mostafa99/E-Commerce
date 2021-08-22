package com.modern_tec.ecommerce.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.modern_tec.ecommerce.core.models.Address;
import com.modern_tec.ecommerce.core.models.Seller;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.data.repository.UserRepo;

import org.jetbrains.annotations.NotNull;

public class UserViewModel extends AndroidViewModel {
    private UserRepo userRepo;

    private LiveData<Boolean> isLogin;
    private MutableLiveData<Boolean> isCheckedCreated;
    private MutableLiveData<Boolean> isCheckedLogged;
    private LiveData<Boolean> isAccountCreated;
    private LiveData<User> userLiveData;
    private LiveData<User> updatedProfile;


    public UserViewModel(@NonNull @NotNull Application application) {
        super(application);

        userRepo = new UserRepo(application);
    }

    public LiveData<User> getUpdatedProfile() {
        return userRepo.getUpdatedProfile();
    }

    public LiveData<User> getUserLiveData() {
        return userRepo.getUserLiveData();
    }

    public void loginUser(String email, String pass) {
        userRepo.loginUser(email, pass);
    }

    public LiveData<Boolean> loginSeller(String email, String pass) {
        return userRepo.loginSeller(email, pass);
    }

    public void loginAdmin(String email, String pass) {
        userRepo.loginAdmin(email, pass);
    }

    public void logOut() {
        userRepo.logOut();
    }

    public void createAccount(String name, String email, String pass) {
        userRepo.createAccount(name, email, pass);
    }

    public void createSellerAccount(Seller seller) {
        userRepo.createSellerAccount(seller);
    }

    public FirebaseUser getCurrentUser() {
        return userRepo.getCurrentUser();
    }

    public void updateUserProfile(User user, boolean isPhoto) {
        userRepo.updateUserProfile(user, isPhoto);
    }


    public void updatePassword(String newPass) {
        userRepo.updatePassword(newPass);
    }

    public void getSellerInfo() {
        userRepo.getSellerInfo();
    }


    public void updatePasswordByEmail(String email) {
        userRepo.updatePasswordByEmail(email);
    }


    public LiveData<Boolean> getIsLogin() {
        return userRepo.getIsLogin();
    }

    public void getUserInfo() {
        userRepo.getUserInfo();
    }

    public void getUserInfoById(String id) {
        userRepo.getUserInfoById(id);
    }

    public LiveData<Boolean> getIsCheckedCreated() {
        return userRepo.getIsCheckedCreated();
    }

    public LiveData<Boolean> getIsCheckedLogged() {
        return userRepo.getIsCheckedLogged();
    }

    public LiveData<Boolean> getIsAccountCreated() {
        return userRepo.getIsAccountCreated();
    }

    public LiveData<Boolean> getIsPasswordUpdated() {
        return userRepo.getIsPasswordUpdated();
    }

    public LiveData<Boolean> updateUserAddress(Address address) {
        return userRepo.updateUserAddress(address);
    }

    public LiveData<Boolean> updateOriginalAddress(Address address) {
        return userRepo.updateOriginalAddress(address);
    }

    public LiveData<Seller> getSellerLiveData() {
        return userRepo.getSellerLiveData();
    }

    public String getUserId() {
        return userRepo.getUserId();
    }

    public LiveData<String> getPasswordResetLinkSent() {
        return userRepo.getPasswordResetLinkSent();
    }
}
