package com.modern_tec.ecommerce.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.modern_tec.ecommerce.core.models.Address;
import com.modern_tec.ecommerce.core.models.Seller;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.data.database.Account;

public class UserRepo {

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

    public LiveData<Boolean> loginSeller(String email, String pass) {
        return account.loginSeller(email, pass);
    }

    public void loginAdmin(String email, String pass) {
        account.loginAdmin(email, pass);
    }

    public FirebaseUser getCurrentUser() {
        return account.getCurrentUser();
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

    public LiveData<Boolean> updateUserAddress(Address address) {
        return account.updateUserAddress(address);
    }

    public LiveData<Boolean> updateOriginalAddress(Address address) {
        return account.updateOriginalAddress(address);
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


    public void getUserInfoById(String id) {
        account.getUserInfoById(id);
    }
}
