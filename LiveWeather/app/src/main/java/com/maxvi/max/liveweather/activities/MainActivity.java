package com.maxvi.max.liveweather.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maxvi.max.liveweather.R;
import com.maxvi.max.liveweather.adapters.ForecastAdapter;
import com.maxvi.max.liveweather.adapters.HorizontalForecastAdapter;
import com.maxvi.max.liveweather.contracts.WeatherContract;
import com.maxvi.max.liveweather.data.DataBinding;
import com.maxvi.max.liveweather.utilities.Convertation;
import com.maxvi.max.liveweather.utilities.NetworkUtils;
import com.maxvi.max.liveweather.utilities.ParsingUtils;
import com.maxvi.max.liveweather.utilities.WeatherUtils;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        ForecastAdapter.ForecastOnClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView mHorizontalRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private HorizontalForecastAdapter mHorizontalForecastAdapter;
    private static final int CURSOR_LOADER_ID = 22;
    private ProgressBar mProgressBar;
    private TextView mErrorTextView;
    private ImageButton mMenuButton;
    private TextView mLocationTextView;
    private double mNowTemp;

    public void onRefreshClick(final View view) {
        new FetchWeatherTask().execute();
    }

    @Override
    public void onClick(final Bundle weatherData) {
        final Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtras(weatherData);
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences,
                                          final String key) {
        if (key.equals(getString(R.string.pref_location))) {
            final String location = sharedPreferences.getString(getString(R.string.pref_location),
                    getString(R.string.pref_location_default));
            new FetchWeatherTask().execute();
            mLocationTextView.setText(location);
        } else if (key.equals(getString(R.string.pref_units))) {
            mForecastAdapter.notifyDataSetChanged();
            mHorizontalForecastAdapter.notifyDataSetChanged();
            final TextView tvMaxTemp = (TextView) findViewById(R.id.now_temp);
            final String units = sharedPreferences.getString(key, getString(R.string.unit_celsius));
            final double temp = mNowTemp;
            String maxTemp = null;
            if (units.equals(getString(R.string.unit_celsius))) {
                maxTemp = Convertation.fromKelvinToCelsius(temp);
            } else if (units.equals(getString(R.string.unit_fahrenheit))) {
                maxTemp = Convertation.fromKelvinToFahrenheit(temp);
            }

            tvMaxTemp.setText(maxTemp);
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorTextView = (TextView) findViewById(R.id.tv_error_message);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mMenuButton = (ImageButton) findViewById(R.id.btn_menu);
        mLocationTextView = (TextView) findViewById(R.id.now_location);

        mMenuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                final PopupMenu popupMenu = new PopupMenu(MainActivity.this, mMenuButton);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(final MenuItem item) {
                        final int id = item.getItemId();
                        switch (id) {
                            case R.id.action_settings:
                                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        setupRecyclerView();
        setupHorizontalRecyclerView();

        getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);

    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_forecast);
        mForecastAdapter = new ForecastAdapter(this, this, null);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mForecastAdapter);
    }

    private void setupHorizontalRecyclerView() {
        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.rv_horizontal_forecast);
        mHorizontalForecastAdapter = new HorizontalForecastAdapter(this);
        mHorizontalRecyclerView.setHasFixedSize(true);
        mHorizontalRecyclerView.setAdapter(mHorizontalForecastAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(final int loaderId, final Bundle args) {

        switch (loaderId) {
            case CURSOR_LOADER_ID:
                mProgressBar.setVisibility(View.VISIBLE);
                return new CursorLoader(this,
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        WeatherContract.WeatherEntry.DATE + " ASC");
            default:
                throw new RuntimeException("Loader not implemented " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
        if (data == null) {
            new FetchWeatherTask().execute();
            return;
        }
        data.moveToFirst();
        if (data.getCount() != 0) {
            bindBlockNow(data);
        }

        mForecastAdapter.swapCursor(data);
        mForecastAdapter.swapList(DataBinding.makeDailyWeatherList(data));
        mHorizontalForecastAdapter.swapCursor(data);

        mProgressBar.setVisibility(View.GONE);
    }

    private void bindBlockNow(final Cursor data) {
        data.moveToFirst();
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String location = preferences.getString(getString(R.string.pref_location),
                getString(R.string.pref_location_default));
        mLocationTextView.setText(location);
        final ImageView ivWeather = (ImageView) findViewById(R.id.now_image_weather);
        final int weatherId = data.getInt(data.getColumnIndex(WeatherContract.WeatherEntry.WEATHER_ID));
        ivWeather.setImageResource(WeatherUtils.getLargeArtResourceIdForWeatherCondition(
                weatherId
        ));
        final TextView tvDescription = (TextView) findViewById(R.id.now_description);
        tvDescription.setText(
                WeatherUtils.getStringForWeatherCondition(this, weatherId)
        );

        final String preferenceKey = preferences.getString(
                getString(R.string.pref_units), getString(R.string.unit_celsius));
        String maxTemp = null;
        final double temp = data.getDouble(data.getColumnIndex(WeatherContract.WeatherEntry.TEMP_MAX));
        if (preferenceKey.equals(getString(R.string.unit_celsius))) {
            maxTemp = Convertation.fromKelvinToCelsius(temp);
        } else if (preferenceKey.equals(getString(R.string.unit_fahrenheit))) {
            maxTemp = Convertation.fromKelvinToFahrenheit(temp);
        }

        mNowTemp = temp;
        final TextView tvMaxTemp = (TextView) findViewById(R.id.now_temp);
        tvMaxTemp.setText(maxTemp);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(final Loader<Cursor> loader) {

    }

    private class FetchWeatherTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(final Void... params) {
            final String result;
            try {
                final SharedPreferences preferences = PreferenceManager
                        .getDefaultSharedPreferences(MainActivity.this);
                final String location = preferences.getString(getString(R.string.pref_location),
                        getString(R.string.pref_location_default));
                result = NetworkUtils.getResponseFromURL(NetworkUtils
                        .buildUrl(location));

                getContentResolver().delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null
                );

                final ContentValues[] contentValues = ParsingUtils.parseJsonTODB(result);
                getContentResolver().bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        contentValues
                );
            } catch (final IOException | JSONException pE) {
                pE.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(final Void pVoid) {
            getSupportLoaderManager().restartLoader(CURSOR_LOADER_ID, null, MainActivity.this);
        }
    }
}