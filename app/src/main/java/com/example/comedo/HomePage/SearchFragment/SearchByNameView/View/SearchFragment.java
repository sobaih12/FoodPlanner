package com.example.comedo.HomePage.SearchFragment.SearchByNameView.View;

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
import android.widget.ImageView;



import com.example.comedo.HomePage.SearchFragment.SearchByNameView.Presenter.SearchPresenter;
import com.example.comedo.HomePage.SearchFragment.SearchByNameView.Presenter.SearchPresenterInterface;

import com.example.comedo.Models.MealModel;

import com.example.comedo.R;

import java.util.List;

public class SearchFragment extends Fragment implements SearchViewInterface,OnMealClickListener {
    SearchPresenterInterface searchPresenterInterface;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ImageView randomImageView;
    EditText searchTextView;
    ConstraintLayout noResultImage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_search, container, false);
        searchPresenterInterface = new SearchPresenter(this);

        recyclerView = view.findViewById(R.id.search_recycler_view);
        randomImageView = view.findViewById(R.id.meal_image_view);
        searchTextView = view.findViewById(R.id.search_text);
        noResultImage = view.findViewById(R.id.no_result_search_name);
        noResultImage.setVisibility(View.VISIBLE);

        searchTextView.setText("");
        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noResultImage.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchPresenterInterface.onViewCreatedSearch(s.toString());
            }
        });
        return view;
    }
    @Override
    public void onSuccessSearchByMeal(List<MealModel> mealListModel) {
            linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            SearchAdapter searchAdapter = new SearchAdapter(mealListModel,getContext(),this);
            recyclerView.setAdapter(searchAdapter);
        if (mealListModel == null || searchTextView.getText().toString().isEmpty()|| searchTextView.getText().toString() == null) {
            recyclerView.setVisibility(View.INVISIBLE);
            noResultImage.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noResultImage.setVisibility(View.GONE);
        }
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