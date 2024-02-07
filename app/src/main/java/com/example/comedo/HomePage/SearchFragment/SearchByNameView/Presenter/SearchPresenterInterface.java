package com.example.comedo.HomePage.SearchFragment.SearchByNameView.Presenter;

import android.view.View;

public interface SearchPresenterInterface {
    void onViewCreatedSearch(String mealName);
    void onViewCreatedSearchOnMeal(String mealName);
    View getView();

}
