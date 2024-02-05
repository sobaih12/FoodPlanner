package com.example.comedo.HomePage.RandomMealFragment.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.comedo.Models.IngredientWithMeasuresModel;
import com.example.comedo.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<IngredientWithMeasuresModel> dataList;
    private Context context;
    private static final String url = "https://www.themealdb.com/images/ingredients/";


    public IngredientsAdapter(List<IngredientWithMeasuresModel> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientWithMeasuresModel item = dataList.get(position);

        holder.ingredientsTextView.setText(item.getIngredient());
        holder.measuresTextView.setText(item.getMeasure());
        Glide.with(holder.itemView.getContext())
                .load(url+item.getIngredient()+".png")
                .apply(new RequestOptions().override(90, 90))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientsTextView, measuresTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientsTextView = itemView.findViewById(R.id.ingredient_text_view);
            measuresTextView = itemView.findViewById(R.id.measure_text_view);
            imageView = itemView.findViewById(R.id.ingredients_image_view);
        }
    }
}
