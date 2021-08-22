package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.databinding.ActivityPaymentBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.OrderViewModel;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();

        double totalPrice = getIntent().getDoubleExtra("price", 0);

        binding.paymentPrice.setText(totalPrice + " EG");

        binding.paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this, CheckoutActivityJava.class)
                        .putExtra("price", totalPrice));
            }
        });


    }


    private void initBinding() {
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}