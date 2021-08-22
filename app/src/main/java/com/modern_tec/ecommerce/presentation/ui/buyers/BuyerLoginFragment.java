package com.modern_tec.ecommerce.presentation.ui.buyers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.modern_tec.ecommerce.data.shared_pref.UserType;
import com.modern_tec.ecommerce.databinding.FragmentLoginBuyerBinding;
import com.modern_tec.ecommerce.presentation.ui.ForgetPasswordAlertDialog;
import com.modern_tec.ecommerce.presentation.ui.RegisterActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import org.jetbrains.annotations.NotNull;

public class BuyerLoginFragment extends Fragment {
    private FragmentLoginBuyerBinding binding;
    private UserViewModel userViewModel;
    private ProgressDialog progressDialog;

    private final String UserDbName = "Users";


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBuyerBinding.inflate(inflater, container, false);
        initViewModels();
        initViews();


        loginBtn();
        forgetBtn();

        userViewModel.getIsLogin().observe(getViewLifecycleOwner(), aBoolean -> {
            progressDialog.dismiss();
            if (aBoolean) {
                UserType userType = new UserType(getContext());
                userType.setType(UserDbName);
                startActivity(new Intent(getContext(), HomeActivity.class));
                getActivity().finish();
            }
        });


        userViewModel.getIsPasswordUpdated().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(getContext(), "Password is Updated.", Toast.LENGTH_SHORT).show();
            }
        });

        userViewModel.getPasswordResetLinkSent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

        binding.loginNotHaveAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RegisterActivity.class));
            }
        });


        return binding.getRoot();
    }

    private void forgetBtn() {
        binding.forgetPassLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswordAlertDialog alertDialog = new ForgetPasswordAlertDialog(getContext());

                alertDialog.createAlertDialog("Reset Password"
                        , new ForgetPasswordAlertDialog.OnClickOkListener() {
                            @Override
                            public void onClick(String verifiedEmail) {
                                userViewModel.updatePasswordByEmail(verifiedEmail);
                            }
                        });
            }
        });


    }

    private void initViews() {
        progressDialog = new ProgressDialog(getContext());
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void loginBtn() {
        binding.loginBtn.setOnClickListener(v -> {
            String emailStr = binding.emailLoginInput.getText().toString().trim();
            String passStr = binding.passLoginInput.getText().toString().trim();

            if (isInputValid(emailStr, passStr)) {
                showProgress("Login Account");
                userViewModel.loginUser(emailStr, passStr);
            }

        });
    }

    private boolean isInputValid(String email, String pass) {
        if (TextUtils.isEmpty(email)) {
            binding.loginLayoutEmail.setError("please write your email...");
            return false;
        } else if (TextUtils.isEmpty(pass) || pass.length() < 6) {
            binding.loginLayoutPass.setError("please write your password more than 6 letters...");
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
