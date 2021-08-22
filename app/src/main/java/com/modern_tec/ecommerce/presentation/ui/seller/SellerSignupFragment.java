package com.modern_tec.ecommerce.presentation.ui.seller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.modern_tec.ecommerce.core.models.Address;
import com.modern_tec.ecommerce.core.models.Seller;
import com.modern_tec.ecommerce.databinding.FragmentSignupSellerBinding;
import com.modern_tec.ecommerce.presentation.ui.LoginActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SellerSignupFragment extends Fragment {

    private FragmentSignupSellerBinding binding;
    private UserViewModel userViewModel;
    private ProgressDialog progressDialog;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentSignupSellerBinding.inflate(inflater, container, false);
        initViewModels();
        initViews();


        binding.sellerSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.sellerRegisterName.getText().toString().trim();
                String email = binding.sellerRegisterEmail.getText().toString().trim();
                String pass = binding.sellerRegisterPassword.getText().toString().trim();
                String phone = binding.sellerRegisterPhone.getText().toString().trim();
                String address = binding.sellerRegisterAddress.getText().toString().trim();
                ArrayList<Address> addressList = new ArrayList<>();
                //TODO Add Address for seller
                addressList.add(new Address("10","egypt","giza","48850","111452"));
                if (isValid(name, email, pass, phone, address)) {
                    Seller seller = new Seller(email, name, null, addressList, phone, pass);
                    userViewModel.createSellerAccount(seller);
                    showProgress("Registration");
                }
            }
        });
        binding.sellerRegisterHaveAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        userViewModel.getIsAccountCreated().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                progressDialog.dismiss();
                if (aBoolean) {
                    Toast.makeText(getContext(), "Account is created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                }
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

    private boolean isValid(String name, String email, String pass, String phone, String address) {
        if (name.isEmpty()) {
            binding.signupLayoutName.setError("write your name");
            return false;
        } else if (email.isEmpty()) {
            binding.signupLayoutEmail.setError("write your email");
            return false;
        } else if (pass.isEmpty()) {
            binding.signupLayoutPass.setError("write your password");
            return false;
        } else if (phone.isEmpty()) {
            binding.signupLayoutPhone.setError("write your phone");
            return false;
        } else if (address.isEmpty()) {
            binding.signupLayoutAddress.setError("write your address");
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
