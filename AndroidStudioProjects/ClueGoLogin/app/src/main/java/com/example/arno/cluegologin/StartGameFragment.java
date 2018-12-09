package com.example.arno.cluegologin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static com.facebook.FacebookSdk.getCacheDir;


public class StartGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public StartGameFragment() {
        // Required empty public constructor
    }

    String description = "A crime has been commited the king has been killed by Jopperman presumably";
    TextView gameinfo,serverinfo,instructions,longlat;
    Button startButton;
    RequestQueue mRequestQueue;



    // TODO: Rename and change types and number of parameters

    private void ShowGameInfo(int GID){

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);

        /* Start the queue */
        mRequestQueue.start();

        String urlGameInfo ="https://cluego.azurewebsites.net/api/game/game/2";

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGameInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(response,response.toString());
                        gameinfo.setText(response);
                        ProgressBar loadCircle = getView().findViewById(R.id.progress_bar);
                        loadCircle.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error.reponse",error.toString());
                        gameinfo.setText("Server is not responding try again later");
                        // Handle error
                    }
                });

// Add the request to the RequestQueue.
                 mRequestQueue.add(stringRequest);



    }
    private void StartGame(int UID,int CID){
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);

        /* Start the queue */
        mRequestQueue.start();

        String urlGameInfo ="http://172.16.222.154:45455/api/game/newgame/"+UID+"/"+CID;

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGameInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(response,response.toString());
                        serverinfo.setText(response);
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error.reponse",error.toString());

                        // Handle error
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);


    }
    private void GetLocations(int UID){
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);

        mRequestQueue.start();
        String locationurl ="http://172.16.222.154:45455/api/game/getlocation/"+ UID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                locationurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            JSONArray array = response.getJSONArray("locations");

                            for (int i =0;i<array.length();i++){
                                JSONObject location = array.getJSONObject(i);
                                String latitude = location.getString("loclat");
                                String longtitude = location.getString("locLong");
                                String Description = location.getString("locDescription");

                                longlat.setText(latitude+ " " + longtitude + "  " + Description);
                            }

                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

    }



    final int random = new Random().nextInt(5);
    int randomInt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_start_of_game, container, false);
        /*TextView start_text = (TextView)v.findViewById(R.id.start);
        start_text.setText(description);*/

        gameinfo =(TextView)v.findViewById(R.id.txt_info);
        serverinfo = (TextView)v.findViewById(R.id.txt_server_info);
        startButton =(Button)v.findViewById(R.id.btn_start);
        instructions =(TextView)v.findViewById(R.id.txt_view_instructions);
        //longlat = (TextView)v.findViewById(R.id.txt_long_lat);
        final ProgressBar loadCircle = v.findViewById(R.id.progress_bar);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameinfo.setText(" ");
                loadCircle.setVisibility(View.VISIBLE);
                randomInt = new Random().nextInt(5);
                StartGame(2,randomInt);
                ShowGameInfo(randomInt);
                //instructions.setVisibility(View.VISIBLE);
                //GetLocations(3);

            }
        });


        return v;

    }

    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////


}