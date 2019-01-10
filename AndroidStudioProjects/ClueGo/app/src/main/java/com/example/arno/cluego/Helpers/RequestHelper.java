package com.example.arno.cluego.Helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.LoginActivity;
import com.example.arno.cluego.Objects.Clue;
import com.example.arno.cluego.StartGameActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RequestHelper {
    private static RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    public static List<Clue> clues;
    public String BASE_URL= "https://clugo.azurewebsites.net/api/";
    public String url;

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

        stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
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

    public void getClues(final int gameId, Context context, SuccessCallBack callBack, final String _url){
        /*mRequestQueue = Volley.newRequestQueue(context);
        url = BASE_URL + _url;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<Arr>() {
            @Override
            public void onResponse(String response) {
                Log.d("RequestHelper", response);

                try{
                    JSONObject userObj = new JSONObject(response);

                    clues.setUserId(userObj.getInt("userId"));
                    user.setEmail(userObj.getString("email"));
                    user.setUsername(userObj.getString("username"));
                    user.setCluesFound(userObj.getInt("cluesFound"));
                    user.setDistanceWalked(userObj.getInt("distanceWalked"));
                    user.setGamesPlayed(userObj.getInt("gamesPlayed"));

                    spinner.setVisibility(View.INVISIBLE);

                    Intent i = new Intent(LoginActivity.this, StartGameActivity.class);
                    i.putExtra("userDataPackage",user);
                    startActivity(i);
                }catch(JSONException e)
                {
                    Log.d("JSonErr", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setTextColor(Color.RED);
                tv.setText(requestHelper.ParseError(error));

                spinner.setVisibility(View.INVISIBLE);
            }
        });
        mRequestQueue.add(stringRequest);
    }*/

    }


}
