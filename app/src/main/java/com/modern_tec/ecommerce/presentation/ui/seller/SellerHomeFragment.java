package com.modern_tec.ecommerce.presentation.ui.seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.databinding.FragmentSellerHomeBinding;
import com.modern_tec.ecommerce.presentation.adapters.SellerProductAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.ProductViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SellerHomeFragment extends Fragment {
    public static final String PRODUCT_EXTRA = "product_extra";

    FragmentSellerHomeBinding binding;
    SellerProductAdapter adapter;
    ProductViewModel productViewModel;
    UserViewModel userViewModel;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentSellerHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initViewModels();
        initAdapter();

        productViewModel.getSellerProducts(userViewModel.getUserId());

        productViewModel.getProductLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.submitList(products);
            }
        });

        return view;
    }


    private void initViewModels() {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initAdapter() {
        adapter = new SellerProductAdapter(getContext());
        binding.sellerProductRecycler.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.sellerProductRecycler.setHasFixedSize(true);
        binding.sellerProductRecycler.setAdapter(adapter);
        setOnClickItem();
    }

    private void setOnClickItem() {
        adapter.setOnItemClickListener(new SellerProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                startActivity(new Intent(getContext(), SellerMaintainProductActivity.class)
                        .putExtra(PRODUCT_EXTRA, product));
            }
        });
    }

}