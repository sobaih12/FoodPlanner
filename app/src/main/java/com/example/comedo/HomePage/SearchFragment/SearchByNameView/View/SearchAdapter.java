package com.example.comedo.HomePage.SearchFragment.SearchByNameView.View;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.comedo.Models.MealListModel;
import com.example.comedo.Models.MealModel;
import com.example.comedo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<MealModel> items;
    private Context context;
    OnMealClickListener listener;

    // Constructor to initialize the adapter with data and context
    public SearchAdapter(List<MealModel> dataSet, Context context,OnMealClickListener listener) {
        this.items = dataSet;
        this.context = context;
        this.listener = listener;
    }

    // ViewHolder class to hold the views for each item
    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mealImageView;
        TextView mealTextView;
        CircleImageView favoriteImageView;
        ConstraintLayout constraintLayout;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your views from search_cell.xml here
            mealImageView = itemView.findViewById(R.id.meal_search_image_view);
            mealTextView = itemView.findViewById(R.id.meal_search_text_view);
            favoriteImageView = itemView.findViewById(R.id.favorite_image_view);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your search_cell layout here
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_cell, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        // Bind data to your views here using dataSet.get(position)
        // For example:
        // holder.mealTextView.setText(dataSet.get(position));
        // You can also load an image into the mealImageView using a library like Glide or Picasso.

        // Set click listeners or any other view-related operations here.
        MealModel item = items.get(position);
        holder.mealTextView.setText(items.get(position).getStrMeal());
        Glide.with(context)
                .load(items.get(position).getStrMealThumb())
                .apply(new RequestOptions().override(379, 235).centerCrop())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.mealImageView);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMealClickListener(item.getStrMeal());
            }
        });



    }

    public int getItemCount() {
        if(items==null) return 0;
        return items.size();
    }


}

