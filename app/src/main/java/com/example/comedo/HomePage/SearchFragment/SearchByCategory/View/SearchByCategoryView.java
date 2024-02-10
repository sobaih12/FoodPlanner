package com.example.comedo.HomePage.SearchFragment.SearchByCategory.View;

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

import com.example.comedo.HomePage.SearchFragment.SearchByCategory.Presenter.SearchByCategoryPresenter;
import com.example.comedo.HomePage.SearchFragment.SearchByCategory.Presenter.SearchByCategoryPresenterInterface;
import com.example.comedo.Models.MealPreviewModel;
import com.example.comedo.R;

import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Observable;

public class SearchByCategoryView extends Fragment implements SearchByCategoryViewInterface, OnMealCategoryClickListener {

    SearchByCategoryPresenterInterface searchByCategoryPresenterInterface;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    EditText searchCategoryText;
    MealPreviewModel catItemListModel;
    TextView textView;
    ConstraintLayout noResult;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_search_by_category_view, container, false);
        searchByCategoryPresenterInterface = new SearchByCategoryPresenter(this);
        recyclerView = view.findViewById(R.id.search_category_recycler_view);
        searchCategoryText = view.findViewById(R.id.search_category_text);
        textView = view.findViewById(R.id.search_label);
        noResult = view.findViewById(R.id.no_result_search_category);
        String mealName = SearchByCategoryViewArgs.fromBundle(getArguments()).getCategoryName();
        textView.setText("Filtered by "+mealName+" category");
        searchByCategoryPresenterInterface.onViewCreatedSearchOnCategory(mealName,searchCategoryText.getText().toString());
        searchCategoryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onSuccessSearchByCategoryFilter(searchCategoryText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    @Override
    public void onSuccessSearchByCategory(MealPreviewModel categoriesItemListModel) {
        this.catItemListModel = categoriesItemListModel;
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        SearchByCategoryAdapter searchAdapter = new SearchByCategoryAdapter(categoriesItemListModel,getContext(),this);
        recyclerView.setAdapter(searchAdapter);

    }

    @Override
    public void onSuccessSearchByCategoryFilter(String filter) {
        Observable.fromIterable(catItemListModel.getMeals())
                .filter(meal -> meal.getStrMeal().toLowerCase().contains(filter))
                .collect(Collectors.toList())
                .subscribe(filteredMeals -> {
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    SearchByCategoryAdapter searchAdapter = new SearchByCategoryAdapter(new MealPreviewModel(filteredMeals),getContext(),this);
                    recyclerView.setAdapter(searchAdapter);
                    if (filteredMeals.size() == 0   || searchCategoryText.getText().toString() == null) {
                        recyclerView.setVisibility(View.INVISIBLE);
                        noResult.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        noResult.setVisibility(View.GONE);
                    }
                });
    }


    @Override
    public View getViewFromFragment() {
        View view;
        view =getView();
        return view;
    }

    @Override
    public void onMealCategoryClickListener(String mealName) {
        searchByCategoryPresenterInterface.onViewCreatedSearchOnMeal(mealName);
    }
}