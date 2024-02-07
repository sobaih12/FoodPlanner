package com.example.comedo.Models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
//    public static final String DATE_FORMAT = "dd-mm-yyyy";
    public static String getString(Date date){
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return  format.format(date);
    }

    public static String getString(int year, int month, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return  format.format(calendar.getTime());
    }
}
