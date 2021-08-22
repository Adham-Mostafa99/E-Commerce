package com.modern_tec.ecommerce.presentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.modern_tec.ecommerce.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();


        intentToLogin(this);
        intentToRegister(this);


    }

    private void initBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }



    private void intentToLogin(Context context) {
        binding.mainLoginBtn.setOnClickListener(v -> startActivity(new Intent(context, LoginActivity.class)));
    }

    private void intentToRegister(Context context) {
        binding.mainJoinNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RegisterActivity.class));
            }
        });
    }


}