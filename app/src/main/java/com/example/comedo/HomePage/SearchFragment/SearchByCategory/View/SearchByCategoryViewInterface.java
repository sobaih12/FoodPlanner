package com.example.comedo.HomePage.SearchFragment.SearchByCategory.View;

import android.view.View;

import com.example.comedo.Models.MealPreviewModel;

public interface SearchByCategoryViewInterface {
    void onSuccessSearchByCategory(MealPreviewModel categoriesItemListModel);
    void onSuccessSearchByCategoryFilter(String filter);
    View getViewFromFragment();

}
