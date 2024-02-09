package com.example.comedo.HomePage.FavoriteFragment.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comedo.HomePage.FavoriteFragment.Presenter.FavoritePresenter;
import com.example.comedo.HomePage.FavoriteFragment.Presenter.FavoritePresenterInterface;
import com.example.comedo.Models.MealModel;
import com.example.comedo.R;
import com.example.comedo.RoomDB.MealDataBase;
import com.example.comedo.SplashScreen.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class FavoriteFragment extends Fragment implements OnFavoriteClickListener,FavoriteFragmentInterface {
    RecyclerView favoriteRecyclerView;
    LinearLayoutManager linearLayoutManager;
    FavoritePresenterInterface favoritePresenterInterface;
    ImageView logoutImage;
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
        logoutImage = view.findViewById(R.id.logout_image);
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
        logoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to sign out?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        new Thread(() -> {
                            MealDataBase.getInstance(getContext()).getMealDao().deleteAllFav();
                            MealDataBase.getInstance(getContext()).getMealDao().deleteAllPlan();
                        }).start();
                        Intent intent = new Intent(getActivity(), SplashActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onFavoriteClickListener(MealModel mealName) {
        FavoriteFragmentDirections.ActionFavouriteFragmentToRandomMealFragment action =
                FavoriteFragmentDirections.actionFavouriteFragmentToRandomMealFragment(mealName);
        Navigation.findNavController(getView()).navigate(action);
//        favoritePresenterInterface.onViewCreatedSearchOnMeal(mealName);
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