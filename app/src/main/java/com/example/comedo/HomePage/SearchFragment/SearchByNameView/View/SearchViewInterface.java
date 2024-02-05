package com.example.comedo.HomePage.SearchFragment.SearchByNameView.View;

import android.view.View;

import com.example.comedo.Models.CategoriesItemListModel;
import com.example.comedo.Models.MealListModel;
import com.example.comedo.Models.MealPreviewModel;

public interface SearchViewInterface {
    void onSuccessSearchByMeal(MealListModel mealListModel);
    void onSuccessSearchByCategory(MealPreviewModel categoriesItemListModel);
    View getViewFromFragment();
}
