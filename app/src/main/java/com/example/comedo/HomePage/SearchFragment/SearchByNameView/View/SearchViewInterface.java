package com.example.comedo.HomePage.SearchFragment.SearchByNameView.View;

import android.view.View;

import com.example.comedo.Models.CategoriesItemListModel;
import com.example.comedo.Models.MealListModel;
import com.example.comedo.Models.MealModel;
import com.example.comedo.Models.MealPreviewModel;

import java.util.List;

public interface SearchViewInterface {
    void onSuccessSearchByMeal(List<MealModel> mealListModel);
    View getViewFromFragment();
}
