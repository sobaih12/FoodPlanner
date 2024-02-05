package com.example.comedo.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MealPreviewModel{

	@SerializedName("meals")
	 List<MealsItem> meals;

	public MealPreviewModel(List<MealsItem> filteredMeals) {
		this.meals = filteredMeals;
	}


	public List<MealsItem> setMeals(List<MealsItem> meals) {
		this.meals = meals;
		return meals;
	}

	public List<MealsItem> getMeals(){
		return meals;
	}
}