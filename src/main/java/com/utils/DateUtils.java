package com.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for all date utilities
 * @author Ashish Patel
 */
public class DateUtils {
    public static String getCurrentDateFormat(String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        String dateString = dateFormat.format(date);
        return dateString;
    }

    public static String getCurrentDate(){
        String dateString = getCurrentDateFormat("MMMM/dd/yyyy");
        String month = dateString.substring(0, dateString.indexOf("/"));
        String day = dateString.substring(dateString.indexOf("/")+1);
        day = day.substring(0, day.indexOf("/"));
        day = day + getDayOfMonthSuffix(Integer.parseInt(day));
        String year = dateString.substring(dateString.lastIndexOf("/")+1);
        day = day.replaceFirst("^0+(?!$)", "");
        return day + " " + month + " " + year;
    }

    public static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }
}
