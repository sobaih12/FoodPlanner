package com.example.comedo.HomePage.HomeFragment.Presenter;


import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.comedo.HomePage.HomeFragment.View.CategoriesApiService;
import com.example.comedo.HomePage.HomeFragment.View.HomePageFragmentInterface;
import com.example.comedo.HomePage.HomeFragment.View.RandomMealApiService;
import com.example.comedo.Models.AreaModel;
import com.example.comedo.Models.CategoriesItemListModel;
import com.example.comedo.Models.CategoriesItemModel;
import com.example.comedo.Models.MealListModel;
import com.example.comedo.Models.MealModel;
import com.example.comedo.Models.RootArea;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePageFragmentPresenter implements HomePageFragmentPresenterInterface {
    public static final String baseUrl = "https://www.themealdb.com/api/json/v1/1/";
    Retrofit retrofit;
    RandomMealApiService randomApiService;
    CategoriesApiService categoriesApiService;
    LinearLayoutManager linearLayoutManager;
    List<MealModel> mealModels;
    List<CategoriesItemModel> categoriesItemModels;
    List<AreaModel> areaModels;
    HomePageFragmentInterface homePageFragmentInterface;

    public HomePageFragmentPresenter(HomePageFragmentInterface homePageFragmentInterface) {
        this.homePageFragmentInterface = homePageFragmentInterface;
    }

    @Override
    public void onCreateViewRandomMeal() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        randomApiService = retrofit.create(RandomMealApiService.class);
        Call<MealListModel> call = randomApiService.getRandomMeal();
        call.enqueue(new Callback<MealListModel>() {
            @Override
            public void onResponse(Call<MealListModel> call, Response<MealListModel> response) {
                mealModels = response.body().getMeals();
                homePageFragmentInterface.onSuccessRandomMeal(mealModels.get(0));
            }

            @Override
            public void onFailure(Call<MealListModel> call, Throwable t) {
                homePageFragmentInterface.onFailureRandomMeal(t.getMessage());
            }
        });
    }

    @Override
    public void onCreateViewCategories() {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        categoriesApiService = retrofit.create(CategoriesApiService.class);
        Call<CategoriesItemListModel> call1 = categoriesApiService.getCategories();
        call1.enqueue(new Callback<CategoriesItemListModel>() {
            @Override
            public void onResponse(Call<CategoriesItemListModel> call, Response<CategoriesItemListModel> response) {
                categoriesItemModels = response.body().getCategories();
                homePageFragmentInterface.onSuccessCategories(categoriesItemModels);
            }

            @Override
            public void onFailure(Call<CategoriesItemListModel> call, Throwable t) {
                homePageFragmentInterface.onFailureCategories(t.getMessage());

            }
        });
    }

    @Override
    public void onCreateViewAreas() {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        categoriesApiService = retrofit.create(CategoriesApiService.class);
        Call<RootArea> call1 = categoriesApiService.getAreas();
        call1.enqueue(new Callback<RootArea>() {
            @Override
            public void onResponse(Call<RootArea> call, Response<RootArea> response) {
                areaModels = response.body().getAreas();
                homePageFragmentInterface.onSuccessArea(areaModels);
            }

            @Override
            public void onFailure(Call<RootArea> call, Throwable t) {
                homePageFragmentInterface.onFailureArea(t.getMessage());

            }
        });
    }
}
