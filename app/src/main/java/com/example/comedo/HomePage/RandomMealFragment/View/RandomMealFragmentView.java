package com.example.comedo.HomePage.RandomMealFragment.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comedo.HomePage.RandomMealFragment.Presenter.RandomMealFragmentPresenter;
import com.example.comedo.HomePage.RandomMealFragment.Presenter.RandomMealFragmentPresenterInterface;
import com.example.comedo.R;


public class RandomMealFragmentView extends Fragment implements RandomMealFragmentViewInterface{

    RandomMealFragmentPresenterInterface randomMealFragmentPresenterInterface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        randomMealFragmentPresenterInterface = new RandomMealFragmentPresenter(this);
        String mealName = RandomMealFragmentViewArgs.fromBundle(getArguments()).getMealData().getStrMeal();
        Log.i("TAG", "onViewCreated:mealName "+mealName);
    }

    @Override
    public void onSuccessRandomMeal() {

    }

    @Override
    public void onFailureRandomMeal() {

    }
}