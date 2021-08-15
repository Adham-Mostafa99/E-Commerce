package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.databinding.FragmentSellerCategoryBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import org.jetbrains.annotations.NotNull;

public class SellerCategoryFragment extends Fragment {

    private FragmentSellerCategoryBinding binding;
    public static final String CATEGORY_EXTRA = "category_extra";

    ImageView tShirts, sports, femaleAresses, sweather;
    ImageView glasses, pursesBags, hats, shoess;
    ImageView headphoness, laptops, watches, mobiles;

    UserViewModel userViewModel;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        binding = FragmentSellerCategoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        initViews();
        initViewModels();

        View.OnClickListener onClickListener = v -> {
            switch (v.getId()) {
                case R.id.t_shirts:
                    intentToNewProductActivity("t_shirts");
                    break;
                case R.id.sports_t_shirts:
                    intentToNewProductActivity("sports_t_shirts");
                    break;
                case R.id.female_dresses:
                    intentToNewProductActivity("female_dresses");
                    break;
                case R.id.sweather:
                    intentToNewProductActivity("sweather");
                    break;
                case R.id.glasses:
                    intentToNewProductActivity("glasses");
                    break;
                case R.id.purses_bags:
                    intentToNewProductActivity("purses_bags");
                    break;
                case R.id.hats:
                    intentToNewProductActivity("hats");
                    break;
                case R.id.shoess:
                    intentToNewProductActivity("shoess");
                    break;
                case R.id.headphoness:
                    intentToNewProductActivity("headphoness");
                    break;
                case R.id.laptops:
                    intentToNewProductActivity("laptops");
                    break;
                case R.id.watches:
                    intentToNewProductActivity("watches");
                    break;
                case R.id.mobiles:
                    intentToNewProductActivity("mobiles");
                    break;
                default:
                    break;

            }
        };
        initViewsListener(onClickListener);

        return view;
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initViews() {
        tShirts = binding.tShirts;
        sports = binding.sportsTShirts;
        femaleAresses = binding.femaleDresses;
        sweather = binding.sweather;
        glasses = binding.glasses;
        pursesBags = binding.pursesBags;
        hats = binding.hats;
        headphoness = binding.headphoness;
        laptops = binding.laptops;
        watches = binding.watches;
        mobiles = binding.mobiles;
        shoess = binding.shoess;
    }

    private void initViewsListener(View.OnClickListener onClickListener) {
        tShirts.setOnClickListener(onClickListener);
        sports.setOnClickListener(onClickListener);
        femaleAresses.setOnClickListener(onClickListener);
        sweather.setOnClickListener(onClickListener);
        glasses.setOnClickListener(onClickListener);
        pursesBags.setOnClickListener(onClickListener);
        hats.setOnClickListener(onClickListener);
        headphoness.setOnClickListener(onClickListener);
        laptops.setOnClickListener(onClickListener);
        watches.setOnClickListener(onClickListener);
        mobiles.setOnClickListener(onClickListener);
        shoess.setOnClickListener(onClickListener);
    }

    private void intentToNewProductActivity(String category) {
        startActivity(new Intent(getContext(), SellerAddNewProductActivity.class)
                .putExtra(CATEGORY_EXTRA, category));
    }
}