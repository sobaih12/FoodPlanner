package com.example.comedo.HomePage.SearchFragment.Presenter;

import static kotlinx.coroutines.flow.FlowKt.subscribeOn;

import android.database.Observable;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.comedo.HomePage.HomeFragment.View.CategoriesApiService;
import com.example.comedo.HomePage.HomeFragment.View.HomePageFragmentInterface;
import com.example.comedo.HomePage.HomeFragment.View.RandomMealApiService;
import com.example.comedo.HomePage.RandomMealFragment.View.RandomMealFragmentView;
import com.example.comedo.HomePage.RandomMealFragment.View.RandomMealFragmentViewInterface;
import com.example.comedo.HomePage.SearchFragment.View.SearchApiService;
import com.example.comedo.HomePage.SearchFragment.View.SearchFragmentDirections;
import com.example.comedo.HomePage.SearchFragment.View.SearchViewInterface;
import com.example.comedo.Models.CategoriesItemModel;
import com.example.comedo.Models.MealListModel;
import com.example.comedo.Models.MealModel;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchPresenter implements SearchPresenterInterface{
    public static final String baseUrl = "https://www.themealdb.com/api/json/v1/1/";
    Retrofit retrofit;
    SearchApiService searchApiService;
    LinearLayoutManager linearLayoutManager;
    List<MealModel> mealModels;
    SearchViewInterface searchViewInterface;
    RandomMealFragmentViewInterface randomMealFragmentViewInterface;

    public SearchPresenter(RandomMealFragmentViewInterface randomMealFragmentViewInterface) {
        this.randomMealFragmentViewInterface = randomMealFragmentViewInterface;
    }

    public SearchPresenter(SearchViewInterface searchViewInterface) {
        this.searchViewInterface = searchViewInterface;
    }

    @Override
    public void onViewCreatedSearch(String mealName) {

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
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
                        searchViewInterface.onSuccessSearchByMeal(mealListModel);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("TAG", "onError: "+e.getMessage());
                    }
                });
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
                        SearchFragmentDirections.ActionSearchFragmentToRandomMealFragment action = SearchFragmentDirections.actionSearchFragmentToRandomMealFragment(mealListModel.getMeals().get(0) );
                        Navigation.findNavController(searchViewInterface.getViewFromFragment()).navigate(action);
                        Log.i("TAG", "onSuccess: "+mealListModel.getMeals().size());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("TAG", "onError: "+e.getMessage());
                    }
                });
    }

}
