package com.maxvi.max.liveweather.utilities;

import com.maxvi.max.liveweather.models.Forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParsingUtils {
    //every object is inside list
    private final String FORECAST_LIST_ARRAY = "list";

    private final String DATE_OBJECT = "dt";

    //temp & pressure inside main
    private final String MAIN_TEMP_OBJECT = "main";
    private final String TEMP_MIN = "temp_min";
    private final String TEMP_MAX = "temp_max";
    private final String PRESSURE = "pressure";

    private final String WEATHER_ARRAY = "weather";
    private final String WEATHER_ID = "id";

    private final String WIND_OBJECT = "wind";
    private final String WIND_SPEED = "speed";
    private final String WIND_DEGREES = "deg";

    public List<Forecast> parseJson(final String json) throws JSONException {
        final JSONObject jsonObject = new JSONObject(json);
        final JSONArray listArray = jsonObject.getJSONArray(FORECAST_LIST_ARRAY);
        final List<Forecast> forecastList = new ArrayList<>();

        for (int i = 0; i < listArray.length(); i++) {
            long date;
            double tempMin;
            double tempMax;
            double pressure;
            int id;
            double windSpeed;
            double windDeg;

            final JSONObject listObject = listArray.getJSONObject(i);
            date = listObject.getLong(DATE_OBJECT);

            final JSONObject mainTempObject = listObject.getJSONObject(MAIN_TEMP_OBJECT);
            tempMin = mainTempObject.getDouble(TEMP_MIN);
            tempMax = mainTempObject.getDouble(TEMP_MAX);
            pressure = mainTempObject.getDouble(PRESSURE);

            final JSONArray weatherArray = listObject.getJSONArray(WEATHER_ARRAY);
            final JSONObject weatherObject = weatherArray.getJSONObject(0);
            id = weatherObject.getInt(WEATHER_ID);

            final JSONObject windObject = listObject.getJSONObject(WIND_OBJECT);
            windSpeed = windObject.getDouble(WIND_SPEED);
            windDeg = windObject.getDouble(WIND_DEGREES);

            //TODO continue here: add Forecast to list

        }
        return null;
    }


}
