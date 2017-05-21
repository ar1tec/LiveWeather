package com.maxvi.max.liveweather.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.maxvi.max.liveweather.R;
import com.maxvi.max.liveweather.contracts.WeatherContract;

public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        final Intent intent = getIntent();
        if (intent != null) {
            final String data = intent.getStringExtra(getString(R.string.key_extra_weather_day_data));
        }


    }
}
