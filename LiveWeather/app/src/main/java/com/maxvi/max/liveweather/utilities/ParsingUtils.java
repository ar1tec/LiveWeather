package com.maxvi.max.liveweather.utilities;

import com.maxvi.max.liveweather.models.Forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class ParsingUtils {
    //every object is inside list
    private static final String FORECAST_LIST_ARRAY = "list";

    private static final String DATE_OBJECT = "dt";

    //temp & pressure inside main
    private static final String MAIN_TEMP_OBJECT = "main";
    private static final String TEMP_MIN = "temp_min";
    private static final String TEMP_MAX = "temp_max";
    private static final String PRESSURE = "pressure";
    private static final String HUMIDITY = "humidity";

    private static final String WEATHER_ARRAY = "weather";
    private static final String WEATHER_ID = "id";

    private static final String WIND_OBJECT = "wind";
    private static final String WIND_SPEED = "speed";
    private static final String WIND_DEGREES = "deg";

    public static List<Forecast> parseJson(final String json) throws JSONException {
        final JSONObject jsonObject = new JSONObject(json);
        final JSONArray listArray = jsonObject.getJSONArray(FORECAST_LIST_ARRAY);
        final List<Forecast> forecastList = new ArrayList<>();

        for (int i = 0; i < listArray.length(); i++) {
            final long date;
            final double tempMin;
            final double tempMax;
            final double pressure;
            final int id;
            final double windSpeed;
            final double windDeg;
            final int humidity;

            final JSONObject listObject = listArray.getJSONObject(i);
            date = listObject.getLong(DATE_OBJECT);

            final JSONObject mainTempObject = listObject.getJSONObject(MAIN_TEMP_OBJECT);
            tempMin = mainTempObject.getDouble(TEMP_MIN);
            tempMax = mainTempObject.getDouble(TEMP_MAX);
            pressure = mainTempObject.getDouble(PRESSURE);
            humidity = mainTempObject.getInt(HUMIDITY);

            final JSONArray weatherArray = listObject.getJSONArray(WEATHER_ARRAY);
            final JSONObject weatherObject = weatherArray.getJSONObject(0);
            id = weatherObject.getInt(WEATHER_ID);

            final JSONObject windObject = listObject.getJSONObject(WIND_OBJECT);
            windSpeed = windObject.getDouble(WIND_SPEED);
            windDeg = windObject.getDouble(WIND_DEGREES);

            final Forecast forecast = new Forecast(tempMin, tempMax, pressure, humidity, id, windSpeed,
                    windDeg, date);
            forecastList.add(forecast);
        }
        return forecastList;
    }


}
