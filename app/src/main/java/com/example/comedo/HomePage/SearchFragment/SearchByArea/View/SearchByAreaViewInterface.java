package com.example.comedo.HomePage.SearchFragment.SearchByArea.View;

import android.view.View;

import com.example.comedo.Models.MealPreviewModel;

public interface SearchByAreaViewInterface {
    void onSuccessSearchByArea(MealPreviewModel categoriesItemListModel);
    View getViewFromFragment();
}
