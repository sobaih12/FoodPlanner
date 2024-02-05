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
import com.example.comedo.Models.CategoriesItemModel;
import com.example.comedo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<CategoriesItemModel> items;
    private Context context;
    OnCategoryClickListener onCategoryClickListener;

    public CategoriesAdapter( Context context , List<CategoriesItemModel> items, OnCategoryClickListener listener) {
        this.items = items;
        this.context = context;
        this.onCategoryClickListener = listener;
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
        CategoriesItemModel item = items.get(position);
        holder.nameTextView.setText(item.getStrCategory());
        Glide.with(context)
                .load(items.get(position).getStrCategoryThumb())
                .apply(new RequestOptions().override(150, 150).centerCrop())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.roundedImageView);
        Log.i("TAG", "onBindViewHolder: ");
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoryClickListener.onCategoryClickListener(item.getStrCategory().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}