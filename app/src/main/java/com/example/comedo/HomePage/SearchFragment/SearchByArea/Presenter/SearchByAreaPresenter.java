package com.example.comedo.HomePage.SearchFragment.SearchByArea.Presenter;



import androidx.navigation.Navigation;

import com.example.comedo.HomePage.HomeFragment.View.HomePageFragmentInterface;
import com.example.comedo.HomePage.SearchFragment.SearchByArea.View.SearchByAreaViewDirections;
import com.example.comedo.HomePage.SearchFragment.SearchByArea.View.SearchByAreaViewInterface;


import com.example.comedo.HomePage.SearchFragment.SearchByNameView.View.SearchApiService;

import com.example.comedo.Models.MealListModel;
import com.example.comedo.Models.MealPreviewModel;

import java.util.stream.Collectors;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchByAreaPresenter implements SearchByAreaPresenterInterface{
    public static final String baseUrl = "https://www.themealdb.com/api/json/v1/1/";
    Retrofit retrofit;
    SearchApiService searchApiService;
    SearchByAreaViewInterface searchViewInterface;
    HomePageFragmentInterface homePageFragmentInterface;
    public SearchByAreaPresenter(SearchByAreaViewInterface searchViewInterface) {
        this.searchViewInterface = searchViewInterface;
    }

    public SearchByAreaPresenter(HomePageFragmentInterface homePageFragmentInterface) {
        this.homePageFragmentInterface = homePageFragmentInterface;
    }
    @Override
    public void onViewCreatedSearchOnMeal(String mealName) {
        Single<MealListModel> mealListModelObservable = searchApiService.getSearchByName(mealName);

        mealListModelObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealListModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    

                    @Override
                    public void onSuccess(@NonNull MealListModel mealListModel) {
                        SearchByAreaViewDirections.ActionSearchByAreaViewToRandomMealFragment action =
                                SearchByAreaViewDirections.actionSearchByAreaViewToRandomMealFragment(mealListModel.getMeals().get(0));
                        Navigation.findNavController(searchViewInterface.getViewFromFragment()).navigate(action);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
    @Override
    public void onViewCreatedSearchOnArea(String areaName, String searchName) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        searchApiService = retrofit.create(SearchApiService.class);
        Single<MealPreviewModel> mealPreviewModelSingle = searchApiService.getSearchByArea(areaName);
        mealPreviewModelSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealPreviewModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onSuccess(@NonNull MealPreviewModel categoriesItemListModel) {
                        //yasmin00--->
                        Observable.fromIterable(categoriesItemListModel.getMeals())
                                .filter(meal -> meal.getStrMeal().toLowerCase().contains(searchName))
                                .collect(Collectors.toList())
                                .subscribe(filteredMeals -> {
                                    if (searchViewInterface != null) {
                                        searchViewInterface.onSuccessSearchByArea( new MealPreviewModel(filteredMeals));
                                    } else {

                                    }
                                });
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }
}
