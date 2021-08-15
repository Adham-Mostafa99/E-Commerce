package com.modern_tec.ecommerce.presentation.ui.seller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.databinding.ActivitySellerMainBinding;
import com.modern_tec.ecommerce.presentation.ui.MainActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import org.jetbrains.annotations.NotNull;

public class SellerMainActivity extends AppCompatActivity {

    private ActivitySellerMainBinding binding;
    private UserViewModel userViewModel;

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
            Fragment currentFragment = null;


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentFragment = new SellerHomeFragment();
                    break;
                case R.id.navigation_orders:
                    currentFragment = new SellerAllUsersOrdersFragment();
                    break;
                case R.id.navigation_add:
                    currentFragment = new SellerCategoryFragment();
                    break;
                case R.id.navigation_logout:
                    userViewModel.logOut();
                    startActivity(new Intent(SellerMainActivity.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                    return true;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.seller_main_frame, currentFragment)
                    .commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();
        binding.navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.seller_main_frame, new SellerHomeFragment())
                .commit();

    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initBinding() {
        binding = ActivitySellerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}