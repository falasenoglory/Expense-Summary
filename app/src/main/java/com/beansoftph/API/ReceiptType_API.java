package com.beansoftph.API;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.beansoftph.models.ReceiptType;
import com.beansoftph.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Christian on 2/26/2016.
 */
public class ReceiptType_API {

    public static final String API_URL = "http://expensesummary.webraw.net/db_api/receipt_types";

    public static ArrayList<String> getReceiptType(String sring, @NonNull String requestMethod) {
        String json = HttpUtils.getResponse(API_URL, requestMethod);

        if (TextUtils.isEmpty(json)) {
            Log.d("Hohoho","wala");

            return null;
        }

        // Here we will now parse the json response and convert it into a Weather object.
        String name;
        ArrayList<String> rectype;
        rectype = new ArrayList<>();

        try {



            JSONArray jArray = new JSONArray(json);
            JSONObject w0 = jArray.getJSONObject(50);

            Log.d("Hohoho", jArray.toString());
            for (int i=0;i<jArray.length();i++){
                name = w0.getString("name").toUpperCase();
                rectype.add(name);

            }
            return rectype;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
