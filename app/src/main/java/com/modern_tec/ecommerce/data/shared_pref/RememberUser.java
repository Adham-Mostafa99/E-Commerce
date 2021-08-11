package com.modern_tec.ecommerce.data.shared_pref;

import android.content.Context;
import android.content.SharedPreferences;

public class RememberUser {
    private static final String MY_PREFS_NAME = "Remember_Pref";
    private final Context context;
    SharedPreferences.Editor editor;

    public RememberUser(Context context) {
        this.context = context;
        editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
    }

    public void setData(boolean value, String type) {
        editor.putBoolean("state", value);
        editor.putString("type", type);
        editor.apply();
    }

    public boolean getState() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean("state", false);

    }

    public String getType() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString("type", "none");

    }

    public void deleteShared() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
