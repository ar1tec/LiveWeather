package com.maxvi.max.liveweather.models;

import java.util.ArrayList;
import java.util.List;

public class ForecastList {

    private static ForecastList mInstance;
    private List<Forecast> mForecastList = new ArrayList<>();

    private ForecastList() {}

    public static ForecastList getInstance() {
        if (mInstance != null) {
            return mInstance;
        }
        mInstance = new ForecastList();
        return mInstance;
    }

    public List<Forecast> getForecastList() {
        return mForecastList;
    }

    public void setForecastList(List<Forecast> pForecastList) {
        mForecastList = pForecastList;
    }
}
