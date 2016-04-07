package com.ovulationcalendar.herdays;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NextPeriod {

    static String lastPeriod = "02-16-2016";
    static int cycleLength = 28;
    static int periodLength = 4;

    public static void main(String[] args) {
        computeLastPeriod();
    }

    public static void computeLastPeriod() {

        // Calendar
        Calendar calNextPeriod = stringToCalendar(lastPeriod);
        calNextPeriod.add(Calendar.DAY_OF_MONTH, cycleLength);

        println("Next Period: " + calNextPeriod.getTime());

        Calendar calCurrentDate = Calendar.getInstance();
        calCurrentDate.set(Calendar.HOUR_OF_DAY, 0);
        calCurrentDate.set(Calendar.MINUTE, 0);
        calCurrentDate.set(Calendar.SECOND, 0);
        calCurrentDate.set(Calendar.MILLISECOND, 0);

        long lNextPeriod = calNextPeriod.getTimeInMillis();
        long lCurrentDate = calCurrentDate.getTimeInMillis();

        long diff = lNextPeriod - lCurrentDate;
        int days = differenceInDays(diff);

        println("Days difference: " + days);

        if (days < cycleLength) {
            if (days >= cycleLength - periodLength) {
                System.out.println("Meron ka ngayon");
                int mensDay = cycleLength - days;
                println("Day: " + mensDay);
            }
        }

    }

    public static void stringToDate(String dateString) {

    }

    public static Calendar stringToCalendar(String date) {
        Calendar c = Calendar.getInstance();

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return c;
    }

    public static int differenceInDays(long diff) {
        int HOURS_IN_DAY = 24;
        int MINUTES_IN_HOUR = 60;
        int SECONDS_IN_MINUTE = 60;
        int MS_IN_SECONDS = 1000;
        println("" + diff);
        return (int) (diff / (HOURS_IN_DAY * MINUTES_IN_HOUR
                * SECONDS_IN_MINUTE * MS_IN_SECONDS));
    }

    public static void println(String message) {
        System.out.println(message);
    }
}
