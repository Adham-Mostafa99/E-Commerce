package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.data.shared_pref.RememberUser;
import com.modern_tec.ecommerce.presentation.ui.seller.SellerRegisterActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;
import com.rey.material.widget.CheckBox;

public class LoginActivity extends AppCompatActivity {

    EditText email, pass;
    TextView forgetPassBtn, adminPanelLink;
    Button loginBtn;
    CheckBox remember_box;
    UserViewModel userViewModel;

    boolean state;
    private ProgressDialog progressDialog;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);




        userViewModel.getIsLogin().observe(this, aBoolean -> {
            progressDialog.dismiss();
            if (aBoolean) {
                RememberUser rememberUser = new RememberUser(getApplicationContext());
                rememberUser.setData(state, parentDbName);
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        });
        userViewModel.getIsPasswordUpdated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(LoginActivity.this, "Password is Updated.", Toast.LENGTH_SHORT).show();
            }
        });

        userViewModel.getPasswordResetLinkSent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });


        loginBtn.setOnClickListener(v -> {
            String emailStr = email.getText().toString().trim();
            String passStr = pass.getText().toString().trim();
            state = remember_box.isChecked();

            if (isInputValid(emailStr, passStr)) {
                showProgress("Login Account");
                userViewModel.loginUser(emailStr, passStr);
            }

        });

        adminPanelLink.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SellerRegisterActivity.class));
        });


        forgetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialog("Reset Password");
            }
        });

    }

    private void initViews() {
        email = findViewById(R.id.email_login_input);
        pass = findViewById(R.id.pass_login_input);
        forgetPassBtn = findViewById(R.id.forget_pass_link);
        loginBtn = findViewById(R.id.login_btn);
        remember_box = findViewById(R.id.remember_me_chkbx);
        adminPanelLink = findViewById(R.id.admin_panel_link);
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

    private void showProgress(String title) {
        progressDialog.setTitle(title);
        progressDialog.setMessage("Please wait, while we are checking the credentials.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void createAlertDialog(String title) {
        EditText emailEditText = new EditText(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(15, 5, 15, 5);
        emailEditText.setLayoutParams(params);
        emailEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage("Please enter your email to sent reset link to it.")
                .setView(emailEditText)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String email = emailEditText.getText().toString().trim();
                        if (!email.isEmpty()) {
                            userViewModel.updatePasswordByEmail(email);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }
}