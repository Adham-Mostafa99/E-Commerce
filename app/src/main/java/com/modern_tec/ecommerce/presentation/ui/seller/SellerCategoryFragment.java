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

    private String menCategory = "men";
    private String womenCategory = "women";
    private String kidsCategory = "kids";

    private FragmentSellerCategoryBinding binding;
    public static final String CATEGORY_EXTRA = "category_extra";
    UserViewModel userViewModel;


    View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.category_men:
                intentToNewProductActivity(menCategory);
                break;
            case R.id.category_women:
                intentToNewProductActivity(womenCategory);
                break;
            case R.id.category_kids:
                intentToNewProductActivity(kidsCategory);
                break;
            default:
                break;

        }
    };


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        binding = FragmentSellerCategoryBinding.inflate(inflater, container, false);

        initViewModels();

        initViewsListener(onClickListener);

        return binding.getRoot();
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }


    private void initViewsListener(View.OnClickListener onClickListener) {
        binding.categoryWomen.setOnClickListener(onClickListener);
        binding.categoryMen.setOnClickListener(onClickListener);
        binding.categoryKids.setOnClickListener(onClickListener);
    }

    private void intentToNewProductActivity(String category) {
        startActivity(new Intent(getContext(), SellerAddNewProductActivity.class)
                .putExtra(CATEGORY_EXTRA, category));
    }
}