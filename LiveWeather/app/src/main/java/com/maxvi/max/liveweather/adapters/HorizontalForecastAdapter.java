package com.maxvi.max.liveweather.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxvi.max.liveweather.R;
import com.maxvi.max.liveweather.contracts.WeatherContract;
import com.maxvi.max.liveweather.models.DayForecast;
import com.maxvi.max.liveweather.models.Forecast;
import com.maxvi.max.liveweather.utilities.Convertation;
import com.maxvi.max.liveweather.utilities.DateUtils;
import com.maxvi.max.liveweather.utilities.WeatherUtils;

import java.util.List;

public class HorizontalForecastAdapter extends RecyclerView.Adapter<HorizontalForecastAdapter
        .HorizontalViewHolder> {

    private Cursor mCursor;
    private SharedPreferences mSharedPreferences;
    private final Context mContext;

    public HorizontalForecastAdapter(final Context pContext) {
        mContext = pContext;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item_horizontal,
                parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {
        mCursor.moveToPosition(position);

        final int unixTime = mCursor.getInt(mCursor.getColumnIndex(WeatherContract.WeatherEntry.DATE));
        final String time = DateUtils.getHour(unixTime);

        final String preferenceKey = mSharedPreferences.getString(
                mContext.getString(R.string.pref_units), mContext.getString(R.string.unit_celsius));
        String maxTemp = null;
        final double temp = mCursor.getDouble(mCursor.getColumnIndex(WeatherContract.WeatherEntry.TEMP_MAX));
        if (preferenceKey.equals(mContext.getString(R.string.unit_celsius))) {
            maxTemp = Convertation.fromKelvinToCelsius(temp);
        } else if (preferenceKey.equals(mContext.getString(R.string.unit_fahrenheit))) {
            maxTemp = Convertation.fromKelvinToFahrenheit(temp);
        }

        final int weatherId = mCursor.getInt(mCursor.getColumnIndex(WeatherContract.WeatherEntry.WEATHER_ID));
        Log.d("HorizontalRV", "onBindViewHolder: " + position + "  " + time);
        holder.mTemp.setText(maxTemp);
        holder.mTime.setText(time);
        holder.mWeatherImage.setImageResource(WeatherUtils.getWeatherImageResource(weatherId, unixTime));
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public void swapCursor(final Cursor pCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = pCursor;
        notifyDataSetChanged();
    }


    class HorizontalViewHolder extends RecyclerView.ViewHolder {

        TextView mTime;
        TextView mTemp;
        ImageView mWeatherImage;

        HorizontalViewHolder(final View itemView) {
            super(itemView);
            mTime = (TextView) itemView.findViewById(R.id.horizontal_time);
            mTemp = (TextView) itemView.findViewById(R.id.horizontal_temp);
            mWeatherImage = (ImageView) itemView.findViewById(R.id.horizontal_weather_image);
        }
    }
}
