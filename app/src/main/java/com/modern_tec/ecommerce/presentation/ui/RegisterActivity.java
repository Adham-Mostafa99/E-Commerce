package com.modern_tec.ecommerce.presentation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.databinding.ActivityRegisterBinding;
import com.modern_tec.ecommerce.presentation.ui.buyers.BuyerSignupFragment;
import com.modern_tec.ecommerce.presentation.ui.seller.SellerSignupFragment;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    MutableLiveData<Fragment> currentFragment = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniBinding();

        currentFragment.setValue(new BuyerSignupFragment());
        binding.signupSwitch.selectButton(binding.buyerBtn.getId());

        binding.signupBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.buyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buyerBtn.setSelectedBgColor(
                        ContextCompat.getColor(RegisterActivity.this, R.color.buyer));

                if (!(currentFragment.getValue() instanceof BuyerSignupFragment))
                    currentFragment.postValue(new BuyerSignupFragment());
            }
        });

        binding.sellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.sellerBtn.setSelectedBgColor(
                        ContextCompat.getColor(RegisterActivity.this, R.color.seller));

                if (!(currentFragment.getValue() instanceof SellerSignupFragment))
                    currentFragment.postValue(new SellerSignupFragment());

            }
        });


        currentFragment.observe(this, new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.signup_frame, fragment)
                        .commit();
            }
        });

    }

    private void iniBinding() {
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}