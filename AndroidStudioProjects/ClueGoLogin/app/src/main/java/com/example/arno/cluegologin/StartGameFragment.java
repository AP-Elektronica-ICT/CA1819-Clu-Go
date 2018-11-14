package com.example.arno.cluegologin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.Random;

import static com.facebook.FacebookSdk.getCacheDir;


public class StartGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public StartGameFragment() {
        // Required empty public constructor
    }

    String description = "A crime has been commited the king has been killed by Jopperman presumably";
    TextView gameinfo;
    Button startButton;
    RequestQueue mRequestQueue;



    // TODO: Rename and change types and number of parameters

    private void CreateGame(int GID){



        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);

        /* Start the queue */
        mRequestQueue.start();

        String url ="http://192.168.0.225:45457/api/game/gameinfocase/"+GID;

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(response,response.toString());
                        gameinfo.setText(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error.reponse",error.toString());
                        gameinfo.setText("wrong");
                        // Handle error
                    }
                });

// Add the request to the RequestQueue.
                 mRequestQueue.add(stringRequest);
    }


    final int random = new Random().nextInt(6);
    int randomInt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_start_of_game, container, false);
        /*TextView start_text = (TextView)v.findViewById(R.id.start);
        start_text.setText(description);*/

        gameinfo =(TextView)v.findViewById(R.id.txt_info);
        startButton =(Button)v.findViewById(R.id.btn_start);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomInt = new Random().nextInt(6);
                CreateGame(randomInt);
            }
        });


        return v;

    }

    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////


}