package com.example.comedo.RoomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.comedo.Models.MealModel;
import com.example.comedo.Models.PlanDetailsModel;

import java.util.List;
@Dao
public interface MealDao {
    @Query("SELECT * From MealDetails")
    LiveData<List<MealModel>> getAllMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealModel mealModel);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealPlan(PlanDetailsModel planDetailsModel);
    @Query("SELECT * FROM PlanDetails where date=:date")
    LiveData<List<PlanDetailsModel>> getPlanFromDate(String date);
    @Query("DELETE FROM PlanDetails")
    void deleteAllPlan();
    @Query("DELETE FROM MealDetails")
    void deleteAllFav();
    @Delete
    void deleteMeal(MealModel mealModel);
    @Delete
    void deleteMealPlan(PlanDetailsModel planDetailsModel);
    @Query("SELECT * FROM MealDetails WHERE idMeal= :id")
    LiveData<List<MealModel>> getMealsById(String id);

}
