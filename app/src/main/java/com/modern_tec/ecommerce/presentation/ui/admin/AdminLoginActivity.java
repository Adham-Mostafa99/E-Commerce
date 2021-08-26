package com.modern_tec.ecommerce.presentation.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.data.shared_pref.UserType;
import com.modern_tec.ecommerce.databinding.ActivityAdminLoginBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

public class AdminLoginActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private String adminDbName="Admins";
    private ActivityAdminLoginBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();
        initViews();

        userViewModel.getIsLogin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                progressDialog.dismiss();
                if (aBoolean) {
                    UserType userType = new UserType(AdminLoginActivity.this);
                    userType.setType(adminDbName);
                    startActivity(new Intent(AdminLoginActivity.this, AdminProductActivity.class));
                    finish();
                }
            }
        });

        binding.adminLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = binding.adminLoginEmail.getText().toString().trim();
                String passStr = binding.adminLoginPassword.getText().toString().trim();

                if (isInputValid(emailStr, passStr)) {
                    showProgress("Login as Admin");
                    userViewModel.loginAdmin(emailStr, passStr);
                }
            }
        });


    }

    private void initViews() {
        progressDialog = new ProgressDialog(this);
    }

    private boolean isInputValid(String email, String pass) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please write your email...", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(pass) || pass.length() < 6) {
            Toast.makeText(this, "please write your password more than 6 letters...", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initBinding() {
        binding = ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void showProgress(String title) {
        progressDialog.setTitle(title);
        progressDialog.setMessage("Please wait, while we are checking the credentials.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

}