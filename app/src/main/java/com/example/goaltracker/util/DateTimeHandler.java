package com.example.goaltracker.util;

import android.util.Log;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class DateTimeHandler {

    public static final String TAG = "DateTimeHandler";
    public static final String NOW = "nownownow";
    public static final String LAST_WEEK = "lastweeeek";
    public static final String LAST_MONTH = "lastmonthhh";

    private static final String datePattern = "dd/MM/uuuu";
    private static final String datePatternFriendly = "dd-MMM";

    //Get local date Now, Last Week or Last Month


    /**
     *
     * @param param Select between NOW, LAST_WEEK, and LAST_MONTH
     * @return date in form of text
     */
    public static String getCalendarText(String param) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDate date = getLocalDate(param);
        return dateTimeFormatter.format(date);
    }

    /**
     *
     * @param param Select between NOW, LAST_WEEK, and LAST_MONTH
     * @return date in form of Long
     */
    public static long getCalendarLong(String param) {
        LocalDate date = getLocalDate(param);
        ZonedDateTime zonedDateTime= date.atStartOfDay(ZoneId.systemDefault());
        return zonedDateTime.toInstant().toEpochMilli();
    }



    //Interconversion between string and long
    public static long stringToLongDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDate date = LocalDate.parse(dateString, formatter);

        ZonedDateTime zonedDateTime= date.atStartOfDay(ZoneId.systemDefault());

        return zonedDateTime.toInstant().toEpochMilli();
    }

    public static String longToStringDate(long dateLong) {
        LocalDate date = longToLocalDate(dateLong);

        return DateTimeFormatter
                .ofPattern(datePattern)
                .format(date);
    }



    public static String getCalendarText(int year, int month, int dayOfMonth) {
        LocalDate date = LocalDate.of(year, month, dayOfMonth);
        String text = DateTimeFormatter
                .ofPattern(datePattern)
                .format(date);
        return text;
    }

    //TODO: Remove hard code
    public static List<String> generateDaysFrom(long dateStartLong, long dateEndLong) {
        LocalDate dateStart = longToLocalDate(dateStartLong);
        LocalDate dateEnd = longToLocalDate(dateEndLong);
        LocalDate iterativeDate = dateStart;
        ArrayList<String> result = new ArrayList<>();
        while (!iterativeDate.isAfter(dateEnd)) {
            result.add(localDateToFriendlyString(iterativeDate));
            iterativeDate = iterativeDate.plusDays(1);
        }

        return result;
    }

    public static String[] getCalendarTextPastWeek() {
        LocalDate dateNow = LocalDate.now();
        LocalDate date;
        String[] result = new String[7];
        for (Integer i : Arrays.asList(0, 1, 2, 3, 4, 5, 6)) {
            date = dateNow.minusDays(6 - i);
            result[i] = DateTimeHandler.getCalendarText(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        }

        return result;
    }

    public static int countDaysBetween(long dateLong1, long dateLong2) {
        LocalDate date1 = longToLocalDate(dateLong1);
        LocalDate date2 = longToLocalDate(dateLong2);

        return (int) DAYS.between(date1, date2);
    }

    //Private methods involving conversion to LocalDate
    private static LocalDate longToLocalDate(long dateLong) {
        return Instant.ofEpochMilli(dateLong)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private static String localDateToFriendlyString(LocalDate date) {
        return DateTimeFormatter
                .ofPattern(datePatternFriendly)
                .format(date);
    }

    private static LocalDate getLocalDate(String param) {
        LocalDate date = LocalDate.now();
        switch (param) {
            case NOW:
                break;
            case LAST_WEEK:
                date = date.minusWeeks(1);
                break;
            case LAST_MONTH:
                date = date.minusMonths(1);
                break;
        }
        return date;
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
