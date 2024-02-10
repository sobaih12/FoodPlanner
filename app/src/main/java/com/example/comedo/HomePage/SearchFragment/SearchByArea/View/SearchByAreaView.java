package com.example.comedo.HomePage.SearchFragment.SearchByArea.View;

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

import com.example.comedo.HomePage.SearchFragment.SearchByArea.Presenter.SearchByAreaPresenter;
import com.example.comedo.HomePage.SearchFragment.SearchByArea.Presenter.SearchByAreaPresenterInterface;
import com.example.comedo.Models.MealPreviewModel;
import com.example.comedo.R;

import org.w3c.dom.Text;

public class SearchByAreaView extends Fragment implements SearchByAreaViewInterface,OnMealAreaClickListener{
    SearchByAreaPresenterInterface searchByAreaPresenterInterface;
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
        view = inflater.inflate(R.layout.fragment_search_by_area_view, container, false);
        searchByAreaPresenterInterface = new SearchByAreaPresenter(this);
        recyclerView = view.findViewById(R.id.search_area_recycler_view);
        searchCategoryText = view.findViewById(R.id.search_area_text);
        noResult = view.findViewById(R.id.no_result_search_category);
        textView = view.findViewById(R.id.search_label);
        String mealName = SearchByAreaViewArgs.fromBundle(getArguments()).getAreaName();
        textView.setText("Filtered by "+mealName+" meals");
        searchByAreaPresenterInterface.onViewCreatedSearchOnArea(mealName,searchCategoryText.getText().toString());
        searchCategoryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchByAreaPresenterInterface.onViewCreatedSearchOnArea(mealName,searchCategoryText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onMealAreaClickListener(String mealName) {
        searchByAreaPresenterInterface.onViewCreatedSearchOnMeal(mealName);
    }

    @Override
    public void onSuccessSearchByArea(MealPreviewModel categoriesItemListModel) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        SearchByAreaAdapter searchAdapter = new SearchByAreaAdapter(categoriesItemListModel, getContext(), this);
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