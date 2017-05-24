package com.maxvi.max.liveweather.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxvi.max.liveweather.R;
import com.maxvi.max.liveweather.contracts.WeatherContract;
import com.maxvi.max.liveweather.models.DayForecast;
import com.maxvi.max.liveweather.utilities.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class DataBinding {

    public static void bindNowWeather(final Context pContext, final View pView) {
        final TextView location = (TextView) pView.findViewById(R.id.now_location);
        final TextView description = (TextView) pView.findViewById(R.id.now_description);
        final TextView temp = (TextView) pView.findViewById(R.id.now_temp);
        final ImageView weatherImage = (ImageView) pView.findViewById(R.id.now_image_weather);

    }

    public static List<DayForecast> makeDailyWeatherList(final Cursor cursor) {
        final List<DayForecast> dayForecast = new ArrayList<>();
        DayForecast forecast = new DayForecast();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            final int time = cursor.getInt(cursor.getColumnIndex(WeatherContract.WeatherEntry.DATE));
            if (DateUtils.isSixAM(time)) {
                forecast.setMinTemp(cursor.getDouble(cursor.getColumnIndex(WeatherContract.WeatherEntry.TEMP_MIN)));

            }
            if (DateUtils.isThreePM(time)) {
                forecast.setMaxTemp(cursor.getDouble(cursor.getColumnIndex(WeatherContract.WeatherEntry.TEMP_MAX)));
                forecast.setDescription(cursor.getInt(cursor.getColumnIndex(WeatherContract.WeatherEntry.WEATHER_ID)));
                forecast.setPressure(cursor.getDouble(cursor.getColumnIndex(WeatherContract.WeatherEntry.PRESSURE)));
                forecast.setHumidity(cursor.getInt(cursor.getColumnIndex(WeatherContract.WeatherEntry.HUMIDITY)));
                forecast.setTimestamp(time);
                forecast.setWindDegrees(cursor.getDouble(cursor.getColumnIndex(WeatherContract.WeatherEntry.WIND_DEGREES)));
                forecast.setWindSpeed(cursor.getDouble(cursor.getColumnIndex(WeatherContract.WeatherEntry.WIND_SPEED)));
                if (forecast.getMinTemp() != 0) {
                    dayForecast.add(forecast);
                }
                forecast = new DayForecast();

            }
        }
        return dayForecast;
    }
}
