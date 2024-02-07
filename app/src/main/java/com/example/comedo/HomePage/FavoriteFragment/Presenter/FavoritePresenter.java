package com.example.comedo.HomePage.FavoriteFragment.Presenter;

import android.util.Log;

import androidx.navigation.Navigation;

import com.example.comedo.HomePage.FavoriteFragment.View.FavoriteFragment;
import com.example.comedo.HomePage.FavoriteFragment.View.FavoriteFragmentDirections;
import com.example.comedo.HomePage.FavoriteFragment.View.FavoriteFragmentInterface;
import com.example.comedo.HomePage.HomeFragment.View.CategoriesApiService;
import com.example.comedo.HomePage.SearchFragment.SearchByArea.View.SearchByAreaViewDirections;
import com.example.comedo.HomePage.SearchFragment.SearchByNameView.View.SearchApiService;
import com.example.comedo.Models.MealListModel;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritePresenter implements FavoritePresenterInterface{
    SearchApiService searchApiService;
    public static final String baseUrl = "https://www.themealdb.com/api/json/v1/1/";
    Retrofit retrofit;
    FavoriteFragmentInterface favoriteFragmentInterface;

    public FavoritePresenter(FavoriteFragmentInterface favoriteFragmentInterface) {
        this.favoriteFragmentInterface = favoriteFragmentInterface;
    }

    @Override
    public void onViewCreatedSearchOnMeal(String mealName) {

        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        searchApiService = retrofit.create(SearchApiService.class);
        Single<MealListModel> mealListModelObservable = searchApiService.getSearchByName(mealName);

        mealListModelObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealListModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull MealListModel mealListModel) {
                        FavoriteFragmentDirections.ActionFavouriteFragmentToRandomMealFragment action =
                                FavoriteFragmentDirections.actionFavouriteFragmentToRandomMealFragment(mealListModel.getMeals().get(0));
                        Navigation.findNavController(favoriteFragmentInterface.getViewFromFragment()).navigate(action);
                        Log.i("TAG", "onSuccess: "+mealListModel.getMeals().size());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("TAG", "onError: "+e.getMessage());
                    }
                });
    }
}
