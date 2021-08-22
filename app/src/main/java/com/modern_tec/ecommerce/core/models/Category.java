package com.modern_tec.ecommerce.core.models;

import android.graphics.drawable.Drawable;

import androidx.drawerlayout.widget.DrawerLayout;

public class Category {
    private String text;
    private Drawable image;
    private Drawable color;

    public Category(String text, Drawable image, Drawable color) {
        this.text = text;
        this.image = image;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public Drawable getImage() {
        return image;
    }

    public Drawable getColor() {
        return color;
    }
}
