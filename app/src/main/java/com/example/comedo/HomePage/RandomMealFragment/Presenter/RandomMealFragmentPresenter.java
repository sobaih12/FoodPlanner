package com.example.comedo.HomePage.RandomMealFragment.Presenter;

import com.example.comedo.HomePage.RandomMealFragment.View.RandomMealFragmentViewInterface;

public class RandomMealFragmentPresenter implements RandomMealFragmentPresenterInterface{
    RandomMealFragmentViewInterface randomMealFragmentViewInterface;

    public RandomMealFragmentPresenter(RandomMealFragmentViewInterface randomMealFragmentViewInterface) {
        this.randomMealFragmentViewInterface = randomMealFragmentViewInterface;
    }

    @Override
    public void onCreateViewRandomMeal() {

    }
}
