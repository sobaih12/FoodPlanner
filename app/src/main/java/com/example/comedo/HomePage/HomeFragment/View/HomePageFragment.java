package com.example.comedo.HomePage.HomeFragment.View;

import android.content.Context;
import android.os.Bundle;

import com.example.comedo.HomePage.HomeFragment.Presenter.HomePageFragmentPresenter;
import com.example.comedo.HomePage.HomeFragment.Presenter.HomePageFragmentPresenterInterface;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.comedo.Models.CategoriesItemModel;
import com.example.comedo.Models.MealModel;
import com.example.comedo.R;

import java.util.List;


public class HomePageFragment extends Fragment implements HomePageFragmentInterface {


    Context context ;

    CategoriesApiService categoriesApiService;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ImageView randomImageView;
    TextView randomTextView;


    HomePageFragmentPresenterInterface homePageFragmentPresenterInterface;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view =  inflater.inflate(R.layout.fragment_home_page, container, false);
        homePageFragmentPresenterInterface =  new HomePageFragmentPresenter(this);

        recyclerView = view.findViewById(R.id.category_recycler);
        randomImageView = view.findViewById(R.id.random_image_view);
        randomTextView = view.findViewById(R.id.random_text_view);
        homePageFragmentPresenterInterface.onCreateViewRandomMeal();
        homePageFragmentPresenterInterface.onCreateViewCategories();
        return view;
    }

    @Override
    public void onSuccessRandomMeal(MealModel mealModel) {
        randomTextView.setText(mealModel.getStrMeal());
        Glide.with(getContext()).load(mealModel.getStrMealThumb())
                .apply(new RequestOptions().override(379, 235).centerCrop())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(randomImageView);

        randomImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "onClick: aaaaaaaaaaaaaa");
                HomePageFragmentDirections.ActionHomePageFragmentToRandomMealFragment action = HomePageFragmentDirections.actionHomePageFragmentToRandomMealFragment(mealModel);
                Navigation.findNavController(v).navigate(action);
            }
        });

    }

    @Override
    public void onFailureRandomMeal(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessCategories(List<CategoriesItemModel> categoriesItemModel) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(), categoriesItemModel);
        recyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public void onFailureCategories(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}