package com.example.arno.cluego;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.arno.cluego.Objects.Clue;
import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.Location;
import com.example.arno.cluego.Objects.Suspect;
import com.example.arno.cluego.Objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements MapViewFragment.MapFragmentListener, Serializable {
    private MapViewFragment mapViewFragment = new MapViewFragment();
    private SuspectFragment suspectFragment= new SuspectFragment();
    private InventoryFragment inventoryFragment= new InventoryFragment();
    private StatsFragment statsFragment = new StatsFragment();
    private StartGameFragment startGameFragment;
    private String jsonResponse;
    private boolean hasRequestsed;
    private int gameId;

    RequestQueue mRequestQueue;
    Bundle bundle;

    User usr = new User();
    Game gameFromDatabase = new Game();

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
                case R.id.test:
                    Intent intent = new Intent(MainActivity.this,GuessActivity.class);
                    intent.putExtra("gameData", gameFromDatabase);
                    intent.putExtra("userId", gameId);
                    intent.putExtra("userDataPackage", usr);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameId = getIntent().getIntExtra("userId", 0);
        usr = (User)getIntent().getSerializableExtra("userDataPackage");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        GetGame(gameId);
        //switchToSuspect();
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
            String gameurl = "https://clugo.azurewebsites.net/api/game/" + GID;

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

                                    JSONObject location = gameLocation.getJSONObject("location");

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
                                    jsonResponse += "name: " + locationFromDatabase.getLocName() + " lat: " + locationFromDatabase.getLocLat() + " long: " + locationFromDatabase.getLoclong() + "\n";

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
                                    //JSONObject singleClue = gameClue.getJSONObject("clue");
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
                                bundle.putSerializable("game", gameFromDatabase);
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

public void sendBunble(Fragment _fragmap, Bundle _bundle){
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    Fragment fragmap = _fragmap;

    fragmap.setArguments(_bundle);

    ft.replace(R.id.fragment_container, fragmap);
    ft.addToBackStack(null);
    ft.commit();
    }

    public void switchToGame() {
        sendBunble(mapViewFragment,bundle);
    }

    public void switchToSuspect(){
        sendBunble(suspectFragment,bundle);
    }

    public void switchToInventory(){
       sendBunble(inventoryFragment, bundle);
    }

    public void switchToStats(){
        sendBunble(statsFragment,bundle);
    }

    public void startPuzzle(View view) {
        Intent intent = new Intent(this, PuzzleActivity.class );
        startActivity(intent);
    }
}
