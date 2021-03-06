package com.maxvi.max.liveweather.utilities;

import java.util.Calendar;

public final class DateUtils {

    private static final long MULTIPLIER = 1000L;

    public static String getDay(final long timeStamp) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * MULTIPLIER);

        final String day;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                day = "Sunday";
                break;
            case Calendar.MONDAY:
                day = "Monday";
                break;
            case Calendar.TUESDAY:
                day = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                day = "Wednesday";
                break;
            case Calendar.THURSDAY:
                day = "Thursday";
                break;
            case Calendar.FRIDAY:
                day = "Friday";
                break;
            case Calendar.SATURDAY:
                day = "Saturday";
                break;
            default:
                day = "Unknown";
                break;
        }
        return day;
    }

    public static String getHour(final long timeStamp) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * MULTIPLIER);

        final int amPm = calendar.get(Calendar.AM_PM);
        final String noon;
        if (amPm == 1) {
            noon = "PM";
        } else {
            noon = "AM";
        }
        int hour = calendar.get(Calendar.HOUR);
        if (hour == 0) {
            hour = 12;
        }

        String time = hour + noon;
        return time;
    }

    public static boolean isThreePM(final long timeStamp) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * MULTIPLIER);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour == 15) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSixAM(final long timeStamp) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * MULTIPLIER);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour == 6) {
            return true;
        } else {
            return false;
        }
    }
}
