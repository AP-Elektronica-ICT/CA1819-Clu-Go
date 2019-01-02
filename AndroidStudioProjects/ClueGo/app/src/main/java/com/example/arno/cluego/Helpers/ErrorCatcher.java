package com.example.arno.cluego.Helpers;

import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


public class ErrorCatcher {

    public String ParseError(VolleyError error) {
        if (error instanceof TimeoutError)
            return ("Request timed out, please try again.");
        else {
            int status = error.networkResponse.statusCode;

            switch (status) {
                case 400:
                    try {
                        String string = new String(error.networkResponse.data);
                        JSONObject object = new JSONObject(string);
                        if (object.has("message"))
                            return object.get("message").toString();
                        else
                            return object.get("error_description").toString();

                    } catch (JSONException e) {
                        return e.toString();
                    }
            }
            return "Unknown error";
        }
    }
}

