package com.maxvi.max.liveweather.data;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.maxvi.max.liveweather.contracts.WeatherContract;

public class ReloadDataTask extends AsyncTask<ContentValues[], Void, Void> {

    private final Context mContext;

    public ReloadDataTask(final Context pContext) {
        mContext = pContext;
    }

    @Override
    protected Void doInBackground(final ContentValues[]... params) {
        if (params.length == 0) {
            return null;
        }
        final ContentValues[] contentValues = params[0];
        if (contentValues == null || contentValues.length == 0) {
            return null;
        }
        mContext.getContentResolver().delete(
                WeatherContract.WeatherEntry.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().bulkInsert(
                WeatherContract.WeatherEntry.CONTENT_URI,
                contentValues
        );
        return null;
    }
}
