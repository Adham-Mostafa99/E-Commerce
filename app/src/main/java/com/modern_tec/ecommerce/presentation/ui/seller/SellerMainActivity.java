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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.databinding.ActivitySellerMainBinding;
import com.modern_tec.ecommerce.presentation.ui.MainActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import org.jetbrains.annotations.NotNull;

public class SellerMainActivity extends AppCompatActivity {

    private ActivitySellerMainBinding binding;
    private UserViewModel userViewModel;
    private MutableLiveData<Fragment> currentFragment = new MutableLiveData<>();

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (!(currentFragment.getValue() instanceof SellerHomeFragment))
                        currentFragment.postValue(new SellerHomeFragment());
                    break;
                case R.id.navigation_orders:
                    if (!(currentFragment.getValue() instanceof SellerAllUsersOrdersFragment))
                        currentFragment.postValue(new SellerAllUsersOrdersFragment());
                    break;
                case R.id.navigation_add:
                    if (!(currentFragment.getValue() instanceof SellerCategoryFragment))
                        currentFragment.postValue(new SellerCategoryFragment());
                    break;
                case R.id.navigation_logout:
                    userViewModel.logOut();
                    startActivity(new Intent(SellerMainActivity.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                    return true;
            }

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();
        binding.navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        currentFragment.setValue(new SellerHomeFragment());

        currentFragment.observe(this, new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.seller_main_frame, currentFragment.getValue())
                        .commit();
            }
        });

    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initBinding() {
        binding = ActivitySellerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}