package com.example.android.popularmovies.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class DateUtility {

    /**
     * This method converts a date String from The MovieDb Api format to
     * the the user's preferred format.
     *
     * @param dateStr A String representation of the date.
     * @param dateFormatPref The date format that the user prefers.
     * @return
     */
    public static String formatDate(String dateStr, String dateFormatPref){

        // The Date Format OF THE Api.
        DateFormat fromFormat = new SimpleDateFormat("yyyy-mm-dd");

        DateFormat toFormat = new SimpleDateFormat(dateFormatPref);

        Date date = null;
        try {
            date = fromFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return toFormat.format(date);

    }


}
