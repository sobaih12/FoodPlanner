package com.example.comedo.HomePage.SearchFragment.SearchByNameView.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.comedo.HomePage.SearchFragment.SearchByNameView.Presenter.SearchPresenter;
import com.example.comedo.HomePage.SearchFragment.SearchByNameView.Presenter.SearchPresenterInterface;
import com.example.comedo.Models.MealListModel;
import com.example.comedo.Models.MealPreviewModel;
import com.example.comedo.R;

public class SearchFragment extends Fragment implements SearchViewInterface,OnMealClickListener {
    SearchPresenterInterface searchPresenterInterface;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ImageView randomImageView;
    EditText searchTextView;
    private RadioButton chipCategory;
    private RadioButton chipArea;
    private RadioButton chipIngredient;
    private RadioButton chipMeal;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_search, container, false);
        searchPresenterInterface = new SearchPresenter(this);

        recyclerView = view.findViewById(R.id.search_recycler_view);
        randomImageView = view.findViewById(R.id.meal_image_view);
        searchTextView = view.findViewById(R.id.search_text);
        chipCategory = view.findViewById(R.id.chip_category);
        chipArea = view.findViewById(R.id.chip_area);
        chipIngredient = view.findViewById(R.id.chip_ingredient);
        chipMeal = view.findViewById(R.id.chip_meal);

        chipMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTextView.setText("");
                searchTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        searchPresenterInterface.onViewCreatedSearch(searchTextView.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        chipCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPresenterInterface.onViewCreatedSearchOnCategory("Seafood");

//                searchTextView.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        searchPresenterInterface.onViewCreatedSearchOnCategory("Seafood");
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });
            }
        });

        return view;
    }

    @Override
    public void onSuccessSearchByMeal(MealListModel mealListModel) {
            linearLayoutManager = new LinearLayoutManager(requireContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            SearchAdapter searchAdapter = new SearchAdapter(mealListModel,requireContext(),this);
            recyclerView.setAdapter(searchAdapter);


    }

    @Override
    public void onSuccessSearchByCategory(MealPreviewModel categoriesItemListModel) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        SearchByCategoriesAdapter searchAdapter = new SearchByCategoriesAdapter(categoriesItemListModel,getContext(),this);
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public View getViewFromFragment() {
        View view;
        view =getView();
        return view;
    }


    @Override
    public void onMealClickListener(String mealName) {
        searchPresenterInterface.onViewCreatedSearchOnMeal(mealName);
    }
}