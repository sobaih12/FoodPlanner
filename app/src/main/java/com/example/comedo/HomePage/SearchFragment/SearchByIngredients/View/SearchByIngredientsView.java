package com.example.comedo.HomePage.SearchFragment.SearchByIngredients.View;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.comedo.HomePage.SearchFragment.SearchByIngredients.Presenter.SearchByIngredientsPresenter;
import com.example.comedo.HomePage.SearchFragment.SearchByIngredients.Presenter.SearchByIngredientsPresenterInterface;
import com.example.comedo.Models.MealPreviewModel;
import com.example.comedo.R;


public class SearchByIngredientsView extends Fragment implements SearchByIngredientsViewInterface, OnMealIngredientClickListener {

    SearchByIngredientsPresenterInterface searchByIngredientsPresenterInterface;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    EditText searchCategoryText;
    ConstraintLayout noResult;
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_search_by_ingredients_view, container, false);
        searchByIngredientsPresenterInterface = new SearchByIngredientsPresenter(this);
        recyclerView = view.findViewById(R.id.search_ingredients_recycler_view);
        searchCategoryText = view.findViewById(R.id.search_ingredients_text);
        noResult = view.findViewById(R.id.no_result_search_ingredients);
        textView = view.findViewById(R.id.search_label);
        String mealName = SearchByIngredientsViewArgs.fromBundle(getArguments()).getIngredientName();
        textView.setText("Filtered by "+mealName+" meals");
        searchByIngredientsPresenterInterface.onViewCreatedSearchOnIngredients(mealName,searchCategoryText.getText().toString());
        searchCategoryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchByIngredientsPresenterInterface.onViewCreatedSearchOnIngredients(mealName,searchCategoryText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onMealIngredientsClickListener(String mealName) {
        searchByIngredientsPresenterInterface.onViewCreatedSearchOnMeal(mealName);
    }

    @Override
    public void onSuccessSearchByIngredient(MealPreviewModel categoriesItemListModel) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        SearchByIngredientsAdapter searchAdapter = new SearchByIngredientsAdapter(categoriesItemListModel,getContext(),this);
        recyclerView.setAdapter(searchAdapter);
        if (categoriesItemListModel.getMeals().size() == 0   || searchCategoryText.getText().toString() == null) {
            recyclerView.setVisibility(View.INVISIBLE);
            noResult.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noResult.setVisibility(View.GONE);
        }
    }

    @Override
    public View getViewFromFragment() {
        View view;
        view =getView();
        return view;
    }
}