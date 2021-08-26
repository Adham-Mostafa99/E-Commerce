package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.modern_tec.ecommerce.core.models.Address;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.databinding.ActivityBuyerAddressBinding;
import com.modern_tec.ecommerce.presentation.adapters.AddressAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

public class BuyerAddressActivity extends AppCompatActivity {
    public static final String ADDRESS_EXTRA = "chosen address";
    public static final int CREATE_ADDRESS_CODE = 1;
    private ActivityBuyerAddressBinding binding;
    private AddressAdapter adapter;
    private UserViewModel userViewModel;
    private Address chosenAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();
        initViewModels();

        userViewModel.getUserInfo();

        userViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                adapter.submitList(user.getAddress());
            }
        });//TODO may set getUserInfo in onRefresh or like it

        binding.addressAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyerAddressActivity.this, CreateAddressActivity.class));
            }
        });

        binding.addressBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.continueToPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chosenAddress != null)
                    startActivity(new Intent(BuyerAddressActivity.this, PaymentActivity.class)
                                    .putExtra(ADDRESS_EXTRA, chosenAddress));
                else
                    Toast.makeText(BuyerAddressActivity.this, "Please choose address", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initAdapter() {
        adapter = new AddressAdapter();
        binding.addressRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.addressRecycler.setAdapter(adapter);
        onClickItem();
    }

    private void initBinding() {
        binding = ActivityBuyerAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void onClickItem() {
        adapter.setOnClickItem(new AddressAdapter.OnClickItem() {
            @Override
            public void onClick(Address address) {
                chosenAddress = address;
            }
        });
    }
}