package com.example.comedo.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoriesItemListModel {

	@SerializedName("categories")
	private List<CategoriesItemModel> categories;

	public List<CategoriesItemModel> getCategories(){
		return categories;
	}
}