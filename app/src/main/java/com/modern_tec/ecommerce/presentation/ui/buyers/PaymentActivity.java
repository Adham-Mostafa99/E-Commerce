package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Address;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.databinding.ActivityPaymentBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.OrderViewModel;

import static com.modern_tec.ecommerce.presentation.ui.buyers.BuyerAddressActivity.ADDRESS_EXTRA;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;
    public static final String GOOGLE_PAYMENT = "google";
    public static final String CASH_PAYMENT = "cash";
    public static final String ADDRESS_EXTRA = "chosen address";
    public static final String PAYMENT_METHOD_EXTRA = "chosen payment";

    private Address address;

    String paymentMethod = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();

        address = (Address) getIntent().getSerializableExtra(ADDRESS_EXTRA);


        binding.googlePayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod = GOOGLE_PAYMENT;
                binding.cashBtn.setChecked(false);
            }
        });

        binding.cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod = CASH_PAYMENT;
                binding.googlePayBtn.setChecked(false);
            }
        });

        binding.paymentNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentMethod != null && address != null) {
                    startActivity(new Intent(PaymentActivity.this, CheckoutActivityJava.class)
                            .putExtra(ADDRESS_EXTRA, address)
                            .putExtra(PAYMENT_METHOD_EXTRA, paymentMethod));
                } else {
                    Toast.makeText(PaymentActivity.this, "Choose payment method.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void initBinding() {
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}