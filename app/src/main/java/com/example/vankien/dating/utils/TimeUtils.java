package com.example.vankien.dating.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vanki on 3/19/2018.
 */

public class TimeUtils {
    private static TimeUtils shareInstance = new TimeUtils();

    public static TimeUtils getShareInstance(){
        return shareInstance;
    }

    public String getDateString(Date date){
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("MM:dd:yyyy:HH:mm:ss");

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);

        return reportDate;
    }
}
