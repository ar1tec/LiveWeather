package com.maxvi.max.liveweather;

import android.database.Cursor;
import android.util.Log;

import com.maxvi.max.liveweather.activities.MainActivity;
import com.maxvi.max.liveweather.contracts.WeatherContract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class DbUnitTest {

    @Test
    public void dbTest() {
        final MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);

        final Cursor cursor = mainActivity.getContentResolver().query(
                WeatherContract.WeatherEntry.CONTENT_URI,
                null,
                null,
                null,
                WeatherContract.WeatherEntry.DATE + " ASC"
        );

        Log.d("DbUnitTest", "dbTest() " + cursor);

    }
}
