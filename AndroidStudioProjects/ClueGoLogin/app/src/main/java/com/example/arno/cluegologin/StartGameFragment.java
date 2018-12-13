package com.example.arno.cluegologin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.arno.cluegologin.Objects.Clue;
import com.example.arno.cluegologin.Objects.Game;
import com.example.arno.cluegologin.Objects.Location;
import com.example.arno.cluegologin.Objects.Suspect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static com.facebook.FacebookSdk.getCacheDir;


public class StartGameFragment extends Fragment {
    private StartGameFragmentListener listener;

    public interface StartGameFragmentListener{
        void onInputGameSent(JSONObject game);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public StartGameFragment() {
        // Required empty public constructor
    }

    TextView gameinfo,serverinfo,instructions;
    Button startButton, continueBtn;
    RequestQueue mRequestQueue;
    private String jsonResponse;
    Game gameFromDatabase = new Game();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_start_of_game, container, false);

        gameinfo = v.findViewById(R.id.txt_info);
        serverinfo = v.findViewById(R.id.txt_server_info);
        startButton = v.findViewById(R.id.btn_start);
        instructions = v.findViewById(R.id.txt_view_instructions);
        final ProgressBar loadCircle = v.findViewById(R.id.progress_bar);
        continueBtn = v.findViewById(R.id.btn_continue);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String UID = preferences.getString("UID","");
                gameinfo.setText(" ");
                loadCircle.setVisibility(View.VISIBLE);
                StartGame(UID);
                //LoadGame(UID);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String UID = preferences.getString("UID","");
                gameinfo.setText(" ");
                loadCircle.setVisibility(View.VISIBLE);
                LoadGame(UID);
            }
        });


        return v;
    }


    // TODO: Rename and change types and number of parameters

    private void StartGame(final String UID){
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        final ProgressBar loadCircle = getView().findViewById(R.id.progress_bar);

        /* Start the queue */
        mRequestQueue.start();

        String urlGameInfo ="https://cluego.azurewebsites.net/api/game/create/3/" + UID;

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGameInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(response,response.toString());
                        gameinfo.setText(response);

                        loadCircle.setVisibility(View.GONE);
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
        final ProgressBar loadCircle = getView().findViewById(R.id.progress_bar);

        /* Start the queue */
        mRequestQueue.start();

        String urlGameInfo ="https://cluego.azurewebsites.net/api/game/game/" + UID;

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGameInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(response,response.toString());
                        gameinfo.setText(response);

                        loadCircle.setVisibility(View.GONE);
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof StartGameFragmentListener){
            listener =(StartGameFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    +"mustimplement StartGameFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener =null;
    }
}