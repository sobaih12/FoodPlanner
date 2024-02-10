package com.example.comedo.HomePage.SearchFragment.SearchByArea.View;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.comedo.Models.MealPreviewModel;

import com.example.comedo.Models.MealsItem;
import com.example.comedo.R;
import com.makeramen.roundedimageview.RoundedImageView;



public class SearchByAreaAdapter extends RecyclerView.Adapter<SearchByAreaAdapter.SearchViewHolder> {
    private MealPreviewModel items;
    private Context context;
    OnMealAreaClickListener listener;

    public SearchByAreaAdapter(MealPreviewModel dataSet, Context context, OnMealAreaClickListener listener) {
        this.items = dataSet;
        this.context = context;
        this.listener = listener;
    }
    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mealImageView;
        TextView mealTextView;
        ConstraintLayout constraintLayout;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImageView = itemView.findViewById(R.id.meal_search_image_view);
            mealTextView = itemView.findViewById(R.id.meal_search_text_view);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }

    @NonNull
    @Override
    public SearchByAreaAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_cell, parent, false);
        return new SearchByAreaAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchByAreaAdapter.SearchViewHolder holder, int position) {
        MealsItem item = items.getMeals().get(position);
        holder.mealTextView.setText(items.getMeals().get(position).getStrMeal());
        Glide.with(context)
                .load(items.getMeals().get(position).getStrMealThumb())
                .apply(new RequestOptions().override(379, 235).centerCrop())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.mealImageView);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMealAreaClickListener(item.getStrMeal());
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.getMeals().size();
    }
}
