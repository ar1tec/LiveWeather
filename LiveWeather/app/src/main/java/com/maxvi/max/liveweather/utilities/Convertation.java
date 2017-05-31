package com.maxvi.max.liveweather.utilities;

public final class Convertation {

    private static final double PARAMETER_KELVIN_TO_CELS = 273.15;
    private static final double PARAMETER_KELVIN_TO_FAHR = 459.67;

    public static String fromKelvinToCelsius(final double temp) {
        double celsius = temp - PARAMETER_KELVIN_TO_CELS;
        celsius = Math.round(celsius);
        return (int) celsius + "\u00b0";
    }

    public static String fromKelvinToFahrenheit(final double temp) {
        double fahrenheitTemp = temp * 9 / 5 - PARAMETER_KELVIN_TO_FAHR;
        fahrenheitTemp = Math.round(fahrenheitTemp);
        return (int) fahrenheitTemp + "\u00b0";
    }
}
