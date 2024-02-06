package com.example.comedo.HomePage.HomeFragment.View;

import android.content.Context;
import android.os.Bundle;

import com.example.comedo.HomePage.HomeFragment.Presenter.HomePageFragmentPresenter;
import com.example.comedo.HomePage.HomeFragment.Presenter.HomePageFragmentPresenterInterface;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.comedo.HomePage.SearchFragment.SearchByArea.Presenter.SearchByAreaPresenter;
import com.example.comedo.HomePage.SearchFragment.SearchByArea.Presenter.SearchByAreaPresenterInterface;
import com.example.comedo.HomePage.SearchFragment.SearchByCategory.Presenter.SearchByCategoryPresenter;
import com.example.comedo.HomePage.SearchFragment.SearchByCategory.Presenter.SearchByCategoryPresenterInterface;
import com.example.comedo.HomePage.SearchFragment.SearchByIngredients.Presenter.SearchByIngredientsPresenter;
import com.example.comedo.HomePage.SearchFragment.SearchByIngredients.Presenter.SearchByIngredientsPresenterInterface;
import com.example.comedo.HomePage.SearchFragment.SearchByNameView.Presenter.SearchPresenterInterface;
import com.example.comedo.Models.AreaModel;
import com.example.comedo.Models.CategoriesItemModel;
import com.example.comedo.Models.IngredientModel;
import com.example.comedo.Models.MealModel;
import com.example.comedo.R;

import java.util.List;


public class HomePageFragment extends Fragment implements HomePageFragmentInterface,OnCategoryClickListener ,OnAreaClickListener,OnIngredientsClickListener{


    Context context ;

    CategoriesApiService categoriesApiService;

    RecyclerView categoryRecyclerView,areaRecycler,ingredientsRecycler;
    LinearLayoutManager linearLayoutManager;
    ImageView randomImageView,noResult;
    TextView randomTextView;
    EditText categorySearch,areaSearch,ingredientsSearch;



    HomePageFragmentPresenterInterface homePageFragmentPresenterInterface;
    SearchByCategoryPresenterInterface searchByCategoryPresenterInterface;
    SearchByAreaPresenterInterface searchByAreaPresenterInterface;
    SearchByIngredientsPresenterInterface searchByIngredientsPresenterInterface;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        categorySearch.getText().clear();
        areaSearch.getText().clear();
        ingredientsSearch.getText().clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view =  inflater.inflate(R.layout.fragment_home_page, container, false);
        homePageFragmentPresenterInterface =  new HomePageFragmentPresenter(this);
        searchByCategoryPresenterInterface = new SearchByCategoryPresenter(this);
        searchByAreaPresenterInterface = new SearchByAreaPresenter(this);
        searchByIngredientsPresenterInterface = new SearchByIngredientsPresenter(this);
        categoryRecyclerView = view.findViewById(R.id.category_recycler);
        areaRecycler = view.findViewById(R.id.area_recycler);
        ingredientsRecycler = view.findViewById(R.id.ingredients_recycler);
        noResult = view.findViewById(R.id.free_palestine_image_view);
        randomImageView = view.findViewById(R.id.meal_image_view);
        randomTextView = view.findViewById(R.id.random_text_view);
        categorySearch = view.findViewById(R.id.category_search_edit_text);
        areaSearch = view.findViewById(R.id.country_search_edit_text);
        ingredientsSearch = view.findViewById(R.id.search_ingredients_edit_text);
        homePageFragmentPresenterInterface.onCreateViewRandomMeal();
        homePageFragmentPresenterInterface.onCreateViewCategories(categorySearch.getText().toString());
        categorySearch.setText("");
        homePageFragmentPresenterInterface.onCreateViewAreas(areaSearch.getText().toString());
        areaSearch.setText("");
        homePageFragmentPresenterInterface.onCreateViewIngredients(ingredientsSearch.getText().toString());
        ingredientsSearch.setText("");
        categorySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                homePageFragmentPresenterInterface.onCreateViewCategories(categorySearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        areaSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                homePageFragmentPresenterInterface.onCreateViewAreas(areaSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ingredientsSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                homePageFragmentPresenterInterface.onCreateViewIngredients(ingredientsSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



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
        if (categoriesItemModel.isEmpty()) {
            categoryRecyclerView.setVisibility(View.INVISIBLE);
            noResult.setVisibility(View.VISIBLE);
        } else {
            categoryRecyclerView.setVisibility(View.VISIBLE);
            noResult.setVisibility(View.GONE);
        }
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
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessIngredients(List<IngredientModel> ingredientModels) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ingredientsRecycler.setLayoutManager(linearLayoutManager);
        HomeIngredientsAdapter areaAdapter = new HomeIngredientsAdapter(getContext(), ingredientModels,this);
        ingredientsRecycler.setAdapter(areaAdapter);
    }

    @Override
    public void onFailureIngredients(String message) {
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

    @Override
    public void onIngredientsClickListener(String ingredientsName) {
//        Toast.makeText(getContext(),ingredientsName, Toast.LENGTH_SHORT).show();
        HomePageFragmentDirections.ActionHomePageFragmentToSearchByIngredientsView action =
                HomePageFragmentDirections.actionHomePageFragmentToSearchByIngredientsView(ingredientsName);
        action.setIngredientName(ingredientsName);
        Navigation.findNavController(requireView()).navigate(action);
    }
}