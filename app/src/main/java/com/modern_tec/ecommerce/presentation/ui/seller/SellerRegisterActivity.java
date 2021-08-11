package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Seller;
import com.modern_tec.ecommerce.databinding.ActivitySellerRegisterBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

public class SellerRegisterActivity extends AppCompatActivity {
    ActivitySellerRegisterBinding binding;
    private ProgressDialog progressDialog;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initModelViews();
        initViews();

        binding.sellerRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.sellerRegisterName.getText().toString().trim();
                String email = binding.sellerRegisterEmail.getText().toString().trim();
                String pass = binding.sellerRegisterPassword.getText().toString().trim();
                String phone = binding.sellerRegisterPhone.getText().toString().trim();
                String address = binding.sellerRegisterAddress.getText().toString().trim();
                if (isValid(name, email, pass, phone, address)) {
                    Seller seller = new Seller(email, name, null, address, phone, pass);
                    userViewModel.createSellerAccount(seller);
                    showProgress("Registration");
                }
            }
        });
        binding.sellerRegisterHaveAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerRegisterActivity.this, SellerLoginActivity.class));
            }
        });

        userViewModel.getIsAccountCreated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                progressDialog.dismiss();
                if (aBoolean) {
                    Toast.makeText(SellerRegisterActivity.this, "Account is created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SellerRegisterActivity.this, SellerLoginActivity.class));
                    finish();
                }
            }
        });
    }

    private void initModelViews() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initViews() {
        progressDialog = new ProgressDialog(this);
    }

    private void initBinding() {
        binding = ActivitySellerRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private boolean isValid(String name, String email, String pass, String phone, String address) {
        if (name.isEmpty()) {
            Toast.makeText(this, "write your name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (email.isEmpty()) {
            Toast.makeText(this, "write your email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.isEmpty()) {
            Toast.makeText(this, "write your password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phone.isEmpty()) {
            Toast.makeText(this, "write your phone", Toast.LENGTH_SHORT).show();
            return false;
        } else if (address.isEmpty()) {
            Toast.makeText(this, "write your address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showProgress(String title) {
        progressDialog.setTitle(title);
        progressDialog.setMessage("Please wait, while we are checking the credentials.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
}