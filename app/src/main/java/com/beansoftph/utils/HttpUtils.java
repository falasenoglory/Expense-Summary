package com.beansoftph.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * A utility class that handles all Http related calls and services.
 */
public class HttpUtils {
    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;

    private static final String LOG_TAG = HttpUtils.class.getSimpleName();

    /**
     * Retrieves the response data in String format from the specifed URL.
     *
     * @param sUrl          the well-formed URL to retrieve the data from
     * @param requestMethod the method used for requesting data (e.g., POST, GET, PUT, DELETE, etc).
     * @return the response data in String format.
     */
    public static String getResponse(String sUrl, String requestMethod) {
        if (TextUtils.isEmpty(sUrl)) {
            throw new RuntimeException("Passed URL is either null or empty.");
        }
        if (TextUtils.isEmpty(requestMethod)) {
            throw new RuntimeException("Request Method is null or empty");
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            if(Objects.equals(requestMethod, "GET")) {
                URL url = new URL(sUrl);

                // Create the request and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(requestMethod);
                urlConnection.connect();

                // Read the input stream and convert into a response string
                InputStream inputStream = urlConnection.getInputStream();

                // This should not be changed to StringBuilder as this method must be invoked on a
                // background thread.
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line);
                    buffer.append("\n");
                }

                if (buffer.length() == 0) {
                    // The Stream was empty. No point in pasing
                    return null;
                }
            }
            else (Objects.equals(requestMethod, "POST")){
                try {
                    URL urlObj = new URL(sUrl);

                    HttpURLConnection  conn = (HttpURLConnection) urlObj.openConnection();

                    conn.setDoOutput(true);

                    conn.setRequestMethod("POST");

                    conn.setRequestProperty("Accept-Charset", "UTF-8");

                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);

                    conn.connect();

                    paramsString = sbParams.toString();

                    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                    wr.writeBytes(paramsString);
                    wr.flush();
                    wr.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error retrieving data from server", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }


    /**
     * Retrieves the response data in String format from the specifed URI.
     *
     * @param uri           the built uri to retrieve data from.
     * @param requestMethod the method used for requesting data (e.g., POST, GET, PUT, DELETE, etc).
     * @return the response data in String format.
     */
//    public static String getResponse(Uri uri, String requestMethod) {
//        return getResponse(uri.toString(), requestMethod);
//    }
}
