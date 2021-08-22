package com.modern_tec.ecommerce.presentation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.data.shared_pref.UserType;
import com.modern_tec.ecommerce.presentation.ui.buyers.HomeActivity;
import com.modern_tec.ecommerce.presentation.ui.seller.SellerMainActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

public class SplashActivity extends AppCompatActivity {
    private String UserDbName = "Users";
    private String sellerDbName = "Sellers";
    private String AdminDbName = "Admins";

    private String currentType="";


    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        hideSystemUI();
        initViewModels();


        checkIfUserLogin();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentType.equals(UserDbName)) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                } else if (currentType.equals(sellerDbName)) {
                    startActivity(new Intent(SplashActivity.this, SellerMainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 1500);

    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void checkIfUserLogin() {
        UserType userType = new UserType(this);

        if (userViewModel.getCurrentUser() != null) {
            if (userType.getType().equals(sellerDbName)) {
                currentType = sellerDbName;
            } else if (userType.getType().equals(UserDbName)) {
                currentType = UserDbName;
            } else {
                userViewModel.logOut();
            }
        }
    }


    private void hideSystemUI() {
        Window window = this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // API 30
            window.setDecorFitsSystemWindows(false);
        } else {

            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        }


    }
}