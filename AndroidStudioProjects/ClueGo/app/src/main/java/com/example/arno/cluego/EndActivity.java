package com.example.arno.cluego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Objects.User;

import java.io.Serializable;

public class EndActivity extends AppCompatActivity implements Serializable {
    Button btnEnd, btnDev;
    TextView tvTest;
    User usr = new User();
    String baseUrl;


    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private String url= baseUrl + "user";
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        baseUrl = getResources().getString(R.string.baseUrl);
        btnEnd = findViewById(R.id.btnEnd);
        tvTest = findViewById(R.id.tv_test2);

        userId = getIntent().getIntExtra("gameId", 0);

        tvTest.setText(String.valueOf(userId));

        btnEnd.setOnClickListener(EndGame);
    }

    private View.OnClickListener EndGame = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            mRequestQueue = Volley.newRequestQueue(EndActivity.this);
            String url= baseUrl + "game/" + userId;

            stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("EndActivity", response);
                    Toast.makeText(EndActivity.this, response, Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(EndActivity.this, StartGameActivity.class);
                    i.putExtra("gameId",userId);
                    i.putExtra("userDataPackage", usr);
                    startActivity(i);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error.response", error.toString());
                    Toast.makeText(EndActivity.this, "Error ending the game", Toast.LENGTH_SHORT).show();
                }
            });
            mRequestQueue.add(stringRequest);
        }


    };
}

