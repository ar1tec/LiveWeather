package com.maxvi.max.liveweather.utilities;

public final class Convertation {

    private static final double KELVIN = 273.5;

    public static String fromKelvinToCelsius(final double temp) {

        final double celsius = temp - KELVIN;


        return String.valueOf(celsius + "&#8451");
    }

    public static String fromKelvinToFahrenheit(final String temp) {
        return null;
    }

    private static double parseStringToDouble(String temp) {
        final double num;
        try {
            num = Double.parseDouble(temp);
            return num;
        } catch (final Exception pE) {
            pE.printStackTrace();
        }
        return Double.parseDouble(null);
    }

}
