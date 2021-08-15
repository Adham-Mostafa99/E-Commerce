package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.modern_tec.ecommerce.data.shared_pref.RememberUser;
import com.modern_tec.ecommerce.databinding.ActivitySellerLoginBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import java.security.acl.Owner;

public class SellerLoginActivity extends AppCompatActivity {
    ActivitySellerLoginBinding binding;
    private ProgressDialog progressDialog;
    UserViewModel userViewModel;

    LifecycleOwner lifecycleOwner = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();
        initViews();

        binding.sellerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.sellerLoginEmail.getText().toString().trim();
                String password = binding.sellerLoginPassword.getText().toString().trim();
                if (isValid(email, password)) {
                    sellerLoginAction(email, password);
                    showProgress("Login");
                }
            }
        });
    }

    private void sellerLoginAction(String email, String password) {
        userViewModel.loginSeller(email, password).observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                progressDialog.dismiss();
                if (aBoolean) {
                    RememberUser rememberUser = new RememberUser(getApplicationContext());
                    rememberUser.setData(true, "Admins");
                    startActivity(new Intent(SellerLoginActivity.this, SellerMainActivity.class));
                    finish();
                }
            }
        });
    }

    private void initViews() {
        progressDialog = new ProgressDialog(this);
    }

    private boolean isValid(String email, String password) {

        if (email.isEmpty()) {
            Toast.makeText(this, "write your email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "write your password", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initBinding() {
        binding = ActivitySellerLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void showProgress(String title) {
        progressDialog.setTitle(title);
        progressDialog.setMessage("Please wait, while we are checking the credentials.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
}