package com.modern_tec.ecommerce.presentation.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.modern_tec.ecommerce.core.models.Seller;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.data.database.Account;

public class UserRepo {
    private LiveData<Boolean> isLogin;
    private MutableLiveData<Boolean> isCheckedCreated;
    private MutableLiveData<Boolean> isCheckedLogged;
    private LiveData<Boolean> isAccountCreated;
    private LiveData<User> userLiveData;
    private LiveData<User> updatedProfile;


    Account account;

    public UserRepo(Application application) {
        account = new Account(application);
    }

    public LiveData<User> getUpdatedProfile() {
        return account.getUpdatedProfile();
    }

    public void loginUser(String email, String pass) {
        account.loginUser(email, pass);
    }

    public void loginSeller(String email, String pass) {
        account.loginSeller(email, pass);
    }

    public void createSellerAccount(Seller seller) {
        account.createSellerAccount(seller);
    }

    public void logOut() {
        account.logOutAccount();
    }

    public void createAccount(String name, String email, String pass) {
        account.createAccount(name, email, pass);
    }

    public void updateUserProfile(User user, boolean isPhoto) {
        account.updateUserProfile(user, isPhoto);
    }

    public void getSellerInfo() {
        account.getSellerInfo();
    }

    public void updatePassword(String newPass) {
        account.updatePassword(newPass);
    }

    public void updatePasswordByEmail(String email) {
        account.updatePasswordByEmail(email);
    }

    public LiveData<Boolean> getIsLogin() {
        return account.getIsLogin();
    }

    public LiveData<Boolean> getIsCheckedCreated() {
        return account.getIsCheckedCreated();
    }

    public LiveData<Boolean> getIsCheckedLogged() {
        return account.getIsCheckedLogged();
    }

    public LiveData<Boolean> getIsAccountCreated() {
        return account.getIsAccountCreated();
    }

    public LiveData<Boolean> getIsPasswordUpdated() {
        return account.getIsPasswordUpdated();
    }

    public LiveData<User> getUserLiveData() {
        return account.getUserMutableLiveData();
    }

    public LiveData<Seller> getSellerLiveData() {
        return account.getSellerMutableLiveData();
    }

    public LiveData<String> getPasswordResetLinkSent() {
        return account.getPasswordResetLinkSent();
    }

    public String getUserId() {
        return account.getUserId();
    }


    public void getUserInfo() {
        account.getUserInfo();
    }
}
