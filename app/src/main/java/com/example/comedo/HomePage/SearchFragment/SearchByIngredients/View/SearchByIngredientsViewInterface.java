package com.example.comedo.HomePage.SearchFragment.SearchByIngredients.View;

import android.view.View;

import com.example.comedo.Models.MealPreviewModel;

public interface SearchByIngredientsViewInterface {
    void onSuccessSearchByIngredient(MealPreviewModel categoriesItemListModel);
    View getViewFromFragment();
}
