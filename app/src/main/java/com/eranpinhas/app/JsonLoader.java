package com.eranpinhas.app;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonLoader {

    private Context context;

    public JsonLoader(Context mContext){
        context = mContext;
    }

    public JSONObject loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            return new JSONObject(json);
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
