package com.maxvi.max.liveweather.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
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
import com.maxvi.max.liveweather.models.Forecast;
import com.maxvi.max.liveweather.utilities.Convertation;
import com.maxvi.max.liveweather.utilities.DateUtils;
import com.maxvi.max.liveweather.utilities.WeatherUtils;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private final Context mContext;
    private List<Forecast> mData;
    private final ForecastOnClickListener mForecastOnClickListener;
    private Cursor mCursor;
    private String TAG = this.getClass().getSimpleName();

    public ForecastAdapter(@NonNull final Context pContext,
                           final ForecastOnClickListener pForecastOnClickListener,
                           final Cursor pCursor) {
        mContext = pContext;
        mForecastOnClickListener = pForecastOnClickListener;
        mCursor = pCursor;
    }

    public void swapCursor(final Cursor pCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = pCursor;
        notifyDataSetChanged();
    }

    public void setData(final List<Forecast> pData) {
        mData = pData;
    }

    public interface ForecastOnClickListener {
        void onClick(String weatherData);
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.forecast_list_item, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ForecastViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: position " + position);
        mCursor.moveToPosition(position);
        Log.d(TAG, "onBindViewHolder: " + mCursor.getInt(mCursor.getColumnIndex(WeatherContract.WeatherEntry.DATE)));
        final int date = mCursor.getInt(mCursor.getColumnIndex(WeatherContract.WeatherEntry.DATE));
        final String sDate = DateUtils.getDate(date);
        holder.dateTextView.setText(sDate);

        final int weatherId = mCursor.getInt(mCursor.getColumnIndex(WeatherContract.WeatherEntry.WEATHER_ID));
        holder.weatherImageView.setImageResource(WeatherUtils.getWeatherImageResource(weatherId, date));
        holder.descriptionTextView.setText(WeatherUtils.getStringForWeatherCondition(mContext, weatherId));

        final double maxTemp = mCursor.getDouble(mCursor.getColumnIndex(WeatherContract.WeatherEntry.TEMP_MAX));
        holder.maxTempTextView.setText(Convertation.fromKelvinToCelsius(maxTemp) + "\u00b0");
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
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
            mForecastOnClickListener.onClick(maxTempTextView.getText().toString());
        }
    }
}
