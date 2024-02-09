package com.example.comedo.HomePage.CalenderFragment.View;

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
import com.example.comedo.Models.PlanDetailsModel;
import com.example.comedo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.SearchViewHolder> {

    private List<PlanDetailsModel> items;
    private Context context;
    OnCalendarClickListener listener;

    // Constructor to initialize the adapter with data and context
    public CalendarAdapter(List<PlanDetailsModel> dataSet, Context context, OnCalendarClickListener listener) {
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
    public CalendarAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your search_cell layout here
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_cell, parent, false);
        return new CalendarAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.SearchViewHolder holder, int position) {
        PlanDetailsModel item = items.get(position);
        holder.mealTextView.setText(items.get(position).strMeal);
        Glide.with(context)
                .load(items.get(position).strMealThumb)
                .apply(new RequestOptions().override(379, 235).centerCrop())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.mealImageView);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCalendarClickListener(item);
            }
        });



    }

    public int getItemCount() {
        if(items==null) return 0;
        return items.size();
    }
    public void setList(List<PlanDetailsModel> mealModels){
        this.items = mealModels;
        notifyDataSetChanged();
    }


}

