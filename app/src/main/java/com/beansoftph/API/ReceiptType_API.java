package com.beansoftph.API;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.beansoftph.models.ReceiptType;
import com.beansoftph.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Christian on 2/26/2016.
 */
public class ReceiptType_API {

    public static final String API_URL = "http://expensesummary.webraw.net/db_api/receipt_types";

    public static ArrayList<ReceiptType> getReceiptType( @NonNull String requestMethod) {
        String json = HttpUtils.getResponse(API_URL, requestMethod);

        if (TextUtils.isEmpty(json)) {
            Log.d("Hohoho","wala");

            return null;
        }

        // Here we will now parse the json response and convert it into a Weather object.
        String name;
        ArrayList<ReceiptType> rectype;
        rectype = new ArrayList<>();

//        try {
//
//
//
//
//            JSONObject w0 = jArray.getJSONObject(50);
//
//            Log.d("Hohoho", jArray.toString());
//            for (int i=0;i<jArray.length();i++){
//                name = w0.getString("name").toUpperCase();
//                rectype.add(name);
//
//            }
//            return rectype;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
        JSONObject jObj = null;
        try {
            ReceiptType rec = null;
            jObj = new JSONObject(json);
            Iterator<String> keys = jObj.keys();
            while( keys.hasNext() )
            {
                String key = keys.next();
                JSONObject w0 = jObj.getJSONObject(key);
                name = w0.getString("name").toUpperCase();
                rec = new ReceiptType(name);
                rectype.add(rec);


            }
            return rectype;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
