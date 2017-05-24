package com.maxvi.max.liveweather.utilities;

public final class Convertation {

    private static final int KELVIN = 273;

    public static String fromKelvinToCelsius(final double temp) {
        final int a = (int) Math.round(temp);
        final int celsius = a - KELVIN;


        return celsius + "\u00b0";
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
