package com.example.comedo.Models;

public class IngredientItemModel {
    private String ingredient;
    private String measure;

    public IngredientItemModel(String ingredient, String measure) {
        this.ingredient = ingredient;
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getMeasure() {
        return measure;
    }

}
