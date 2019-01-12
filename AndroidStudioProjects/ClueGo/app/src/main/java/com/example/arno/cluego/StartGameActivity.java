package com.example.arno.cluego;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Helpers.RequestHelper;
import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class StartGameActivity extends Activity implements Serializable {
    TextView gameinfo, instructions, tvWelcomeMsg;
    EditText etAmtSus;
    Button startBtn, continueBtn, testBtn;
    RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    String baseUrl, username;
    int gameId;

    SharedPreferences prefs;

    private String jsonResponse;
    private boolean confirmed;
    Game gameFromDatabase = new Game();

    RequestHelper requestHelper = new RequestHelper();
    User usr = new User();

    public int UID, amtItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_start_of_game);
        baseUrl = getResources().getString(R.string.baseUrl);

        prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        UID = Integer.parseInt(prefs.getString("userId", "0"));
        username = prefs.getString("userName", "UserNotLoaded");

        gameId = UID;

        gameinfo = findViewById(R.id.txt_info);
        startBtn = findViewById(R.id.btn_start);
        tvWelcomeMsg = findViewById(R.id.tv_welcome);
        instructions = findViewById(R.id.txt_view_instructions);
        final ProgressBar loadCircle = findViewById(R.id.progress_bar);
        continueBtn = findViewById(R.id.btn_continue);
        testBtn = findViewById(R.id.btn_test);
        etAmtSus = findViewById(R.id.et_amtSus);

        tvWelcomeMsg.setText("Welcome back " + username + "!");

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amtItems = Integer.parseInt(etAmtSus.getText().toString());
                loadCircle.setVisibility(View.VISIBLE);
                StartGame(UID, amtItems);
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
        
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testBtn.setVisibility(View.VISIBLE);
                etAmtSus.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                startBtn.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void StartGame(final int UID, int amtItems){

        final ProgressBar loadCircle = findViewById(R.id.progress_bar);

        mRequestQueue = Volley.newRequestQueue(this);

        String urlGameInfo = baseUrl + "game/create/" + UID + "/" + amtItems;

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGameInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Log.i(response,response);
                            gameinfo.setText(response);
                            loadCircle.setVisibility(View.GONE);

                            LoadGame(UID);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadCircle.setVisibility(View.GONE);
                        String errorMessage = requestHelper.ParseError(error);
                        gameinfo.setText(errorMessage);

                        if (errorMessage.contains("already") && !confirmed)
                            ConfirmRemoveGame();
                        else
                            LoadGame(UID);
                    }
                });
        mRequestQueue.add(stringRequest);
    }

    private void LoadGame(final int UID){
        /*Intent i = new Intent(StartGameActivity.this, MainActivity.class);
        i.putExtra("userId", UID);
        i.putExtra("userDataPackage", usr);
        startActivity(i);*/

        final ProgressBar loadCircle = findViewById(R.id.progress_bar);
        //Start the queue
        //mRequestQueue.start();
        mRequestQueue = Volley.newRequestQueue(this);

        String urlGameInfo =baseUrl + "game/" + UID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGameInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(response,response);
                        loadCircle.setVisibility(View.GONE);


                        try {
                                JSONObject responseObj = new JSONObject(response);
                                if (responseObj.length() == 0)
                                    gameinfo.setText("You don't have an active game, start a new one and get hunting!");
                                else {
                                    gameinfo.setText(response);

                                    Intent i = new Intent(StartGameActivity.this, MainActivity.class);
                                    i.putExtra("gameId", UID);
                                    i.putExtra("userDataPackage", usr);
                                    startActivity(i);
                                }
                        }catch (JSONException ex){
                            gameinfo.setText(ex.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadCircle.setVisibility(View.GONE);
                        String errorMessage = requestHelper.ParseError(error);
                        if (errorMessage.contains("does not"))
                            gameinfo.setText("You don't have an active game, start a new one and get hunting!");
                        Log.d("ErrCatcherStartGameFrag", errorMessage);
                    }
                });
        mRequestQueue.add(stringRequest);
    }

    private void ConfirmRemoveGame(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(StartGameActivity.this);
        builder1.setMessage("You have an unfinished game, do you wish to continue this game?");
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoadGame(UID);
                    }
                });

        builder1.setPositiveButton(
                "Start new game",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestHelper.delete(UID, StartGameActivity.this);
                        confirmed = true;
                        StartGame(UID, amtItems);
                    }
                });

        builder1.setNeutralButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}