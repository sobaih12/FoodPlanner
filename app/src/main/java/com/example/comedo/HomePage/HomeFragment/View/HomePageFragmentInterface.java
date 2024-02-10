package com.example.comedo.HomePage.HomeFragment.View;

import com.example.comedo.Models.AreaModel;
import com.example.comedo.Models.CategoriesItemModel;
import com.example.comedo.Models.IngredientModel;
import com.example.comedo.Models.MealModel;

import java.util.List;

public interface HomePageFragmentInterface {
    void onSuccessRandomMeal(MealModel mealModel);
    void onSuccessCategories(List<CategoriesItemModel> categoriesItemModel);
    void onSuccessArea(List<AreaModel> areaModels);
    void onSuccessIngredients(List<IngredientModel> ingredientModels);

}
