package com.example.comedo.HomePage.HomeFragment.View;

import com.example.comedo.Models.AreaModel;
import com.example.comedo.Models.CategoriesItemListModel;
import com.example.comedo.Models.RootArea;
import com.example.comedo.Models.RootIngredient;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesApiService {
    @GET("categories.php")
    Single<CategoriesItemListModel> getCategories();
    @GET("list.php?a=list")
    Single<RootArea> getAreas();
    @GET("list.php?i=list")
    Single<RootIngredient> getIngredients();
}
