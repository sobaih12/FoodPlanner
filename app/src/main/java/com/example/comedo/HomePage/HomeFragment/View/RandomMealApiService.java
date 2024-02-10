package com.example.comedo.HomePage.HomeFragment.View;


import com.example.comedo.Models.MealListModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomMealApiService {
    @GET("random.php")
    Call<MealListModel> getRandomMeal() ;
}
