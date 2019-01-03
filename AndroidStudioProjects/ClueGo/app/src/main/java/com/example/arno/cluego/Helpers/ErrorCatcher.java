package com.example.arno.cluego.Helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.EndActivity;
import com.example.arno.cluego.StartGameFragment;

import org.json.JSONException;
import org.json.JSONObject;


public class ErrorCatcher {
    private RequestQueue mRequestQueue;

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

    public void delete(final int userId, Context context) {
            mRequestQueue = Volley.newRequestQueue(context);
            String url= "https://clugo.azurewebsites.net/api/game/" + userId;

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("EndActivity", response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error.response", error.toString());
                }
            });
            mRequestQueue.add(stringRequest);
        }
}
