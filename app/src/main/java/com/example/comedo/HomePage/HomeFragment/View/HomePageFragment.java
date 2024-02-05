package com.example.comedo.HomePage.HomeFragment.View;

import android.content.Context;
import android.os.Bundle;

import com.example.comedo.HomePage.HomeFragment.Presenter.HomePageFragmentPresenter;
import com.example.comedo.HomePage.HomeFragment.Presenter.HomePageFragmentPresenterInterface;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.comedo.HomePage.SearchFragment.SearchByArea.Presenter.SearchByAreaPresenter;
import com.example.comedo.HomePage.SearchFragment.SearchByArea.Presenter.SearchByAreaPresenterInterface;
import com.example.comedo.HomePage.SearchFragment.SearchByCategory.Presenter.SearchByCategoryPresenter;
import com.example.comedo.HomePage.SearchFragment.SearchByCategory.Presenter.SearchByCategoryPresenterInterface;
import com.example.comedo.HomePage.SearchFragment.SearchByNameView.Presenter.SearchPresenterInterface;
import com.example.comedo.Models.AreaModel;
import com.example.comedo.Models.CategoriesItemModel;
import com.example.comedo.Models.MealModel;
import com.example.comedo.R;

import java.util.List;


public class HomePageFragment extends Fragment implements HomePageFragmentInterface,OnCategoryClickListener ,OnAreaClickListener{


    Context context ;

    CategoriesApiService categoriesApiService;

    RecyclerView categoryRecyclerView;
    RecyclerView areaRecycler;
    LinearLayoutManager linearLayoutManager;
    ImageView randomImageView;
    TextView randomTextView;



    HomePageFragmentPresenterInterface homePageFragmentPresenterInterface;
    SearchByCategoryPresenterInterface searchByCategoryPresenterInterface;
    SearchByAreaPresenterInterface searchByAreaPresenterInterface;




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
        searchByCategoryPresenterInterface = new SearchByCategoryPresenter(this);
        searchByAreaPresenterInterface = new SearchByAreaPresenter(this);
        categoryRecyclerView = view.findViewById(R.id.category_recycler);
        areaRecycler = view.findViewById(R.id.area_recycler);
        randomImageView = view.findViewById(R.id.meal_image_view);
        randomTextView = view.findViewById(R.id.random_text_view);
        homePageFragmentPresenterInterface.onCreateViewRandomMeal();
        homePageFragmentPresenterInterface.onCreateViewCategories();
        homePageFragmentPresenterInterface.onCreateViewAreas();
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
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(), categoriesItemModel,this);
        categoryRecyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public void onFailureCategories(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessArea(List<AreaModel> areaModels) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        areaRecycler.setLayoutManager(linearLayoutManager);
        AreaAdapter areaAdapter = new AreaAdapter(getContext(), areaModels,this);
        areaRecycler.setAdapter(areaAdapter);
    }

    @Override
    public void onFailureArea(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClickListener(String categoryName) {
        HomePageFragmentDirections.ActionHomePageFragmentToSearchByCategoryView action =
                HomePageFragmentDirections.actionHomePageFragmentToSearchByCategoryView(categoryName);
        action.setCategoryName(categoryName);
        Navigation.findNavController(requireView()).navigate(action);
    }
    public  View onView(){
        View view;
        view = getView();
        return view;
    }

    @Override
    public void onAreaClickListener(String areaName) {
        HomePageFragmentDirections.ActionHomePageFragmentToSearchByAreaView action =
                HomePageFragmentDirections.actionHomePageFragmentToSearchByAreaView(areaName);
        action.setAreaName(areaName);
        Navigation.findNavController(requireView()).navigate(action);
    }
}