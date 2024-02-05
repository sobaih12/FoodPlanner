package com.example.comedo.HomePage.HomeFragment.View;

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
import com.example.comedo.Models.AreaModel;
import com.example.comedo.Models.IngredientModel;
import com.example.comedo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class HomeIngredientsAdapter extends RecyclerView.Adapter<HomeIngredientsAdapter.ViewHolder> {

    private List<IngredientModel> items;
    private Context context;
    OnIngredientsClickListener onIngredientsClickListener;
    private static final String url = "https://www.themealdb.com/images/ingredients/";


    public HomeIngredientsAdapter( Context context , List<IngredientModel> items,OnIngredientsClickListener listener) {
        this.items = items;
        this.context = context;
        this.onIngredientsClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        RoundedImageView roundedImageView;
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.category_text_view);
            roundedImageView = itemView.findViewById(R.id.category_image_view);
            constraintLayout = itemView.findViewById(R.id.category_cell_constraint);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientModel item = items.get(position);
        holder.nameTextView.setText(item.getStrIngredient());
        Glide.with(holder.itemView.getContext())
                .load(url+item.getStrIngredient()+".png")
                .apply(new RequestOptions().override(90, 90))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.roundedImageView);
        Log.i("TAG", "onBindViewHolder: ");
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIngredientsClickListener.onIngredientsClickListener(item.getStrIngredient().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

