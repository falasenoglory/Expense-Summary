package com.beansoftph.API;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.beansoftph.models.AmountDesignation;
import com.beansoftph.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Christian on 2/26/2016.
 */
public class AmountDesignation_API {
    public static ArrayList<AmountDesignation> amountDesignation = new ArrayList<>();
    public static final String API_URL = "http://expensesummary.webraw.net/db_api/amount_designations";
    //x
    public ArrayList<AmountDesignation> getAmountDesignations(@NonNull String requestMethod) {
        String json = HttpUtils.getResponse(API_URL, requestMethod);

        if (TextUtils.isEmpty(json)) {
            Log.d("Hohoho", "wala");

            return null;
        }

        // Here we will now parse the json response and convert it into a Weather object.
        String name;



//        try {
//
//
//
//            JSONArray jArray = new JSONArray(json);
//
//
//            Log.d("Hohoho", jArray.toString());
//            for (int i=0;i<jArray.length();i++){
//                JSONObject w0 = jArray.getJSONObject(i);
//                name = w0.getString("name").toUpperCase();
//                amountDesignation.add(new AmountDesignation(name));
//
//            }
//            return amountDesignation;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//


        try {

           JSONObject jObj = new JSONObject(json);
            Iterator<String> keys = jObj.keys();
//            Toast.makeText(AmountDesignation_API.this,"Sud sa try",Toast.LENGTH_LONG);
            Log.d("chan", "sud sa try");
            while( keys.hasNext() )
            {
                String key = keys.next();
                JSONObject w0 = jObj.getJSONObject(key);
                name = w0.getString("name").toUpperCase();
                amountDesignation.add(new AmountDesignation(name));
                Log.d("chan", "sud sa while");


            }
            return amountDesignation;
        } catch (JSONException e) {
            Log.d("chan", "sud sa catch");
            e.printStackTrace();
            return null;
        }


    }
}
