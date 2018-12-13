package com.example.arno.cluegologin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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
import com.example.arno.cluegologin.Objects.Clue;
import com.example.arno.cluegologin.Objects.Game;
import com.example.arno.cluegologin.Objects.Location;
import com.example.arno.cluegologin.Objects.Suspect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements MapViewFragment.MapFragmentListener,StartGameFragment.StartGameFragmentListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    switchToGame();

                    return true;
                case R.id.navigation_suspects:

                    switchToSuspect();
                    return true;
                case  R.id.navigation_inventory:
                    switchToInventory();
                    return true;
                case R.id.navigation_stats:
                    switchToStats();
                    return true;
            }
            return false;
        }
    };

    private MapViewFragment mapViewFragment = new MapViewFragment();
    private SuspectFragment suspectFragment= new SuspectFragment();
    private InventoryFragment inventoryFragment= new InventoryFragment();
    private StatsFragment statsFragment = new StatsFragment();
    private StartGameFragment startGameFragment;
    private String jsonResponse;
    RequestQueue mRequestQueue;

    Game gameFromDatabase =new Game();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        startOfGame();
    }

    @Override
    public void onInputGameSent(JSONObject game) {

        mapViewFragment.updateGame(game);
    }

    @Override
    public void onInputMapSent(JSONObject game) {

    }
    public void GetGame(int GID, final Fragment destinationFragment){

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

                            //serverinfo.setText(jsonResponse);

                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            Fragment fragmap = destinationFragment;

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("game",gameFromDatabase);
                            fragmap.setArguments(bundle);
                            ft.replace(R.id.fragment_container,fragmap);
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

    public void startOfGame() {

         FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new StartGameFragment()).addToBackStack(null).commit();


    }
    public void switchToGame() {
        GetGame(3,mapViewFragment);
      //  FragmentManager manager = getSupportFragmentManager();
       // manager.beginTransaction().replace(R.id.fragment_container, new MapViewFragment()).addToBackStack(null).commit();

    }

    public void switchToSuspect(){
        GetGame(3,suspectFragment);
       // FragmentManager manager = getSupportFragmentManager();
       // manager.beginTransaction().replace(R.id.fragment_container, new SuspectFragment()).addToBackStack(null).commit();
    }

    public void switchToInventory(){
        GetGame(3,inventoryFragment);
      //  FragmentManager manager = getSupportFragmentManager();
      //  manager.beginTransaction().replace(R.id.fragment_container, new InventoryFragment()).addToBackStack(null).commit();
    }

    public void switchToStats(){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new StatsFragment()).addToBackStack(null).commit();
    }

    public void startPuzzle(View view) {
        Intent intent = new Intent(this, PuzzleActivity.class );
        startActivity(intent);
    }



}
