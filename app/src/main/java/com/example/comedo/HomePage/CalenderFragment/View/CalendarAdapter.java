package com.example.comedo.HomePage.CalenderFragment.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.comedo.Models.PlanDetailsModel;
import com.example.comedo.R;
import com.example.comedo.RoomDB.MealDataBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.SearchViewHolder> {

    private List<PlanDetailsModel> items;
    private Context context;
    OnCalendarClickListener listener;


    public CalendarAdapter(List<PlanDetailsModel> dataSet, Context context, OnCalendarClickListener listener) {
        this.items = dataSet;
        this.context = context;
        this.listener = listener;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mealImageView;
        TextView mealTextView;
        ConstraintLayout constraintLayout;
        ImageView removeCalendar;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImageView = itemView.findViewById(R.id.meal_search_image_view);
            mealTextView = itemView.findViewById(R.id.meal_search_text_view);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            removeCalendar = itemView.findViewById(R.id.remove_calendar);
        }
    }

    @NonNull
    @Override
    public CalendarAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_cell, parent, false);
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
        holder.removeCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReferenceCalendar = firebaseDatabase.getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("CalendarMeals");
                databaseReferenceCalendar.child(item.idMeal).removeValue();
                new Thread(()->{
                    MealDataBase.getInstance(v.getContext()).getMealDao().deleteMealPlan(item);
                    Log.i("TAG", "onClick: Data Successfully Deleted To Room");
                }).start();
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


