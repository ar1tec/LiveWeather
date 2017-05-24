package com.maxvi.max.liveweather.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
        implements LoaderManager.LoaderCallbacks<Cursor>, ForecastAdapter.ForecastOnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView mHorizontalRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private HorizontalForecastAdapter mHorizontalForecastAdapter;
    private static final int CURSOR_LOADER_ID = 22;
    private ProgressBar mProgressBar;
    private TextView mErrorTextView;
    private ImageButton mMenuButton;

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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorTextView = (TextView) findViewById(R.id.tv_error_message);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mMenuButton = (ImageButton) findViewById(R.id.btn_menu);

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

        disableActionBarShadow();

    }

    private void disableActionBarShadow() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0f);
        }
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_forecast);
        mForecastAdapter = new ForecastAdapter(this, this, null);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mForecastAdapter);
    }

    private void setupHorizontalRecyclerView() {
        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.rv_horizontal_forecast);
        mHorizontalForecastAdapter = new HorizontalForecastAdapter();
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
        final TextView tvLocation = (TextView) findViewById(R.id.now_location);
        tvLocation.setText("Hrodna");
        final ImageView ivWeather = (ImageView) findViewById(R.id.now_image_weather);
        final int weatherId = data.getInt(data.getColumnIndex(WeatherContract.WeatherEntry.WEATHER_ID));
        ivWeather.setImageResource(WeatherUtils.getLargeArtResourceIdForWeatherCondition(
                weatherId
        ));
        final TextView tvDescription = (TextView) findViewById(R.id.now_description);
        tvDescription.setText(
                WeatherUtils.getStringForWeatherCondition(this, weatherId)
        );
        final double temp = data.getDouble(data.getColumnIndex(WeatherContract.WeatherEntry.TEMP_MAX));
        final TextView tvMaxTemp = (TextView) findViewById(R.id.now_temp);
        tvMaxTemp.setText(Convertation.fromKelvinToCelsius(temp));
        View view = getLayoutInflater().inflate(R.layout.forecast_item_now, null);
        TextView tv = (TextView) view.findViewById(R.id.now_temp);
        Log.d(TAG, "bindBlockNow: LOOK AT THIS " + tv.getText());
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
                result = NetworkUtils.getResponseFromURL(NetworkUtils.buildUrl("Hrodna"));

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