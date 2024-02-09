package com.example.comedo.HomePage.CalenderFragment.View;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.comedo.HomePage.FavoriteFragment.View.FavoriteFragmentDirections;
import com.example.comedo.Models.DateFormatter;
import com.example.comedo.Models.MealModel;
import com.example.comedo.Models.PlanDetailsConverter;
import com.example.comedo.Models.PlanDetailsModel;
import com.example.comedo.R;
import com.example.comedo.RoomDB.MealDataBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment implements OnCalendarClickListener {
    TextView selectDate;
    MealModel mealModel;
    CalendarAdapter calendarAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_calender, container, false);
        selectDate = view.findViewById(R.id.select_date_text_view);
        recyclerView = view.findViewById(R.id.calendar_recycler);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        calendarAdapter = new CalendarAdapter(new ArrayList<>(),getActivity(),this);
        recyclerView.setAdapter(calendarAdapter);
        selectDate.setText(DateFormatter.getString(new Date()));
        setViews(DateFormatter.getString(new Date()));
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new CalendarFragment.DatePickerFragment(CalendarFragment.this);
                newFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");
            }
        });
        return view;
    }

    void setViews(String date)
    {
        MealDataBase.getInstance(getContext()).getMealDao().getPlanFromDate(date)
                .observe(getViewLifecycleOwner(), new Observer<List<PlanDetailsModel>>() {
                    @Override
                    public void onChanged(List<PlanDetailsModel> planDetailsModels) {
                        calendarAdapter.setList(planDetailsModels);
                    }
                });
    }

    @Override
    public void onCalendarClickListener(PlanDetailsModel mealModel) {
        CalendarFragmentDirections.ActionCalenderFragmentToRandomMealFragment action =
                CalendarFragmentDirections.actionCalenderFragmentToRandomMealFragment(PlanDetailsConverter.getMealFromMealPlanner(mealModel));
        Navigation.findNavController(getView()).navigate(action);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {
        CalendarFragment mealFragmentView;

        public DatePickerFragment(CalendarFragment planFragment)
        {
            this.mealFragmentView =planFragment;

        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            c.add(Calendar.DAY_OF_MONTH, 7);
            long maxDate = c.getTimeInMillis();
            datePickerDialog.getDatePicker().setMaxDate(maxDate);
            return datePickerDialog;
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {

            String date = DateFormatter.getString(year,month,day);
            mealFragmentView.setViews(date);
            mealFragmentView.selectDate.setText(date);
            //real time database

        }
    }
}