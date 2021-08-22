package com.modern_tec.ecommerce.data.database;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryServices {
    List<Category> categoryList = new ArrayList<>();
    Context context;

    public CategoryServices(Context context) {
        this.context = context;
        initCategories();
    }

    public CategoryServices() {
        initCategories();
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    private void initCategories() {
        categoryList.add(new Category("Men"
                , ContextCompat.getDrawable(context, R.drawable.men_image)
                , ContextCompat.getDrawable(context, R.drawable.category_item_men_color)));
        categoryList.add(new Category("Women"
                , ContextCompat.getDrawable(context, R.drawable.women_image)
                , ContextCompat.getDrawable(context, R.drawable.category_item_women_color)));
        categoryList.add(new Category("Kids"
                , ContextCompat.getDrawable(context, R.drawable.kids_image)
                , ContextCompat.getDrawable(context, R.drawable.category_item_kids_color)));
    }
}
