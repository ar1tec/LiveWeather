package com.maxvi.max.liveweather.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxvi.max.liveweather.R;
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

    public ForecastAdapter(final Context pContext,
                           final ForecastOnClickListener pForecastOnClickListener) {
        mContext = pContext;
        mForecastOnClickListener = pForecastOnClickListener;
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
        final long date = mData.get(position).getDate();
        holder.dateTextView.setText(DateUtils.getDate(date));

        final int description = mData.get(position).getDescription();
        holder.descriptionTextView.setText(WeatherUtils.getStringForWeatherCondition(mContext, description));

        final double maxTemp = mData.get(position).getTempMax();
        holder.maxTempTextView.setText(Convertation.fromKelvinToCelsius(maxTemp) + "\u00b0");

        /*final double minTemp = mData.get(position).getTempMin();
        holder.minTempTextView.setText(Convertation.fromKelvinToCelsius(minTemp));*/

        holder.weatherImageView.setImageResource(WeatherUtils.getLargeArtResourceIdForWeatherCondition(
                mData.get(position).getDescription()
        ));
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
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
        public void onClick(View v) {

        }
    }
}
