package com.example.goaltracker.util;

import android.util.Log;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHandler {

    public static final String TAG = "DateTimeHandler";

    private static final String datePattern = "dd/MM/uuuu";


    public static String getCalendarText(int year, int month, int dayOfMonth) {
        LocalDate date = LocalDate.of(year, month, dayOfMonth);
        String text = DateTimeFormatter
                .ofPattern(datePattern)
                .format(date);
        return text;
    }

    public static String getCalendarTextNow() {
        LocalDate date = LocalDate.now();
        String text = DateTimeFormatter
                .ofPattern(datePattern)
                .format(date);
        return text;
    }

    public static long stringToLongDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDate date = LocalDate.parse(dateString, formatter);

        ZonedDateTime zonedDateTime= date.atStartOfDay(ZoneId.systemDefault());

        return zonedDateTime.toInstant().toEpochMilli();
    }

    /**
     *
     * @param dateString the dateString
     * @return if the first dateString is before the current date
     */
    public static boolean afterNow(String dateString) {
        LocalDate dateNow = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDate date = LocalDate.parse(dateString, formatter);
        Log.d(TAG, "afterNow: " + dateNow + " " + date);
        return date.isAfter(dateNow);
    }


    /**
     *
     * @param dateString1 First Date
     * @param dateString2 Second Date
     * @return if dateString1 is after dateString2
     */
    public static boolean after(String dateString1, String dateString2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDate date1 = LocalDate.parse(dateString1, formatter);
        LocalDate date2 = LocalDate.parse(dateString2, formatter);
        return date1.isAfter(date2);
    }



}
