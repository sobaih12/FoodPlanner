package com.example.comedo.HomePage.HomeFragment.View;

import com.example.comedo.Models.CategoriesItemModel;
import com.example.comedo.Models.MealModel;

import java.util.List;

public interface HomePageFragmentInterface {
    void onSuccessRandomMeal(MealModel mealModel);
    void onFailureRandomMeal(String message);
    void onSuccessCategories(List<CategoriesItemModel> categoriesItemModel);
    void onFailureCategories(String message);

}