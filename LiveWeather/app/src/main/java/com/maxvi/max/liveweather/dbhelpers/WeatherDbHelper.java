package com.maxvi.max.liveweather.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.maxvi.max.liveweather.contracts.WeatherContract;

public class WeatherDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "weather.db";
    private static final int DB_VERSION = 1;

    public WeatherDbHelper(final Context pContext) {
        super(pContext, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        final String createQuery = "CREATE TABLE " + WeatherContract.WeatherEntry.TABLE_NAME + " (" +
                WeatherContract.WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WeatherContract.WeatherEntry.TEMP_MIN + " REAL NOT NULL, " +
                WeatherContract.WeatherEntry.TEMP_MAX + " REAL NOT NULL, " +
                WeatherContract.WeatherEntry.HUMIDITY + " INTEGER NOT NULL, " +
                WeatherContract.WeatherEntry.PRESSURE + " REAL NOT NULL, " +
                WeatherContract.WeatherEntry.WEATHER_ID + " INTEGER NOT NULL, " +
                WeatherContract.WeatherEntry.WIND_SPEED + " REAL NOT NULL, " +
                WeatherContract.WeatherEntry.WIND_DEGREES + " REAL NOT NULL, " +
                WeatherContract.WeatherEntry.DATE + " INTEGER NOT NULL " +
                ");";
        db.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WeatherContract.WeatherEntry.TABLE_NAME);
    }
}
