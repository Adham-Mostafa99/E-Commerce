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
import com.modern_tec.ecommerce.presentation.adapters.CategoryAdapter;
import com.modern_tec.ecommerce.presentation.adapters.ProductAdapter;
import com.modern_tec.ecommerce.presentation.ui.MainActivity;
import com.modern_tec.ecommerce.presentation.viewmodels.CategoryViewModel;
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
    public static final String CLICKED_CATEGORY_EXTRA = "category clicked";

    private ActivityHomeBinding binding;

    private UserViewModel userViewModel;
    private ProductViewModel productViewModel;
    private CategoryViewModel categoryViewModel;

    private View headerView;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;

    private TextView userName;
    private CircleImageView userPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();
        initAdapter();
        initToolbar();
        initNavigationDrawable();

        //TODO add on click category

        userViewModel.getUserInfo();
        productViewModel.getProducts();


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
        productViewModel.getProductLiveData().

                observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> product) {
                        productAdapter.submitList(product);
                    }
                });


        binding.appBarHome.contentHome.featuredSeeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FeaturesActivity.class));
            }
        });

    }

    private void initBinding() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initAdapter() {
        productAdapter = new ProductAdapter(this);
        binding.appBarHome.contentHome.recyclerMenu.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.appBarHome.contentHome.recyclerMenu.setAdapter(productAdapter);
        binding.appBarHome.contentHome.recyclerMenu.setHasFixedSize(true);

        categoryAdapter = new CategoryAdapter();
        binding.appBarHome.contentHome.recyclerCategory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.appBarHome.contentHome.recyclerCategory.setAdapter(categoryAdapter);
        categoryAdapter.submitList(categoryViewModel.getCategories());


        onClickProductItem();
        onClickCategory();
    }

    private void onClickCategory() {
        categoryAdapter.setOnCLickItem(new CategoryAdapter.OnCLickItem() {
            @Override
            public void onclick(String name) {
                startActivity(new Intent(HomeActivity.this, FeaturesActivity.class)
                        .putExtra(CLICKED_CATEGORY_EXTRA, name));
            }
        });
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
    }


    private void initNavigationDrawable() {
        initActionBarDrawerToggle();
        binding.navView.setNavigationItemSelectedListener(this);


        headerView = binding.navView.getHeaderView(0);
        userName = headerView.findViewById(R.id.user_profile_name);
        userPhoto = headerView.findViewById(R.id.user_profile_image);
    }

    private void initToolbar() {
        setSupportActionBar(binding.appBarHome.toolbar);
        getSupportActionBar().setTitle(null);
    }


    private void initActionBarDrawerToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarHome.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
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

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        else
//            super.onBackPressed();
            Toast.makeText(this, "Exit!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.nav_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
            case R.id.nav_orders:
                startActivity(new Intent(this, OrdersActivity.class));
                break;
            case R.id.nav_search:
                startActivity(new Intent(this, SearchProductActivity.class));
                break;
            case R.id.nav_tools:
                //TODO will add later
                break;
            case R.id.nav_logout:
                startActivity(new Intent(HomeActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                userViewModel.logOut();
                break;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}