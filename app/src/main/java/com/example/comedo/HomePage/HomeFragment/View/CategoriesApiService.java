package com.example.comedo.HomePage.HomeFragment.View;

import com.example.comedo.Models.AreaModel;
import com.example.comedo.Models.CategoriesItemListModel;
import com.example.comedo.Models.RootArea;
import com.example.comedo.Models.RootIngredient;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesApiService {
    @GET("categories.php")
    Call<CategoriesItemListModel> getCategories();
    @GET("list.php?a=list")
    Call<RootArea> getAreas();
    @GET("list.php?i=list")
    Call<RootIngredient> getIngredients();
}
