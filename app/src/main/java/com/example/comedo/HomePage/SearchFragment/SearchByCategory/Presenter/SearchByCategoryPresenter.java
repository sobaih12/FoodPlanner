package com.example.comedo.HomePage.SearchFragment.SearchByCategory.Presenter;



import androidx.navigation.Navigation;


import com.example.comedo.HomePage.HomeFragment.View.HomePageFragmentInterface;
import com.example.comedo.HomePage.SearchFragment.SearchByCategory.View.SearchByCategoryViewDirections;
import com.example.comedo.HomePage.SearchFragment.SearchByCategory.View.SearchByCategoryViewInterface;
import com.example.comedo.HomePage.SearchFragment.SearchByNameView.View.SearchApiService;
import com.example.comedo.Models.MealListModel;
import com.example.comedo.Models.MealPreviewModel;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchByCategoryPresenter implements SearchByCategoryPresenterInterface{
    public static final String baseUrl = "https://www.themealdb.com/api/json/v1/1/";
    Retrofit retrofit;
    SearchApiService searchApiService;
    SearchByCategoryViewInterface searchViewInterface;
    HomePageFragmentInterface homePageFragmentInterface;

    public SearchByCategoryPresenter(SearchByCategoryViewInterface searchViewInterface) {
        this.searchViewInterface = searchViewInterface;
    }

    public SearchByCategoryPresenter(HomePageFragmentInterface homePageFragmentInterface) {
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
                        SearchByCategoryViewDirections.ActionSearchByCategoryViewToRandomMealFragment action =
                                SearchByCategoryViewDirections.actionSearchByCategoryViewToRandomMealFragment(mealListModel.getMeals().get(0));
                        Navigation.findNavController(searchViewInterface.getViewFromFragment()).navigate(action);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
    @Override
    public void onViewCreatedSearchOnCategory(String categoryName,String searchName) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        searchApiService = retrofit.create(SearchApiService.class);

        Single<MealPreviewModel> mealPreviewModelSingle = searchApiService.getSearchByCategory(categoryName);
        mealPreviewModelSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealPreviewModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull MealPreviewModel categoriesItemListModel) {
                        searchViewInterface.onSuccessSearchByCategory(categoriesItemListModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

}
