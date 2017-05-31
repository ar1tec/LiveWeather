package com.maxvi.max.liveweather.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxvi.max.liveweather.R;
import com.maxvi.max.liveweather.activities.MainActivity;
import com.maxvi.max.liveweather.contracts.WeatherContract;
import com.maxvi.max.liveweather.data.FakeData;
import com.maxvi.max.liveweather.models.DayForecast;
import com.maxvi.max.liveweather.models.Forecast;
import com.maxvi.max.liveweather.utilities.Convertation;
import com.maxvi.max.liveweather.utilities.DateUtils;
import com.maxvi.max.liveweather.utilities.WeatherUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private final Context mContext;
    private List<DayForecast> mDayForecastList = new ArrayList<>();
    private final ForecastOnClickListener mForecastOnClickListener;
    private Cursor mCursor;
    private final String TAG = this.getClass().getSimpleName();
    private SharedPreferences mSharedPreferences;

    public ForecastAdapter(@NonNull final Context pContext,
                           final ForecastOnClickListener pForecastOnClickListener,
                           final Cursor pCursor) {
        mContext = pContext;
        mForecastOnClickListener = pForecastOnClickListener;
        mCursor = pCursor;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void swapCursor(final Cursor pCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = pCursor;
        notifyDataSetChanged();
    }

    public void swapList(final Collection<DayForecast> pDayForecastList) {
        mDayForecastList.clear();
        mDayForecastList.addAll(pDayForecastList);
        notifyDataSetChanged();
    }

    public interface ForecastOnClickListener {

        void onClick(Bundle weatherData);
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.forecast_list_item, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ForecastViewHolder holder, final int position) {
        final String day = DateUtils.getDay(mDayForecastList.get(position).getTimestamp());
        holder.dateTextView.setText(day);

        final int weatherId = mDayForecastList.get(position).getDescription();
        final String description = WeatherUtils.getStringForWeatherCondition(mContext, weatherId);
        holder.descriptionTextView.setText(description);
        holder.weatherImageView.setImageResource(WeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId));

        final String preferenceKey = mSharedPreferences.getString(
                mContext.getString(R.string.pref_units), mContext.getString(R.string.unit_celsius));
        String maxTemp = null;
        String minTemp = null;
        if (preferenceKey.equals(mContext.getString(R.string.unit_celsius))) {
            maxTemp = Convertation.fromKelvinToCelsius(mDayForecastList.get(position).getMaxTemp());
            minTemp = Convertation.fromKelvinToCelsius(mDayForecastList.get(position).getMinTemp());
        } else if (preferenceKey.equals(mContext.getString(R.string.unit_fahrenheit))) {
            maxTemp = Convertation.fromKelvinToFahrenheit(mDayForecastList.get(position).getMaxTemp());
            minTemp = Convertation.fromKelvinToFahrenheit(mDayForecastList.get(position).getMinTemp());
        }
        holder.minTempTextView.setText(minTemp);
        holder.maxTempTextView.setText(maxTemp);

    }

    @Override
    public int getItemCount() {
        return mDayForecastList.size();
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dateTextView;
        TextView descriptionTextView;
        TextView maxTempTextView;
        TextView minTempTextView;
        ImageView weatherImageView;

        ForecastViewHolder(final View itemView) {
            super(itemView);

            dateTextView = (TextView) itemView.findViewById(R.id.tv_item_date);
            descriptionTextView = (TextView) itemView.findViewById(R.id.tv_item_description);
            maxTempTextView = (TextView) itemView.findViewById(R.id.tv_item_max_temp);
            minTempTextView = (TextView) itemView.findViewById(R.id.tv_item_min_temp);
            weatherImageView = (ImageView) itemView.findViewById(R.id.iv_weather_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            final int position = getAdapterPosition();
            final Bundle bundle = new Bundle();

            final int weatherId = mDayForecastList.get(position).getDescription();
            bundle.putInt(mContext.getString(R.string.key_weather_id), weatherId);

            final String maxTemp = maxTempTextView.getText().toString();
            final String minTemp = minTempTextView.getText().toString();
            bundle.putString(mContext.getString(R.string.key_max_temp), maxTemp);
            bundle.putString(mContext.getString(R.string.key_min_temp), minTemp);

            final int humidity = mDayForecastList.get(position).getHumidity();
            bundle.putInt(mContext.getString(R.string.key_humidity), humidity);

            final double pressure = mDayForecastList.get(position).getPressure();
            bundle.putDouble(mContext.getString(R.string.key_pressure), pressure);

            final double windSpeed = mDayForecastList.get(position).getWindSpeed();
            bundle.putDouble(mContext.getString(R.string.key_wind_speed), windSpeed);

            final double windDegrees = mDayForecastList.get(position).getWindDegrees();
            bundle.putDouble(mContext.getString(R.string.key_wind_degrees), windDegrees);

            mForecastOnClickListener.onClick(bundle);
        }
    }
}















