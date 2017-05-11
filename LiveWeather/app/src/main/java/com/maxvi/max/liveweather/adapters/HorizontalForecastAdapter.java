package com.maxvi.max.liveweather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxvi.max.liveweather.R;
import com.maxvi.max.liveweather.models.Forecast;
import com.maxvi.max.liveweather.utilities.Convertation;
import com.maxvi.max.liveweather.utilities.DateUtils;
import com.maxvi.max.liveweather.utilities.WeatherUtils;

import java.util.List;

public class HorizontalForecastAdapter extends RecyclerView.Adapter<HorizontalForecastAdapter
        .HorizontalViewHolder> {

    private List<Forecast> mData;

    public void setData(final List<Forecast> pData) {
        mData = pData;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item_horizontal,
                parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {
        holder.mTemp.setText(Convertation.fromKelvinToCelsius(mData.get(position).getTempMax()) + "\u00b0");
        holder.mTime.setText(DateUtils.getHour(mData.get(position).getDate()));
        holder.mWeatherImage.setImageResource(WeatherUtils.getLargeArtResourceIdForWeatherCondition(
                mData.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
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
