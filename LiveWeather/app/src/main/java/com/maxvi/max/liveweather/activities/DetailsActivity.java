package com.maxvi.max.liveweather.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.maxvi.max.liveweather.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView mDataTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mDataTextView = (TextView) findViewById(R.id.tv_weather_data);

        final Intent intent = getIntent();
        if (intent != null) {
            final String data = intent.getStringExtra(getString(R.string.key_extra_weather_day_data));
            mDataTextView.setText(data);
        }
    }
}
