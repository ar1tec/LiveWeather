package com.maxvi.max.liveweather.utilities;

public final class Convertation {

    private static final int KELVIN = 273;

    public static String fromKelvinToCelsius(final double temp) {
        int a = (int) Math.round(temp);
        final int celsius = a - KELVIN;


        return String.valueOf(celsius);
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
