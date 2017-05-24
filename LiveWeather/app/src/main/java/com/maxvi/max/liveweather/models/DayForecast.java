package com.maxvi.max.liveweather.models;

public class DayForecast {

    private double minTemp;
    private double maxTemp;
    private String day;
    private long timestamp;
    private int description;
    private double pressure;
    private int humidity;
    private double windSpeed;
    private double windDegrees;

    public DayForecast() {}

    public DayForecast(double pMinTemp, double pMaxTemp, String pDay, long pTimestamp, int pDescription, double pPressure, int pHumidity, double pWindSpeed, double pWindDegrees) {
        minTemp = pMinTemp;
        maxTemp = pMaxTemp;
        day = pDay;
        timestamp = pTimestamp;
        description = pDescription;
        pressure = pPressure;
        humidity = pHumidity;
        windSpeed = pWindSpeed;
        windDegrees = pWindDegrees;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public String getDay() {
        return day;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getDescription() {
        return description;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDegrees() {
        return windDegrees;
    }

    public void setMinTemp(double pMinTemp) {
        minTemp = pMinTemp;
    }

    public void setMaxTemp(double pMaxTemp) {
        maxTemp = pMaxTemp;
    }

    public void setDay(String pDay) {
        day = pDay;
    }

    public void setTimestamp(long pTimestamp) {
        timestamp = pTimestamp;
    }

    public void setDescription(int pDescription) {
        description = pDescription;
    }

    public void setPressure(double pPressure) {
        pressure = pPressure;
    }

    public void setHumidity(int pHumidity) {
        humidity = pHumidity;
    }

    public void setWindSpeed(double pWindSpeed) {
        windSpeed = pWindSpeed;
    }

    public void setWindDegrees(double pWindDegrees) {
        windDegrees = pWindDegrees;
    }

    @Override
    public String toString() {
        return "DayForecast{" +
                "minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", day='" + day + '\'' +
                ", timestamp=" + timestamp +
                ", description=" + description +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", windDegrees=" + windDegrees +
                '}';
    }
}
