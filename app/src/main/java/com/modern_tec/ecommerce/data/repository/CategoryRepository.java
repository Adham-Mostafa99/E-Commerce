package com.modern_tec.ecommerce.data.repository;

import android.app.Application;
import android.content.Context;

import com.modern_tec.ecommerce.core.models.Category;
import com.modern_tec.ecommerce.data.database.CategoryServices;

import java.util.List;

public class CategoryRepository {

    CategoryServices categoryServices;

    public CategoryRepository(Context context) {
        categoryServices = new CategoryServices(context);
    }

    public List<Category> getCategories() {
        return categoryServices.getCategoryList();
    }
}
