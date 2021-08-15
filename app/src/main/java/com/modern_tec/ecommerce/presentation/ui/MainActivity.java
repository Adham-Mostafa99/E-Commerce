package com.modern_tec.ecommerce.presentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.data.shared_pref.RememberUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.modern_tec.ecommerce.presentation.ui.buyers.HomeActivity;
import com.modern_tec.ecommerce.presentation.ui.buyers.LoginActivity;
import com.modern_tec.ecommerce.presentation.ui.buyers.RegisterActivity;
import com.modern_tec.ecommerce.presentation.ui.seller.SellerMainActivity;

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
        intentToLogin(this);
        intentToRegister(this);

        if (isRememberUser()) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                RememberUser rememberUser = new RememberUser(this);
                Log.v("TAG", rememberUser.getType());
                if (rememberUser.getType().equals("Admins")) {
                    finish();
                    startActivity(new Intent(this, SellerMainActivity.class));
                } else if (rememberUser.getType().equals("Users")) {
                    finish();
                    startActivity(new Intent(this, HomeActivity.class));
                } else {
                    //handle none type
                }
            }
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                FirebaseAuth.getInstance().signOut();
            }
        }

    }

    private boolean isRememberUser() {
        RememberUser rememberUser = new RememberUser(this);
        return rememberUser.getState();
    }

    private void initViews() {
        loginBtn = findViewById(R.id.main_login_btn);
        signupBtn = findViewById(R.id.main_join_now_btn);
    }


    private void intentToLogin(Context context) {
        loginBtn.setOnClickListener(v -> startActivity(new Intent(context, LoginActivity.class)));
    }

    private void intentToRegister(Context context) {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RegisterActivity.class));
            }
        });
    }

    private void hideUi() {
        getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                );
    }


}