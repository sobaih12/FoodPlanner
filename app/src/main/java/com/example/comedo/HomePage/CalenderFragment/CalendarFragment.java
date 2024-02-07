package com.example.comedo.HomePage.CalenderFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.comedo.R;

public class CalendarFragment extends Fragment {
    CalendarView calendarView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_calender, container, false);
        calendarView = view.findViewById(R.id.calendarView);

        // Set OnDateChangeListener
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Handle the selected date
                String selectedDate = year + "-" + month + "-" + dayOfMonth;
                // You can now use 'selectedDate' for further processing
                Log.i("TAG", "onSelectedDayChange: "+selectedDate);
//                Log.d("SelectedDate", selectedDate);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}