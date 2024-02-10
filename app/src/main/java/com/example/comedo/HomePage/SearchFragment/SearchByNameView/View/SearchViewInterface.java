package com.example.comedo.HomePage.SearchFragment.SearchByNameView.View;

import android.view.View;
import com.example.comedo.Models.MealModel;


import java.util.List;

public interface SearchViewInterface {
    void onSuccessSearchByMeal(List<MealModel> mealListModel);
    View getViewFromFragment();
}
