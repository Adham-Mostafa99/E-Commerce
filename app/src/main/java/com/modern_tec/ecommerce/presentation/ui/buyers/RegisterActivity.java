package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, pass;
    Button register;

    UserViewModel userViewModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.getIsCheckedCreated().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgress("Create Account");
            } else {
                progressDialog.dismiss();
            }
        });

        userViewModel.getIsAccountCreated().observe(this, aBoolean -> {
            if (aBoolean) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(v -> {
            String nameStr = name.getText().toString().trim();
            String emailStr = email.getText().toString().trim();
            String passStr = pass.getText().toString().trim();
            if (isInputValid(nameStr, emailStr, passStr))
                userViewModel.createAccount(nameStr, emailStr, passStr);
        });
    }


    private boolean isInputValid(String name, String email, String pass) {
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "please write your name...", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please write your email...", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(pass) || pass.length() < 6) {
            Toast.makeText(this, "please write your password more than 6 letters...", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private void initViews() {
        name = findViewById(R.id.user_name_register_input);
        email = findViewById(R.id.email_register_input);
        pass = findViewById(R.id.pass_register_input);
        register = findViewById(R.id.register_btn);
        progressDialog = new ProgressDialog(this);

    }

    private void showProgress(String title) {
        progressDialog.setTitle(title);
        progressDialog.setMessage("Please wait, while we are checking the credentials.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }


}