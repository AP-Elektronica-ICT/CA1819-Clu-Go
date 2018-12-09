package com.example.arno.cluegologin;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GuessActivity extends AppCompatActivity {
    ArrayList<String>suspectList;
    RequestQueue mRequestQueue;
    StringRequest stringRequest;
    String suspectGuess;
    ListView listview;
    String murderer;
    private String url = "https://cluegotesting.conveyor.cloud/api/suspect";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        suspectList = new ArrayList<String>();
        mRequestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray suspectArray = new JSONArray(response);
                    for (int i = 0; i <= suspectArray.length(); i++)
                    {
                        JSONObject singleSuspect =new JSONObject(suspectArray.getString(i));
                        final String suspect = singleSuspect.getString("susName");
                        boolean isMurderer = singleSuspect.getBoolean("isMurderer");
                        if(isMurderer==true)
                            murderer = suspect;
                        suspectList.add(suspect);

                        if(suspectList.size()==3)
                            makeListViewAdapter();

                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("yoyoyo", "Error: " + error.toString());
            }
        });
        mRequestQueue.add(stringRequest);
        listview=(ListView)findViewById(R.id.ListView_guess);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                suspectGuess = (String)parent.getItemAtPosition(position);
                Log.d("suspectGuess", "onItemClick: " + suspectGuess);
                if(suspectGuess.equals(murderer)){
                    Toast.makeText(getApplicationContext(),"you have quessed correctly",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"you have guessed incorrectly",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    public void makeListViewAdapter(){
            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    suspectList

            );
            listview.setAdapter(listViewAdapter);
    }

}
