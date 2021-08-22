package com.modern_tec.ecommerce.presentation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.databinding.ActivityLoginBinding;
import com.modern_tec.ecommerce.presentation.ui.buyers.BuyerLoginFragment;
import com.modern_tec.ecommerce.presentation.ui.seller.SellerLoginFragment;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    MutableLiveData<Fragment> currentFragment = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();

        currentFragment.setValue(new BuyerLoginFragment());
        binding.loginSwitch.selectButton(binding.buyerBtn.getId());

        binding.loginBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.buyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buyerBtn.setSelectedBgColor(
                        ContextCompat.getColor(LoginActivity.this, R.color.buyer));

                if (!(currentFragment.getValue() instanceof BuyerLoginFragment))
                    currentFragment.postValue(new BuyerLoginFragment());
            }
        });

        binding.sellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.sellerBtn.setSelectedBgColor(
                        ContextCompat.getColor(LoginActivity.this, R.color.seller));

                if (!(currentFragment.getValue() instanceof SellerLoginFragment))
                    currentFragment.postValue(new SellerLoginFragment());

            }
        });


        currentFragment.observe(this, new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.login_frame, fragment)
                        .commit();
            }
        });


    }

    private void initBinding() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}