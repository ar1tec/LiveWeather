package com.maxvi.max.liveweather.models;

public class Forecast {

    private double tempMin;
    private double tempMax;
    private double pressure;
    private double humidity;
    private int description;
    private double windSpeed;
    private double windDegrees;
    private long date;

    public Forecast(final double pTempMin, final double pTempMax,
                    final double pPressure, final double pHumidity,
                    final int pDescription, final double pWindSpeed,
                    final double pWindDegrees, final long pDate) {
        tempMin = pTempMin;
        tempMax = pTempMax;
        pressure = pPressure;
        humidity = pHumidity;
        description = pDescription;
        windSpeed = pWindSpeed;
        windDegrees = pWindDegrees;
        date = pDate;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(final double pTempMin) {
        tempMin = pTempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(final double pTempMax) {
        tempMax = pTempMax;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(final double pPressure) {
        pressure = pPressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(final double pHumidity) {
        humidity = pHumidity;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(final int pDescription) {
        description = pDescription;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(final double pWindSpeed) {
        windSpeed = pWindSpeed;
    }

    public double getWindDegrees() {
        return windDegrees;
    }

    public void setWindDegrees(final double pWindDegrees) {
        windDegrees = pWindDegrees;
    }

    public long getDate() {
        return date;
    }

    public void setDate(final long pDate) {
        date = pDate;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", description=" + description +
                ", windSpeed=" + windSpeed +
                ", windDegrees=" + windDegrees +
                ", date=" + date +
                '}';
    }
}
