package com.modern_tec.ecommerce.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.modern_tec.ecommerce.core.models.Category;
import com.modern_tec.ecommerce.data.repository.CategoryRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    CategoryRepository categoryRepository;

    public CategoryViewModel(@NonNull @NotNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
    }

    public List<Category> getCategories() {
        return categoryRepository.getCategories();
    }
}
