package com.example.comedo.HomePage.HomeFragment.View;

import com.example.comedo.Models.CategoriesItemListModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesApiService {
    @GET("categories.php")
    Call<CategoriesItemListModel> getCategories();
}
