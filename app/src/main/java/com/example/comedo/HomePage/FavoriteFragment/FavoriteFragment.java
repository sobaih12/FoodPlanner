package com.example.comedo.HomePage.FavoriteFragment;

import static com.example.comedo.HomePage.RandomMealFragment.View.RandomMealFragmentView.processMealModel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comedo.HomePage.RandomMealFragment.View.IngredientsAdapter;
import com.example.comedo.Models.MealModel;
import com.example.comedo.R;
import com.example.comedo.RoomDB.MealDataBase;

import java.util.List;

public class FavoriteFragment extends Fragment {
    RecyclerView favoriteRecyclerView;
    LinearLayoutManager linearLayoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        favoriteRecyclerView = view.findViewById(R.id.favorite_recycler_view);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        favoriteRecyclerView.setLayoutManager(linearLayoutManager);
        MealDataBase.getInstance(getContext()).getMealDao().getAllMeals().observe(getViewLifecycleOwner(), new Observer<List<MealModel>>() {
            @Override
            public void onChanged(List<MealModel> mealModels) {
                FavoriteAdapter favoriteAdapter = new FavoriteAdapter( mealModels, getContext());
                favoriteRecyclerView.setAdapter(favoriteAdapter);
            }
        });

        return view;
    }
}