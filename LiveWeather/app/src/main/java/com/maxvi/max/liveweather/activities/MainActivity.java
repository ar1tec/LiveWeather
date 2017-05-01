package com.maxvi.max.liveweather.activities;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.maxvi.max.liveweather.R;
import com.maxvi.max.liveweather.adapters.ForecastAdapter;
import com.maxvi.max.liveweather.utilities.NetworkUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements ForecastAdapter.ForecastOnClickListener,
        LoaderManager.LoaderCallbacks<String> {

    private RecyclerView mRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private static final int LOADER_ID = 22;

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

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_forecast);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mForecastAdapter = new ForecastAdapter(this, this);
        mRecyclerView.setAdapter(mForecastAdapter);

        if (getSupportLoaderManager().getLoader(LOADER_ID) == null) {
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }


    @Override
    public Loader<String> onCreateLoader(final int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            String data;

            @Override
            protected void onStartLoading() {
                if (data != null) {
                    deliverResult(data);
                } else {
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

        };
    }

    @Override
    public void onLoadFinished(final Loader<String> loader, final String data) {
        Log.d("MainActivity", "onLoadFinished: " + data);
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
