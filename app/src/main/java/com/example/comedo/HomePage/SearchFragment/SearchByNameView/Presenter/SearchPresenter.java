package com.example.comedo.HomePage.SearchFragment.SearchByNameView.Presenter;


import androidx.navigation.Navigation;
import com.example.comedo.HomePage.SearchFragment.SearchByNameView.View.SearchApiService;
import com.example.comedo.HomePage.SearchFragment.SearchByNameView.View.SearchFragmentDirections;
import com.example.comedo.HomePage.SearchFragment.SearchByNameView.View.SearchViewInterface;
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

public class SearchPresenter implements SearchPresenterInterface{
    public static final String baseUrl = "https://www.themealdb.com/api/json/v1/1/";
    Retrofit retrofit;
    SearchApiService searchApiService;
    SearchViewInterface searchViewInterface;
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
                        searchViewInterface.onSuccessSearchByMeal(mealListModel.getMeals());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
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
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
