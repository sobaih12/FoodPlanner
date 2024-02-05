package com.example.comedo.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootIngredient {
    @SerializedName("meals")
    public List<IngredientModel> ingredients;

    public List<IngredientModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }

}
