package com.modern_tec.ecommerce.presentation.ui;

import android.app.Application;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.modern_tec.ecommerce.data.network.ConnectionLiveData;
import com.modern_tec.ecommerce.presentation.ui.MainActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(this);
        connectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    networkIsConnect();
                } else {
                    networkIsNotConnect();
                }
            }
        });
    }

    protected abstract void networkIsConnect();

    protected abstract void networkIsNotConnect();
}
