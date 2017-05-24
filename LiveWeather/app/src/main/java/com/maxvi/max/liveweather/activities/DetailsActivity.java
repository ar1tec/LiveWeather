package com.maxvi.max.liveweather.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxvi.max.liveweather.R;
import com.maxvi.max.liveweather.contracts.WeatherContract;
import com.maxvi.max.liveweather.utilities.WeatherUtils;

public class DetailsActivity extends AppCompatActivity {

    private ImageButton mBackButton;
    private ImageButton mMenuButton;
    private ImageView mWeatherImageView;
    private TextView mDescriptionTextView;
    private TextView mMaxTempTextView;
    private TextView mMinTempTextView;
    private TextView mHumidityTextView;
    private TextView mPressureTextView;
    private TextView mWindTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        findViews();
        setOnClickListeners();

        final Intent intent = getIntent();
        if (intent != null) {
            final Bundle bundle = intent.getExtras();
            final int weatherId = bundle.getInt(getString(R.string.key_weather_id));
            mWeatherImageView.setImageResource(
                    WeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId));
            final String description = WeatherUtils.getStringForWeatherCondition(this, weatherId);
            mDescriptionTextView.setText(description);
            mMaxTempTextView.setText(bundle.getString(getString(R.string.key_max_temp)));
            mMinTempTextView.setText(bundle.getString(getString(R.string.key_min_temp)));
            final int humidity = bundle.getInt(getString(R.string.key_humidity));
            mHumidityTextView.setText(humidity + "%");
            final double pressure = bundle.getDouble(getString(R.string.key_pressure));
            mPressureTextView.setText(pressure + "mmHg");
            final double windSpeed = bundle.getDouble(getString(R.string.key_wind_speed));
            final double windDir = bundle.getDouble(getString(R.string.key_wind_degrees));
            mWindTextView.setText(WeatherUtils.getFormattedWind(windSpeed, windDir));
        }


    }

    private void findViews() {
        mBackButton = (ImageButton) findViewById(R.id.home_btn_back);
        mMenuButton = (ImageButton) findViewById(R.id.home_btn_menu);
        mWeatherImageView = (ImageView) findViewById(R.id.details_weather_image);
        mDescriptionTextView = (TextView) findViewById(R.id.details_description);
        mMaxTempTextView = (TextView) findViewById(R.id.details_max_temp);
        mMinTempTextView = (TextView) findViewById(R.id.details_min_temp);
        mHumidityTextView = (TextView) findViewById(R.id.details_humidity_value);
        mPressureTextView = (TextView) findViewById(R.id.details_pressure_value);
        mWindTextView = (TextView) findViewById(R.id.details_wind_value);
    }

    private void setOnClickListeners() {
        if (mBackButton != null) {
            mBackButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    onBackPressed();
                }
            });
        }
        if (mMenuButton != null) {
            mMenuButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    final PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, mMenuButton);
                    popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(final MenuItem item) {
                            final int id = item.getItemId();
                            switch (id) {
                                case R.id.action_settings:
                                    startActivity(new Intent(DetailsActivity.this,
                                            SettingsActivity.class));
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }
}
