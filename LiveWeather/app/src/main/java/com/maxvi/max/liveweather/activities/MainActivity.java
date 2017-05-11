package com.maxvi.max.liveweather.activities;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maxvi.max.liveweather.R;
import com.maxvi.max.liveweather.adapters.ForecastAdapter;
import com.maxvi.max.liveweather.adapters.HorizontalForecastAdapter;
import com.maxvi.max.liveweather.models.Forecast;
import com.maxvi.max.liveweather.models.ForecastList;
import com.maxvi.max.liveweather.utilities.Convertation;
import com.maxvi.max.liveweather.utilities.NetworkUtils;
import com.maxvi.max.liveweather.utilities.ParsingUtils;
import com.maxvi.max.liveweather.utilities.WeatherUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ForecastAdapter.ForecastOnClickListener,
        LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView mHorizontalRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private HorizontalForecastAdapter mHorizontalForecastAdapter;
    private static final int LOADER_ID = 22;
    private ProgressBar mProgressBar;
    private TextView mErrorTextView;

    @Override
    public void onClick(final String weatherData) {
        final Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(getString(R.string.key_extra_weather_day_data), weatherData);
        startActivity(intent);

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorTextView = (TextView) findViewById(R.id.tv_error_message);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading);

        setupRecyclerView();
        setupHorizontalRecyclerView();

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        disableActionBarShadow();
    }

    private void disableActionBarShadow() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_forecast);
        mForecastAdapter = new ForecastAdapter(this, this);
        mRecyclerView.setAdapter(mForecastAdapter);
    }

    private void setupHorizontalRecyclerView() {
        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.rv_horizontal_forecast);
        mHorizontalForecastAdapter = new HorizontalForecastAdapter();
        mHorizontalRecyclerView.setAdapter(mHorizontalForecastAdapter);
    }

    @Override
    public Loader<String> onCreateLoader(final int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            private String mData;

            @Override
            protected void onStartLoading() {
                if (mData != null) {
                    deliverResult(mData);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {
                String response = null;
                try {
                    response = NetworkUtils.getResponseFromURL(NetworkUtils.buildUrl("Hrodna"));
                } catch (final IOException pE) {
                    pE.printStackTrace();
                }
                return response;
            }

            @Override
            public void deliverResult(final String data) {
                mData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(final Loader<String> loader, final String data) {

        mProgressBar.setVisibility(View.INVISIBLE);
        final List<Forecast> forecastList;
        try {
            forecastList = ParsingUtils.parseJson(data);

            if (forecastList != null) {

                //TODO make it right
                TextView location = (TextView) findViewById(R.id.now_location);
                TextView description = (TextView) findViewById(R.id.now_description);
                TextView temp = (TextView) findViewById(R.id.now_temp);
                ImageView weatherImage = (ImageView) findViewById(R.id.now_image_weather);

                location.setText("Hrodna");
                description.setText(WeatherUtils.getStringForWeatherCondition(
                        this, forecastList.get(0).getDescription()
                        ));
                temp.setText(Convertation.fromKelvinToCelsius(forecastList.get(0).getTempMax())
                        + "\u00b0");
                weatherImage.setImageResource(WeatherUtils.getLargeArtResourceIdForWeatherCondition(
                        forecastList.get(0).getDescription()));

                mForecastAdapter.setData(forecastList);
                mHorizontalForecastAdapter.setData(forecastList);
                mHorizontalForecastAdapter.notifyDataSetChanged();
                mForecastAdapter.notifyDataSetChanged();
            } else {
                showErrorMessage();
            }



        } catch (final JSONException pE) {
            pE.printStackTrace();
        }

    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(final Loader<String> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}