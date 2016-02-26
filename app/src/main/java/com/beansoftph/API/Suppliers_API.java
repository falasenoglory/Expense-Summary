package com.beansoftph.API;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.beansoftph.models.Supplier;
import com.beansoftph.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Christian on 2/26/2016.
 */
public class Suppliers_API {

    public static final String API_URL = "http://expensesummary.webraw.net/db_api/suppliers";

    public static ArrayList<Supplier> getSuppliers(String sring, @NonNull String requestMethod) {
        String json = HttpUtils.getResponse(API_URL, requestMethod);

        if (TextUtils.isEmpty(json)) {
            Log.d("Hohoho", "wala");

            return null;
        }

        // Here we will now parse the json response and convert it into a Weather object.
        String name,tin;
        ArrayList<Supplier> suppList;
        suppList = new ArrayList<>();


        try {


            Supplier supplier = null;
            JSONArray jArray = new JSONArray(json);
            JSONObject w0 = jArray.getJSONObject(50);

            Log.d("Hohoho", jArray.toString());
            for (int i=0;i<jArray.length();i++){
                name = w0.getString("name").toUpperCase();
                tin = w0.getString("tin").toUpperCase();
                supplier = new Supplier(name,
                        tin);
                suppList.add(supplier);

            }
            return suppList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
