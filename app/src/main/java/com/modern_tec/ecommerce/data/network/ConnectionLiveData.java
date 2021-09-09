package com.modern_tec.ecommerce.data.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.HashSet;

public class ConnectionLiveData extends LiveData<Boolean> {

    private Context context;
    private HashSet<Network> validNetworks;
    private ConnectivityManager cm;

    private final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            postValue(true);
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            postValue(false);
        }
    };


    public ConnectionLiveData(Context context) {
        this.context = context;
        validNetworks = new HashSet<>();
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private void checkValidNetwork() {
        postValue(validNetworks.size() > 0);
    }

    @Override
    protected void onActive() {
        super.onActive();

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        cm.registerNetworkCallback(networkRequest, networkCallback);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        cm.unregisterNetworkCallback(networkCallback);
    }


}
