package com.example.arno.cluego;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Helpers.ErrorCatcher;
import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;


public class StartGameActivity extends Activity implements Serializable {
    TextView gameinfo,serverinfo,instructions, tvWelcomeMsg;
    Button startButton, continueBtn, testBtn;
    RequestQueue mRequestQueue;
    private StringRequest stringRequest;

    private String jsonResponse;
    Game gameFromDatabase = new Game();

    ErrorCatcher errorCatcher = new ErrorCatcher();
    User usr = new User();

    public int UID, amtItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_start_of_game);

        usr = (User)getIntent().getSerializableExtra("userDataPackage");

        UID = usr.getUserId();
        amtItems = 3;

        gameinfo = findViewById(R.id.txt_info);
        serverinfo = findViewById(R.id.txt_server_info);
        startButton = findViewById(R.id.btn_start);
        tvWelcomeMsg = findViewById(R.id.tv_welcome);
        instructions = findViewById(R.id.txt_view_instructions);
        final ProgressBar loadCircle = findViewById(R.id.progress_bar);
        continueBtn = findViewById(R.id.btn_continue);
        testBtn = findViewById(R.id.btn_test);

        tvWelcomeMsg.setText("Welcome back " + usr.getUsername() + "!");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameinfo.setText(" ");
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
        
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartGameActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void StartGame(final int UID, int amtItems){

        final ProgressBar loadCircle = findViewById(R.id.progress_bar);

        mRequestQueue = Volley.newRequestQueue(this);

        String urlGameInfo ="https://clugo.azurewebsites.net/api/game/create/" + UID + "/" + amtItems;

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
                        String errorMessage = errorCatcher.ParseError(error);
                        gameinfo.setText(errorMessage);

                        if (errorMessage.contains("already"))
                            ConfirmRemoveGame();
                    }
                });
        mRequestQueue.add(stringRequest);
    }

    private void LoadGame(final int UID){
        Intent i = new Intent(StartGameActivity.this, MainActivity.class);
        i.putExtra("userId", UID);
        i.putExtra("userDataPackage", usr);
        startActivity(i);

        final ProgressBar loadCircle = findViewById(R.id.progress_bar);
        //Start the queue
        //mRequestQueue.start();
        mRequestQueue = Volley.newRequestQueue(this);

        String urlGameInfo ="https://clugo.azurewebsites.net/api/game/brief/" + UID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGameInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(response,response);
                        loadCircle.setVisibility(View.GONE);

                        try {
                                JSONArray responseArray = new JSONArray(response);
                                if (responseArray.length() == 0)
                                    gameinfo.setText("You don't have an active game, start a new one and get hunting!");
                                else {
                                    gameinfo.setText(response);

                                    Intent i = new Intent(StartGameActivity.this, MainActivity.class);
                                    i.putExtra("userId", UID);
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
                        String errorMessage = errorCatcher.ParseError(error);
                        Log.d("ErrCatcherStartGameFrag", errorMessage);
                    }
                });
        mRequestQueue.add(stringRequest);
    }

    private void ConfirmRemoveGame(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(StartGameActivity.this);
        builder1.setMessage("You have an unfinished game, do you wish to continue this game?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoadGame(UID);
                    }
                });

        builder1.setNeutralButton(
                "Start new game",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        errorCatcher.delete(UID, StartGameActivity.this);
                        StartGame(UID, amtItems);
                    }
                });

        builder1.setNegativeButton(
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