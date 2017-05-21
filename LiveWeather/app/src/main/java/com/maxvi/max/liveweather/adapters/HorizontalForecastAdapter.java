package com.maxvi.max.liveweather.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxvi.max.liveweather.R;
import com.maxvi.max.liveweather.contracts.WeatherContract;
import com.maxvi.max.liveweather.models.Forecast;
import com.maxvi.max.liveweather.utilities.Convertation;
import com.maxvi.max.liveweather.utilities.DateUtils;
import com.maxvi.max.liveweather.utilities.WeatherUtils;

import java.util.List;

public class HorizontalForecastAdapter extends RecyclerView.Adapter<HorizontalForecastAdapter
        .HorizontalViewHolder> {

    private Cursor mCursor;

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
        final String temp = Convertation.fromKelvinToCelsius(
                mCursor.getDouble(mCursor.getColumnIndex(WeatherContract.WeatherEntry.TEMP_MAX))
        );
        final int weatherId = mCursor.getInt(mCursor.getColumnIndex(WeatherContract.WeatherEntry.WEATHER_ID));
        Log.d("HorizontalRV", "onBindViewHolder: " + position + "  " + time);
        holder.mTemp.setText(temp + "\u00b0");
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

    public void swapCursor(Cursor pCursor) {
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
