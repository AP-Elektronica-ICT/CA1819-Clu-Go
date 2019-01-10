package com.example.arno.cluego;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.arno.cluego.Objects.Game;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.getCacheDir;


public class StartGameFragment extends Activity {
    TextView gameinfo,instructions;
    Button startButton, continueBtn;
    RequestQueue mRequestQueue;
    private String jsonResponse;
    Game gameFromDatabase = new Game();
    String baseUrl;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_start_of_game);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        gameinfo = findViewById(R.id.txt_info);
        baseUrl = getResources().getString(R.string.baseUrl);
        startButton = findViewById(R.id.btn_start);
        instructions = findViewById(R.id.txt_view_instructions);
        final ProgressBar loadCircle = findViewById(R.id.progress_bar);
        continueBtn = findViewById(R.id.btn_continue);
        final String UID = preferences.getString("UID","");


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameinfo.setText(" ");
                loadCircle.setVisibility(View.VISIBLE);
                StartGame(UID);

            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameinfo.setText(" ");
                loadCircle.setVisibility(View.VISIBLE);
                LoadGame(UID);
            }
        });
    }

    private void StartGame(final String UID){
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        final ProgressBar loadCircle = findViewById(R.id.progress_bar);

        /* Start the queue */
        mRequestQueue.start();

        String urlGameInfo = baseUrl + "game/create/3/" + UID;

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGameInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(response,response.toString());
                        loadCircle.setVisibility(View.GONE);

                        Intent i = new Intent(StartGameFragment.this, MainActivity.class);
                        startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadCircle.setVisibility(View.GONE);
                        Log.i("error.reponse",error.toString());
                        if (UID == "0")
                            gameinfo.setText("Are you logged in?");
                        else
                            gameinfo.setText("Server is not responding try again later");
                    }

                });
                 mRequestQueue.add(stringRequest);
    }

    private void LoadGame(final String UID){
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        final ProgressBar loadCircle = findViewById(R.id.progress_bar);

        /* Start the queue */
        mRequestQueue.start();

        String urlGameInfo = baseUrl + "game/" + UID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGameInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(response,response);

                        loadCircle.setVisibility(View.GONE);

                        Intent i = new Intent(StartGameFragment.this, MainActivity.class);
                        startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadCircle.setVisibility(View.GONE);
                        Log.i("error.reponse",error.toString());
                        if (UID == "0")
                            gameinfo.setText("Are you logged in?");
                        else
                            gameinfo.setText("Server is not responding try again later");
                    }

                });
        mRequestQueue.add(stringRequest);
    }
}