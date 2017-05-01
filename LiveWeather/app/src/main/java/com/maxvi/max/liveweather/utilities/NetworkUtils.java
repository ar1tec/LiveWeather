package com.maxvi.max.liveweather.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String DEFAULT_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private static final String API_KEY = "6564c15f01e75f23035b0c24b98020ac";
    private static final String API_PARAMETER = "APPID";

    private static final String QUERY_PARAMETER = "q";

    private static final String RESPONSE_MODE = "mode";
    private static final String JSON_RESPONSE = "json";

    public static URL buildUrl(final String city) {
        final Uri uri = Uri.parse(DEFAULT_URL).buildUpon()
                .appendQueryParameter(API_PARAMETER, API_KEY)
                .appendQueryParameter(RESPONSE_MODE, JSON_RESPONSE)
                .appendQueryParameter(QUERY_PARAMETER, city)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
            Log.d("NetworkUtils", "buildUrl: " + uri);
        } catch (final MalformedURLException pE) {
            pE.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromURL(final URL url) throws IOException {
        final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        final InputStream inputStream = urlConnection.getInputStream();
        try {
            final Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                return scanner.next();
            }
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

}
