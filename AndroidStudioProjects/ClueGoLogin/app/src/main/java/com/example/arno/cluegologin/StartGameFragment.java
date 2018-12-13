package com.example.arno.cluegologin;

import android.content.Context;
import android.os.Bundle;
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

    String description = "A crime has been commited the king has been killed by Jopperman presumably";
    TextView gameinfo,serverinfo,instructions,longlat;
    Button startButton;
    RequestQueue mRequestQueue;
    private String jsonResponse;
    Game gameFromDatabase =new Game();



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
    
    private void GetGame(int GID){
        Log.e("GetGameStart", "GetGame: in de startgamefragment" );
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        String gameurl ="https://cluego.azurewebsites.net/api/game/game/"+ GID;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                gameurl, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("tag",response.toString());

                        try{

                            JSONObject game = response.getJSONObject(0);
                            //Save game across fragments in bundle.





                            gameFromDatabase.setGameId(game.getInt("gameId"));
                            gameFromDatabase.setGameWon(game.getBoolean("gameWon"));

                            jsonResponse ="";
                            jsonResponse += "gameId: " +gameFromDatabase.getGameId() +"\n";
                            jsonResponse += "gameWon: " + gameFromDatabase.getGameWon() +"\n";

                            JSONArray gameLocations = game.getJSONArray("gameLocations");

                                    for (int i = 0; i <gameLocations.length() ; i++) {
                                        JSONObject gameLocation = (JSONObject) gameLocations.get(i);

                                            // int gameIdGameLoc = gameLocation.getInt("gameId");
                                            int locId = gameLocation.getInt("locId");

                                            jsonResponse += "locId: "+ locId + "\n";

                                        JSONObject location =  gameLocation.getJSONObject("location");

                                            double latitude = location.getDouble("locLat");
                                            double longtitude = location.getDouble("locLong");
                                            String desciption = location.getString("locDescription");
                                            String name = location.getString("locName");

                                        Location locationFromDatabase = new Location();
                                        locationFromDatabase.setLocDescription(location.getString("locDescription"));
                                        locationFromDatabase.setLocName(location.getString("locName"));
                                        locationFromDatabase.setLocLat(location.getDouble("locLat"));
                                        locationFromDatabase.setLoclong(location.getDouble("locLong"));


                                        gameFromDatabase.setLocations(locationFromDatabase);
                                        jsonResponse += "name: "+locationFromDatabase.getLocName() +" lat: "  + locationFromDatabase.getLocLat() + " long: " +locationFromDatabase.getLoclong() +"\n";

                                    }


                        JSONArray gameSuspects = game.getJSONArray("gameSuspects");

                                    for (int i = 0; i <gameSuspects.length() ; i++) {
                                        JSONObject gamesuspect = (JSONObject) gameSuspects.get(i);

                                        //  int gameIdSuspect = gamesuspect.getInt("gameId");
                                        int susId = gamesuspect.getInt("susId");
                                        Boolean isMurderer = gamesuspect.getBoolean("isMurderer");

                                        JSONObject suspect = gamesuspect.getJSONObject("suspect");

                                        String suspectname = suspect.getString("susName");
                                        String susdescription = suspect.getString("susDescription");
                                        String susWeapon = suspect.getString("susWeapon");
                                        String susImgUrl = suspect.getString("susImgUrl");

                                        Suspect suspectFromDatabase = new Suspect();
                                        suspectFromDatabase.setMurderer(gamesuspect.getBoolean("isMurderer"));
                                        suspectFromDatabase.setSusDescription(suspect.getString("susDescription"));
                                        suspectFromDatabase.setSusWeapon(suspect.getString("susWeapon"));
                                        suspectFromDatabase.setSusImgUrl(suspect.getString("susImgUrl"));
                                        suspectFromDatabase.setSusName(suspect.getString("susName"));

                                        gameFromDatabase.setSuspects(suspectFromDatabase);

                                        jsonResponse += "name:  " + suspectname + " description: " + susdescription + " weapon: " + susWeapon + "\n";

                                    }

                        JSONArray gameClues = game.getJSONArray("gameClues");

                                    for (int i = 0; i <gameClues.length(); i++) {

                                            JSONObject gameClue = (JSONObject) gameClues.get(i);
                                            int clueId = gameClue.getInt("clueId");

                                            JSONObject clue = gameClue.getJSONObject("clue");
                                            String clueName = clue.getString("clueName");
                                            String clueDescription = clue.getString("clueDescription");
                                            String clueImgUrl = clue.getString("clueImgUrl");
                                            Boolean found = clue.getBoolean("found");

                                             Clue clueFromDatabase = new Clue();

                                             clueFromDatabase.setClueDescription(clue.getString("clueDescription"));
                                            clueFromDatabase.setClueId(clue.getInt("clueId"));
                                            clueFromDatabase.setClueImgUrl(clue.getString("clueImgUrl"));
                                            clueFromDatabase.setClueName(clue.getString("clueName"));
                                            clueFromDatabase.setFound(clue.getBoolean("found"));


                                            gameFromDatabase.setClues(clueFromDatabase);


                                            }

                            serverinfo.setText(jsonResponse);

                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            MapViewFragment fragmap = new MapViewFragment();

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("game",gameFromDatabase);
                            fragmap.setArguments(bundle);
                           // ft.replace(R.id.fragment_container,fragmap);
                            ft.addToBackStack(null);
                            ft.commit();

                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error",error.toString());
                    }
                }
        );
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonArrayRequest);

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

                //StartGame(2,randomInt);
                //ShowGameInfo(randomInt);
                instructions.setVisibility(View.VISIBLE);
                //GetLocations(3);
                //GetGame(3);

               // StartGame(2,randomInt);
                ShowGameInfo(randomInt);
                //instructions.setVisibility(View.VISIBLE);
                //GetLocations(3);


            }
        });


        return v;

    }

    public void updateGame(JSONObject newGame){

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
    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////


}