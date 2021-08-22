package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.databinding.ActivityConfirmationBinding;

public class ConfirmationActivity extends AppCompatActivity {
    ActivityConfirmationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intiBinding();

        binding.confirmationBackToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmationActivity.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void intiBinding() {
        binding = ActivityConfirmationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onBackPressed() {
        //NONE
    }
}