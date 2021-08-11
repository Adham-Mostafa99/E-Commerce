package com.modern_tec.ecommerce.presentation.ui.buyers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.databinding.ActivityHomeBinding;
import com.modern_tec.ecommerce.presentation.adapters.ProductAdapter;
import com.modern_tec.ecommerce.presentation.ui.MainActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String CLICKED_ITEM_EXTRA = "clicked";

    private ActivityHomeBinding binding;

    private UserViewModel userViewModel;
    private ProductViewModel productViewModel;

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private View headerView;
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView;

    private TextView userName;
    private CircleImageView userPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViews();
        initAdapter();


        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");


        initActionBarDrawerToggle();
        navigationView.setNavigationItemSelectedListener(this);


        headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.user_profile_name);
        userPhoto = headerView.findViewById(R.id.user_profile_image);
        userViewModel.getUserInfo();

        userViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    userName.setText(user.getName());
                    Glide.with(getApplicationContext())
                            .load(user.getPhotoUrl())
                            .placeholder(R.drawable.profile)
                            .into(userPhoto);
                }
            }
        });

        productViewModel.getProducts();

        productViewModel.getProductLiveData().

                observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> product) {
                        productAdapter.submitList(product);
                    }
                });

        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });

        onClickProductItem();

    }

    private void onClickProductItem() {
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                startActivity(new Intent(HomeActivity.this, ProductDetailsActivity.class)
                        .putExtra(CLICKED_ITEM_EXTRA, product));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("TAG", "USER UPDATED");

        userViewModel.getUserInfo();
    }

    private void initBinding() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initViews() {
        drawer = binding.drawerLayout;
        toolbar = binding.appBarHome.toolbar;
        navigationView = binding.navView;
        recyclerView = findViewById(R.id.recycler_menu);

    }

    private void initAdapter() {
        productAdapter = new ProductAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initActionBarDrawerToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
//            super.onBackPressed();
            Toast.makeText(this, "Exit!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
            case R.id.nav_orders:
                startActivity(new Intent(this,OrdersActivity.class));
                break;
            case R.id.nav_search:
                startActivity(new Intent(this, SearchProductActivity.class));
                break;
            case R.id.nav_categories:
                //TODO add catigory
                break;
            case R.id.nav_tools:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_logout:
                startActivity(new Intent(HomeActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                userViewModel.logOut();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}