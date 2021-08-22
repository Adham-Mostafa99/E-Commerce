package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Address;
import com.modern_tec.ecommerce.databinding.ActivityCreateAddressBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

public class CreateAddressActivity extends AppCompatActivity {
    ActivityCreateAddressBinding binding;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();

        binding.createAddressBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid())
                    addAddress();
            }
        });
    }

    private void addAddress() {
        String addressId = binding.createAddressInput.getText().toString().trim() +
                binding.createAddressCityInput.getText().toString().trim() +
                binding.createAddressPostalCodeInput.getText().toString().trim();

        Address address = new Address(addressId,
                binding.createAddressInput.getText().toString().trim(),
                binding.createAddressCityInput.getText().toString().trim(),
                binding.createAddressPostalCodeInput.getText().toString().trim(),
                binding.createAddressPhoneInput.getText().toString().trim());

        userViewModel.updateUserAddress(address).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                onBackPressed();
            }
        });
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initBinding() {
        binding = ActivityCreateAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    private boolean isValid() {
        if (binding.createAddressInput.getText().toString().trim().isEmpty()) {
            binding.createAddressLayout1.setError("Please write address name");
            return false;
        } else if (binding.createAddressCityInput.getText().toString().trim().isEmpty()) {
            binding.createAddressLayout2.setError("Please write city");
            return false;
        } else if (binding.createAddressPostalCodeInput.getText().toString().trim().isEmpty()) {
            binding.createAddressLayout3.setError("Please write postal code");
            return false;
        } else if (binding.createAddressPhoneInput.getText().toString().trim().isEmpty()) {
            binding.createAddressLayout4.setError("Please write phone");
            return false;
        }
        return true;
    }
}