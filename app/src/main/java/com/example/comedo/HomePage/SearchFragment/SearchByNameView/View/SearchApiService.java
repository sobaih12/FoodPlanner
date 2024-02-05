package com.example.comedo.HomePage.SearchFragment.SearchByNameView.View;

import android.database.Observable;

import com.example.comedo.Models.CategoriesItemListModel;
import com.example.comedo.Models.MealListModel;
import com.example.comedo.Models.MealPreviewModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApiService {
    @GET("search.php")
    Single<MealListModel> getSearchByName(@Query("s") String mealName);
    @GET("filter.php")
    Single<MealPreviewModel> getSearchByCategory(@Query("c") String category);
    @GET("filter.php")
    Single<MealListModel> getSearchByArea(@Query("a") String area);
    @GET("filter.php")
    Single<MealListModel> getSearchByIngredient(@Query("i") String ingredient);

}
