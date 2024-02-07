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

    @Delete
    void deleteMeal(MealModel mealModel);
    @Query("SELECT * From PlanDetails")
    LiveData<List<PlanDetailsModel>> getAllMealsPlan();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealPlan(PlanDetailsModel planDetailsModel);

    @Delete
    void deleteMealPlan(PlanDetailsModel planDetailsModel);
    @Query("SELECT * FROM PlanDetails where date=:date")
    LiveData<List<PlanDetailsModel>> getPlanFromDate(String date);
}
