package com.example.arno.cluegologin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class EndActivity extends AppCompatActivity {
    Button btnEnd, btnDev;
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private String url= "https://clugo.azurewebsites.net/api/user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        btnEnd = findViewById(R.id.btnEnd);

        btnEnd.setOnClickListener(EndGame);
    }

    private View.OnClickListener EndGame = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            mRequestQueue = Volley.newRequestQueue(EndActivity.this);
            String UID = getIntent().getExtras().getString("UIDcurrent");
            String url= "https://clugo.azurewebsites.net/api/game/delete/"+ UID;

            stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("EndActivity", response);
                    Toast.makeText(EndActivity.this, response, Toast.LENGTH_SHORT).show();
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

