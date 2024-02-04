package com.example.comedo.HomePage.SearchFragment.View;

import android.view.View;

import com.example.comedo.Models.MealListModel;

public interface SearchViewInterface {
    void onSuccessSearchByMeal(MealListModel mealListModel);
    View getViewFromFragment();
}
