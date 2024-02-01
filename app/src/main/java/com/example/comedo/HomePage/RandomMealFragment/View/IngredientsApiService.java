package com.example.comedo.HomePage.RandomMealFragment.View;

import android.graphics.Bitmap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IngredientsApiService {
    @GET("your_endpoint_for_ingredient_image/{ingredientName}")
    Call<Bitmap> getIngredientImage(@Path("ingredientName") String ingredientName);

}
