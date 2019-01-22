package com.example.arno.cluego;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toolbar;

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
import com.example.arno.cluego.Objects.Clue;
import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.GameLocation;
import com.example.arno.cluego.Objects.Suspect;
import com.example.arno.cluego.Objects.User;
import com.github.amlcurran.showcaseview.ShowcaseView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MapViewFragment.MapFragmentListener, Serializable{
    private MapViewFragment mapViewFragment = new MapViewFragment();
    private SuspectFragment suspectFragment= new SuspectFragment();
    private InventoryFragment inventoryFragment= new InventoryFragment();
    private StatsFragment statsFragment = new StatsFragment();
    private StartGameActivity startGameActivity;
    private String jsonResponse;
    private boolean hasRequestsed;
    private int gameId, test;
    boolean showTutorial;

    BottomNavigationView navigation;

    FragmentManager manager = getSupportFragmentManager();

    String baseUrl;

    ArrayList<String> foundClueList;
    ArrayList<String> foundClueImageList;
    List<Clue> clueList;

    RequestQueue mRequestQueue;
    Bundle bundle;

    User usr = new User();
    Game gameFromDatabase = new Game();
    Game gameFromDatabase1 = new Game();

    ShowcaseView showcaseView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseUrl = getResources().getString(R.string.baseUrl);
        gameId = getIntent().getIntExtra("gameId", 0);
        //usr = (User)getIntent().getSerializableExtra("userDataPackage");
        test = getIntent().getIntExtra("setClue", 0);
        //gameFromDatabase1 = (Game)getIntent().getSerializableExtra("gameData");
        if (test > 0)
        {
            gameFromDatabase = gameFromDatabase1;
            foundClueList = new ArrayList<String>();
            foundClueImageList = new ArrayList<String>();
            clueList = gameFromDatabase.getClues();

            Clue clue = clueList.get(test);
            clue.setFound(true);
        }
        else
            GetGame(gameId);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setSelectedItemId(R.id.navigation_suspects);

        manager.beginTransaction().replace(R.id.fragment_container, new SuspectFragment()).addToBackStack(null).commit();



    }


    @Override
    public void onInputMapSent(JSONObject game) {
        mapViewFragment.updateGame(game);
    }
    public void GetGame(int GID) {

        Log.e("GetGameMain", "GetGame: in de mainact" );
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
            String gameurl = baseUrl + "game/full/" + GID;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    gameurl, null,

                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("tag", response.toString());

                            try {
                                JSONObject game = response.getJSONObject(0);
                                //Save game across fragments in bundle.

                                gameFromDatabase.setGameId(game.getInt("gameId"));
                                gameFromDatabase.setGameWon(game.getBoolean("gameWon"));

                                jsonResponse = "";
                                jsonResponse += "gameId: " + gameFromDatabase.getGameId() + "\n";
                                jsonResponse += "gameWon: " + gameFromDatabase.getGameWon() + "\n";

                                JSONArray gameLocations = game.getJSONArray("gameLocations");

                                for (int i = 0; i < gameLocations.length(); i++) {
                                    JSONObject gameLocation = (JSONObject) gameLocations.get(i);

                                    // int gameIdGameLoc = gameLocation.getInt("gameId");
                                    int locId = gameLocation.getInt("locId");

                                    jsonResponse += "locId: " + locId + "\n";

                                    JSONObject location = gameLocation.getJSONObject("gameLocation");

                                    double latitude = location.getDouble("locLat");
                                    double longtitude = location.getDouble("locLong");
                                    String desciption = location.getString("locDescription");
                                    String name = location.getString("locName");

                                    GameLocation gameLocationFromDatabase = new GameLocation();
                                    gameLocationFromDatabase.setLocDescription(location.getString("locDescription"));
                                    gameLocationFromDatabase.setLocName(location.getString("locName"));
                                    gameLocationFromDatabase.setLocLat(location.getDouble("locLat"));
                                    gameLocationFromDatabase.setLoclong(location.getDouble("locLong"));


                                    gameFromDatabase.setGameLocations(gameLocationFromDatabase);
                                    jsonResponse += "name: " + gameLocationFromDatabase.getLocName() + " lat: " + gameLocationFromDatabase.getLocLat() + " long: " + gameLocationFromDatabase.getLoclong() + "\n";

                                }


                                JSONArray gameSuspects = game.getJSONArray("gameSuspects");

                                for (int i = 0; i < gameSuspects.length(); i++) {
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
                                    suspectFromDatabase.setSusId(gamesuspect.getInt("susId"));
                                    suspectFromDatabase.setSusDescription(suspect.getString("susDescription"));
                                    suspectFromDatabase.setSusWeapon(suspect.getString("susWeapon"));
                                    suspectFromDatabase.setSusImgUrl(suspect.getString("susImgUrl"));
                                    suspectFromDatabase.setSusName(suspect.getString("susName"));

                                    gameFromDatabase.setSuspects(suspectFromDatabase);

                                    jsonResponse += "name:  " + suspectname + " description: " + susdescription + " weapon: " + susWeapon + "\n";

                                }

                                JSONArray gameClues = game.getJSONArray("gameClues");


                                for (int i = 0; i < gameClues.length(); i++) {

                                    JSONObject gameClue = (JSONObject) gameClues.get(i);
                                    int clueId = gameClue.getInt("clueId");

                                    JSONObject clue = gameClue.getJSONObject("clue");
                                    String clueName = clue.getString("clueName");
                                    //String clueDescription = clue.getString("clueDescription");
                                    String clueImgUrl = clue.getString("clueImgUrl");
                                    Boolean found = clue.getBoolean("found");

                                    Clue clueFromDatabase = new Clue();

                                    //clueFromDatabase.setClueDescription(clue.getString("clueDescription"));
                                    clueFromDatabase.setClueId(clue.getInt("clueId"));
                                    clueFromDatabase.setClueImgUrl(clue.getString("clueImgUrl"));
                                    clueFromDatabase.setClueName(clue.getString("clueName"));
                                    clueFromDatabase.setFound(clue.getBoolean("found"));

                                    gameFromDatabase.setClues(clueFromDatabase);
                                }
                                bundle = new Bundle();
                                bundle.putSerializable("gameData", gameFromDatabase);
                                bundle.putSerializable("userDataPackage",usr);
                                Log.e("bundleOpvragen", "onResponse: " + bundle);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.toString());

                        }
                    }
            );
            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            mRequestQueue.add(jsonArrayRequest);
    }

    public void switchToGame() {
        manager.beginTransaction().replace(R.id.fragment_container, new MapViewFragment()).addToBackStack(null).commit();
    }

    public void switchToSuspect(){
        manager.beginTransaction().replace(R.id.fragment_container, new SuspectFragment()).addToBackStack(null).commit();
    }

    public void switchToInventory(){
        manager.beginTransaction().replace(R.id.fragment_container, new InventoryFragment()).addToBackStack(null).commit();
    }

    public void switchToStats(){
        manager.beginTransaction().replace(R.id.fragment_container, new StatsFragment()).addToBackStack(null).commit();
    }



}
