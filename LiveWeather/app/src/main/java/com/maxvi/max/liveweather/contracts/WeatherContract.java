package com.maxvi.max.liveweather.contracts;

import android.net.Uri;
import android.provider.BaseColumns;

public final class WeatherContract {

    public static final String AUTHORITY = "com.maxvi.max.liveweather";
    private static final String PRE_URI = "content://";
    private static final Uri BASE_CONTENT_URI = Uri.parse(PRE_URI + AUTHORITY);
    public static final String PATH_WEATHER = "weather";

    public static final class WeatherEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER)
                .build();

        public static final String TABLE_NAME = "weather_table";

        public static final String TEMP_MIN = "temp_min";
        public static final String TEMP_MAX = "temp_max";
        public static final String PRESSURE = "pressure";
        public static final String HUMIDITY = "humidity";
        public static final String WEATHER_ID = "weather_id";
        public static final String WIND_SPEED = "wind_speed";
        public static final String WIND_DEGREES = "wind_degrees";
        public static final String DATE = "date";
    }
}
