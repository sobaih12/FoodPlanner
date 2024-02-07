package com.example.comedo.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.comedo.Models.MealModel;
import com.example.comedo.Models.PlanDetailsModel;

@Database(entities = {MealModel.class, PlanDetailsModel.class}, version = 1)
public abstract class MealDataBase extends RoomDatabase {
    public abstract MealDao getMealDao();
    private static MealDataBase instance;
    public static synchronized MealDataBase getInstance(Context context){
        if(instance == null){
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), MealDataBase.class, "favoriteMeals")
                    .build();
        }
        return instance;
    }

}
