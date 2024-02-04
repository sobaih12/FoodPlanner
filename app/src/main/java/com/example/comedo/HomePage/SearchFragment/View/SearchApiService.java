package com.example.comedo.HomePage.SearchFragment.View;

import android.database.Observable;

import com.example.comedo.Models.MealListModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApiService {
    @GET("search.php")
    Single<MealListModel> getSearchByName(@Query("s") String mealName);

}
