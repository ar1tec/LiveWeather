package com.maxvi.max.liveweather.data;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxvi.max.liveweather.R;
public class DataBinding {
    public static void bindNowWeather(final Context pContext, final View pView) {
        final TextView location = (TextView) pView.findViewById(R.id.now_location);
        final TextView description = (TextView) pView.findViewById(R.id.now_description);
        final TextView temp = (TextView) pView.findViewById(R.id.now_temp);
        final ImageView weatherImage = (ImageView) pView.findViewById(R.id.now_image_weather);


    }
}
