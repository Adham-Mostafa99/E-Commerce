package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Address;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.databinding.ActivityEditInformationBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

public class EditInformationActivity extends AppCompatActivity {
    ActivityEditInformationBinding binding;
    private UserViewModel userViewModel;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();


        binding.editInfoBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userViewModel.getUserInfo();
        userViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                updateProfileData(user);
                currentUser = user;
            }
        });

        binding.editInfoSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    String addressId = binding.editInfoAddressInput.getText().toString().trim() +
                            binding.editInfoCityInput.getText().toString().trim() +
                            binding.editInfoPostalCodeInput.getText().toString().trim();

                    Address address = new Address(addressId,
                            binding.editInfoAddressInput.getText().toString().trim(),
                            binding.editInfoCityInput.getText().toString().trim(),
                            binding.editInfoPostalCodeInput.getText().toString().trim(),
                            binding.editInfoPhoneInput.getText().toString().trim());

                    String name = binding.editInfoNameInput.getText().toString().trim();
                    updateInfo(name, address);


                }
            }
        });

        userViewModel.getUpdatedProfile().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                onBackPressed();
            }
        });
    }

    private void updateInfo(String name, Address address) {
        userViewModel.updateOriginalAddress(address).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    currentUser.setName(name);
                    userViewModel.updateUserProfile(currentUser, false);
                }
            }
        });

    }

    private boolean isValid() {
        if (binding.editInfoNameInput.getText().toString().trim().isEmpty()) {
            binding.editInfoLayout0.setError("Please write your name");
            return false;
        } else if (binding.editInfoAddressInput.getText().toString().trim().isEmpty()) {
            binding.editInfoLayout1.setError("Please write address name");
            return false;
        } else if (binding.editInfoCityInput.getText().toString().trim().isEmpty()) {
            binding.editInfoLayout2.setError("Please write city");
            return false;
        } else if (binding.editInfoPostalCodeInput.getText().toString().trim().isEmpty()) {
            binding.editInfoLayout3.setError("Please write postal code");
            return false;
        } else if (binding.editInfoPhoneInput.getText().toString().trim().isEmpty()) {
            binding.editInfoLayout4.setError("Please write phone");
            return false;
        }
        return true;
    }

    private void initBinding() {
        binding = ActivityEditInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void updateProfileData(User user) {

        binding.editInfoNameInput.setText(user.getName());
        if (user.getAddress().size() > 0) {

            binding.editInfoAddressInput.setText(user.getAddress().get(0).getAddressName());
            binding.editInfoCityInput.setText(user.getAddress().get(0).getCity());
            binding.editInfoPostalCodeInput.setText(user.getAddress().get(0).getPostalCode());
            binding.editInfoPhoneInput.setText(user.getAddress().get(0).getPhone());
        }
    }
}