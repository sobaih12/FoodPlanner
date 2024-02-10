package com.example.comedo.HomePage.SearchFragment.SearchByNameView.View;

import com.example.comedo.Models.MealListModel;
import com.example.comedo.Models.MealPreviewModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApiService {
    @GET("search.php")
    Single<MealListModel> getSearchByName(@Query("s") String mealName);
    @GET("filter.php")
    Single<MealPreviewModel> getSearchByCategory(@Query("c") String category);
    @GET("filter.php")
    Single<MealPreviewModel> getSearchByArea(@Query("a") String area);
    @GET("filter.php")
    Single<MealPreviewModel> getSearchByIngredient(@Query("i") String ingredient);

}
