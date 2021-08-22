package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.databinding.ActivityUserProductsBinding;
import com.modern_tec.ecommerce.presentation.adapters.CartAdapter;
import com.modern_tec.ecommerce.presentation.adapters.OrderProductsAdapter;

import java.util.List;

import static com.modern_tec.ecommerce.presentation.ui.buyers.UnShippedOrders.PRODUCT_LIST;

public class UserOrderProductsActivity extends AppCompatActivity {
    ActivityUserProductsBinding binding;

    private OrderProductsAdapter orderProductsAdapter;

    private List<CartProduct> cartProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();

        binding.userProductBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cartProductList = getIntent().getParcelableArrayListExtra(PRODUCT_LIST);

        if (cartProductList.size() > 0) {
            orderProductsAdapter.submitList(cartProductList);
        }


    }

    private void initAdapter() {
        orderProductsAdapter = new OrderProductsAdapter();
        binding.userOrderProductsRecycler.setHasFixedSize(true);
        binding.userOrderProductsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.userOrderProductsRecycler.setAdapter(orderProductsAdapter);
    }

    private void initBinding() {
        binding = ActivityUserProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}