package com.example.arno.cluegologin;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class InventoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    RequestQueue mRequestQueue;
    StringRequest stringRequest;
    ArrayList<String> foundClueList;
    ListView listView;

    private String url = "https://clugo.azurewebsites.net/api/clue";
    public InventoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_guess, container, false);
        foundClueList = new ArrayList<String>();
        mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("responseJson", "onResponse: " + response );
                try {
                    JSONArray clueArray = new JSONArray(response);
                    for (int i = 0; i <= clueArray.length(); i++)
                    {
                        JSONObject singleClue =new JSONObject(clueArray.getString(i));
                        final String clue = singleClue.getString("clueName");
                        boolean foundClue = singleClue.getBoolean("found");
                        Log.e("SingleClue", "onResponse: " + singleClue.toString());
                        Log.e("clueclue", "onResponse: " + clue );
                        if(foundClue==true)
                            foundClueList.add(clue);
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
        listView = (ListView)v.findViewById(R.id.ListView_guess);

        return v;
    }
    public void makeListViewAdapter(){
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                foundClueList

        );
        listView.setAdapter(listViewAdapter);
    }
    // TODO: Rename method, update argument and hook method into UI event

}

