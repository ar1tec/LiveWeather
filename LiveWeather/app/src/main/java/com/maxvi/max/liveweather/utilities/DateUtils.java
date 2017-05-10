package com.maxvi.max.liveweather.utilities;

import java.util.Calendar;

public final class DateUtils {

    private static final long MULTIPLIER = 1000L;

    public static String getDate(final long timeStamp) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * MULTIPLIER);

        String day;
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
        }

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

        day += " " + hour + " " + noon;

        return day;
    }
}