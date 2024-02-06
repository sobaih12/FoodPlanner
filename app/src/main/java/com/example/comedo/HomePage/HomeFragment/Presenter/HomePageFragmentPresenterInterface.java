package com.example.comedo.HomePage.HomeFragment.Presenter;

public interface HomePageFragmentPresenterInterface {
    void onCreateViewRandomMeal();
    void onCreateViewCategories(String searchText);
    void onCreateViewAreas(String areaSearch);
    void onCreateViewIngredients(String ingredientsSearch);
}
