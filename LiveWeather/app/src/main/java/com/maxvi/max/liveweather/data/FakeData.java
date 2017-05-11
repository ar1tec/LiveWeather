package com.maxvi.max.liveweather.data;

import com.maxvi.max.liveweather.R;

public class FakeData {

    private static final String[] mData = new String[]{
            "Today - +16, Sunny",
            "Tomorrow - +10, Rain",
            "22.07 - +17, Hot",
            "23.07 - +18, Very hot",
            "Today - +16, Sunny",
            "Tomorrow - +10, Rain",
            "22.07 - +17, Hot",
            "23.07 - +18, Very hot",
            "Today - +16, Sunny",
            "Tomorrow - +10, Rain",
            "22.07 - +17, Hot",
            "23.07 - +18, Very hot",
            "Today - +16, Sunny",
            "Tomorrow - +10, Rain",
            "22.07 - +17, Hot",
            "23.07 - +18, Very hot"
    };

    public static String getTime() {
        return time;
    }

    public static int getImage() {
        return image;
    }

    public static String getTemp() {
        return temp;
    }

    private static final String time = "3PM";
    private static final int image = R.drawable.art_clear;
    private static final String temp = "22\u00b0";

    public static String[] getData() {
        return mData;
    }
}
