package com.example.comedo.HomePage.FavoriteFragment.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comedo.HomePage.FavoriteFragment.Presenter.FavoritePresenter;
import com.example.comedo.HomePage.FavoriteFragment.Presenter.FavoritePresenterInterface;
import com.example.comedo.Models.MealModel;
import com.example.comedo.R;
import com.example.comedo.RoomDB.MealDataBase;

import java.util.List;

public class FavoriteFragment extends Fragment implements OnFavoriteClickListener,FavoriteFragmentInterface {
    RecyclerView favoriteRecyclerView;
    LinearLayoutManager linearLayoutManager;
    FavoritePresenterInterface favoritePresenterInterface;
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
        favoritePresenterInterface = new FavoritePresenter(this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        favoriteRecyclerView.setLayoutManager(linearLayoutManager);
        MealDataBase.getInstance(getContext()).getMealDao().getAllMeals().observe(getViewLifecycleOwner(), new Observer<List<MealModel>>() {
            @Override
            public void onChanged(List<MealModel> mealModels) {
                ViewData(mealModels);
            }
        });

        return view;
    }

    @Override
    public void onFavoriteClickListener(String mealName) {
        favoritePresenterInterface.onViewCreatedSearchOnMeal(mealName);
    }

    @Override
    public View getViewFromFragment() {
        View view;
        view =getView();
        return view;
    }
    void ViewData(List<MealModel> mealModels){
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(mealModels, getContext(),this);
        favoriteRecyclerView.setAdapter(favoriteAdapter);
    }
}