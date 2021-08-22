package com.modern_tec.ecommerce.presentation.ui.buyers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.modern_tec.ecommerce.data.shared_pref.UserType;
import com.modern_tec.ecommerce.databinding.FragmentLoginBuyerBinding;
import com.modern_tec.ecommerce.databinding.FragmentSignupBuyerBinding;
import com.modern_tec.ecommerce.presentation.ui.LoginActivity;
import com.modern_tec.ecommerce.presentation.ui.RegisterActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import org.jetbrains.annotations.NotNull;

public class BuyerSignupFragment extends Fragment {

    private FragmentSignupBuyerBinding binding;
    private UserViewModel userViewModel;
    private ProgressDialog progressDialog;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentSignupBuyerBinding.inflate(inflater, container, false);
        initViewModels();
        initViews();

        userViewModel.getIsCheckedCreated().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showProgress("Create Account");
            } else {
                progressDialog.dismiss();
            }
        });

        userViewModel.getIsAccountCreated().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        binding.signupBtn.setOnClickListener(v -> {
            String nameStr = binding.userNameRegisterInput.getText().toString().trim();
            String emailStr = binding.emailRegisterInput.getText().toString().trim();
            String passStr = binding.passRegisterInput.getText().toString().trim();
            if (isInputValid(nameStr, emailStr, passStr))
                userViewModel.createAccount(nameStr, emailStr, passStr);
        });

        binding.signupAlreadyHaveAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return binding.getRoot();
    }

    private void initViews() {
        progressDialog = new ProgressDialog(getContext());
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private boolean isInputValid(String name, String email, String pass) {
        if (TextUtils.isEmpty(name)) {
            binding.signupLayoutName.setError("please write your name...");
            return false;
        } else if (TextUtils.isEmpty(email)) {
            binding.signupLayoutEmail.setError("please write your email...");
            return false;
        } else if (TextUtils.isEmpty(pass) || pass.length() < 6) {
            binding.signupLayoutPass.setError( "please write your password more than 6 letters...");
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

}
