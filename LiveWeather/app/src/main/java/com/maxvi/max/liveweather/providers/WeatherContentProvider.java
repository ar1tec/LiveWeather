package com.maxvi.max.liveweather.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.maxvi.max.liveweather.contracts.WeatherContract;
import com.maxvi.max.liveweather.dbhelpers.WeatherDbHelper;

public class WeatherContentProvider extends ContentProvider {

    private static final int CODE_WEATHER = 100;
    private static final int CODE_WEATHER_WITH_DATE = 101;
    private final UriMatcher mUriMatcher = buildUriMatcher();
    private WeatherDbHelper mWeatherDbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(WeatherContract.AUTHORITY, WeatherContract.PATH_WEATHER, CODE_WEATHER);
        uriMatcher.addURI(WeatherContract.AUTHORITY, WeatherContract.PATH_WEATHER + "/#",
                CODE_WEATHER_WITH_DATE);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mWeatherDbHelper = new WeatherDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull final Uri uri, @Nullable final String[] projection,
                        @Nullable final String selection, @Nullable final String[] selectionArgs,
                        @Nullable final String sortOrder) {
        final int matcher = mUriMatcher.match(uri);
        final SQLiteDatabase db = mWeatherDbHelper.getReadableDatabase();
        switch (matcher) {
            case CODE_WEATHER:
                return db.query(WeatherContract.WeatherEntry.TABLE_NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        WeatherContract.WeatherEntry.DATE
                );
            case CODE_WEATHER_WITH_DATE:
                break;
            default:
                throw new UnsupportedOperationException("Wrong uri");
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull final Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull final Uri uri, @Nullable final ContentValues values) {
        final int matcher = mUriMatcher.match(uri);
        final SQLiteDatabase db = mWeatherDbHelper.getWritableDatabase();
        final Uri returnUri;
        switch (matcher) {
            case CODE_WEATHER:
                final long id = db.insert(WeatherContract.WeatherEntry.TABLE_NAME,
                        null, values);
                returnUri = ContentUris.withAppendedId(WeatherContract.WeatherEntry.CONTENT_URI, id);
                break;
            default:
                throw new UnsupportedOperationException("Wrong uri");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull final Uri uri, @NonNull final ContentValues[] values) {
        final int matcher = mUriMatcher.match(uri);
        final SQLiteDatabase db = mWeatherDbHelper.getWritableDatabase();
        int insertedItemsCount = 0;
        switch (matcher) {
            case CODE_WEATHER:
                try {
                    db.beginTransaction();
                    for (final ContentValues value : values) {
                        final long id = db.insert(WeatherContract.WeatherEntry.TABLE_NAME,
                                null,
                                value);
                        if (id != -1) {
                            insertedItemsCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                break;
            default:
                throw new UnsupportedOperationException("Wrong uri");
        }
        if (insertedItemsCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return insertedItemsCount;
    }

    @Override
    public int delete(@NonNull final Uri uri, @Nullable final String selection,
                      @Nullable final String[] selectionArgs) {
        final int matcher = mUriMatcher.match(uri);
        final SQLiteDatabase db = mWeatherDbHelper.getWritableDatabase();
        final int deletedRows;
        switch (matcher) {
            case CODE_WEATHER:
                deletedRows = db.delete(WeatherContract.WeatherEntry.TABLE_NAME,
                        null,
                        null);
                break;
            default:
                throw new UnsupportedOperationException("Wrong uri");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return deletedRows;
    }

    @Override
    public int update(@NonNull final Uri uri, @Nullable final ContentValues values,
                      @Nullable final String selection, @Nullable final String[] selectionArgs) {
        return 0;
    }
}
