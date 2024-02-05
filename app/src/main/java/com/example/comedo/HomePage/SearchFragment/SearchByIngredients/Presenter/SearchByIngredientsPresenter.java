package com.example.comedo.HomePage.SearchFragment.SearchByIngredients.Presenter;

import android.util.Log;

import androidx.navigation.Navigation;

import com.example.comedo.HomePage.HomeFragment.View.HomePageFragmentInterface;
import com.example.comedo.HomePage.SearchFragment.SearchByArea.View.SearchByAreaViewDirections;
import com.example.comedo.HomePage.SearchFragment.SearchByArea.View.SearchByAreaViewInterface;
import com.example.comedo.HomePage.SearchFragment.SearchByIngredients.View.SearchByIngredientsViewDirections;
import com.example.comedo.HomePage.SearchFragment.SearchByIngredients.View.SearchByIngredientsViewInterface;
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

public class SearchByIngredientsPresenter implements SearchByIngredientsPresenterInterface{
    public static final String baseUrl = "https://www.themealdb.com/api/json/v1/1/";
    Retrofit retrofit;
    SearchApiService searchApiService;
    SearchByIngredientsViewInterface searchViewInterface;
    HomePageFragmentInterface homePageFragmentInterface;
    public SearchByIngredientsPresenter(SearchByIngredientsViewInterface searchViewInterface) {
        this.searchViewInterface = searchViewInterface;
    }

    public SearchByIngredientsPresenter(HomePageFragmentInterface homePageFragmentInterface) {
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
                        SearchByIngredientsViewDirections.ActionSearchByIngredientsViewToRandomMealFragment action =
                                SearchByIngredientsViewDirections.actionSearchByIngredientsViewToRandomMealFragment(mealListModel.getMeals().get(0));
                        Navigation.findNavController(searchViewInterface.getViewFromFragment()).navigate(action);
                        Log.i("TAG", "onSuccess: "+mealListModel.getMeals().size());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("TAG", "onError: "+e.getMessage());
                    }
                });
    }

    @Override
    public void onViewCreatedSearchOnIngredients(String ingredientName, String searchName) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        searchApiService = retrofit.create(SearchApiService.class);
        Single<MealPreviewModel> mealPreviewModelSingle = searchApiService.getSearchByIngredient(ingredientName);




        mealPreviewModelSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealPreviewModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull MealPreviewModel categoriesItemListModel) {
                        Observable.fromIterable(categoriesItemListModel.getMeals())
                                .filter(meal -> meal.getStrMeal().toLowerCase().contains(searchName))
                                .collect(Collectors.toList())
                                .subscribe(filteredMeals -> {
                                    Log.i("TAG", "onSuccess: filter "+filteredMeals.get(0).getStrMeal());
                                    if (searchViewInterface != null) {
                                        searchViewInterface.onSuccessSearchByIngredient( new MealPreviewModel(filteredMeals));
                                    } else {
                                        Log.i("TAG", "onSuccess: searchViewInterface is null");
                                    }
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("TAG", "onError: "+e.getMessage());
                    }
                });
    }
}
