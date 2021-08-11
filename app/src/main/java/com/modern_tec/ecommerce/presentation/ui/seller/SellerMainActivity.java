package com.modern_tec.ecommerce.presentation.ui.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.databinding.ActivitySellerMainBinding;
import com.modern_tec.ecommerce.presentation.ui.MainActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import org.jetbrains.annotations.NotNull;

public class SellerMainActivity extends AppCompatActivity {

    private ActivitySellerMainBinding binding;
    private UserViewModel userViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(SellerMainActivity.this, SellerHomeActivity.class));
                    return true;
                case R.id.navigation_orders:
                    startActivity(new Intent(SellerMainActivity.this, SellerAllUsersOrdersActivity.class));
                    return true;
                case R.id.navigation_add:
                    startActivity(new Intent(SellerMainActivity.this, SellerCategoryActivity.class));
                    return true;
                case R.id.navigation_logout:
                    userViewModel.logOut();
                    startActivity(new Intent(SellerMainActivity.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();

        binding.navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initBinding() {
        binding = ActivitySellerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}