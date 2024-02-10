package com.example.comedo.HomePage.HomeFragment.Presenter;


import android.util.Log;



import com.example.comedo.HomePage.HomeFragment.View.CategoriesApiService;
import com.example.comedo.HomePage.HomeFragment.View.HomePageFragmentInterface;
import com.example.comedo.HomePage.HomeFragment.View.RandomMealApiService;

import com.example.comedo.Models.CategoriesItemListModel;


import com.example.comedo.Models.MealListModel;
import com.example.comedo.Models.MealModel;

import com.example.comedo.Models.RootArea;
import com.example.comedo.Models.RootIngredient;

import java.util.List;
import java.util.stream.Collectors;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
    List<MealModel> mealModels;
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
            }
        });
    }

    @Override
    public void onCreateViewCategories(String searchText) {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        categoriesApiService = retrofit.create(CategoriesApiService.class);
        Single<CategoriesItemListModel> categoriesSingle = categoriesApiService.getCategories();
        categoriesSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoriesItemListModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CategoriesItemListModel categoriesItemListModel) {
                        Observable.fromIterable(categoriesItemListModel.getCategories())
                                .filter(category -> category.getStrCategory().toLowerCase().contains(searchText))
                                .collect(Collectors.toList())
                                .subscribe(filteredMeals -> {
                                    homePageFragmentInterface.onSuccessCategories(filteredMeals);
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("TAG", "onError: "+e.getMessage());
                    }
                });
    }

    @Override
    public void onCreateViewAreas(String areaSearch) {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        categoriesApiService = retrofit.create(CategoriesApiService.class);
        Single<RootArea> categoriesSingle = categoriesApiService.getAreas();
        categoriesSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootArea>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RootArea categoriesItemListModel) {
                        Observable.fromIterable(categoriesItemListModel.getAreas())
                                .filter(category -> category.getStrArea().toLowerCase().contains(areaSearch))
                                .collect(Collectors.toList())
                                .subscribe(filteredMeals -> {
                                    homePageFragmentInterface.onSuccessArea(filteredMeals);
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("TAG", "onError: "+e.getMessage());
                    }
                });
    }

    @Override
    public void onCreateViewIngredients(String ingredientSearch) {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        categoriesApiService = retrofit.create(CategoriesApiService.class);
        Single<RootIngredient> categoriesSingle = categoriesApiService.getIngredients();
        categoriesSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootIngredient>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RootIngredient categoriesItemListModel) {
                        Observable.fromIterable(categoriesItemListModel.getIngredients())
                                .filter(category -> category.getStrIngredient().toLowerCase().contains(ingredientSearch))
                                .collect(Collectors.toList())
                                .subscribe(filteredMeals -> {
                                    homePageFragmentInterface.onSuccessIngredients(filteredMeals);
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("TAG", "onError: "+e.getMessage());
                    }
                });
    }
}
