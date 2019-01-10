package com.example.arno.cluego;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.Suspect;
import com.example.arno.cluego.Objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GuessActivity extends AppCompatActivity implements Serializable {
    final ArrayList<String> suspectNames = new ArrayList<String>();
    ArrayList<String>suspectList;
    RequestQueue mRequestQueue;
    StringRequest stringRequest;
    String suspectGuess;
    ListView listview;
    String murderer;
    TextView tvTest;
    int userId;
    List<Suspect> suspects = new ArrayList<>();
    Suspect suspect = new Suspect();
    int gameId;
    User usr = new User();
    Game game = new Game();
    String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        suspectList = new ArrayList<String>();
        mRequestQueue = Volley.newRequestQueue(this);

        tvTest = findViewById(R.id.tv_test);
        listview = findViewById(R.id.ListView_guess);
        baseUrl = getResources().getString(R.string.baseUrl);

        //usr = (User)getIntent().getSerializableExtra("userDataPackage");
        //userId = usr.getUserId();

        //game = (Game)getIntent().getSerializableExtra("gameData");
        gameId = getIntent().getIntExtra("gameId", 0);
        GetSuspects(gameId);



    }

    public void makeListViewAdapter(){
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                suspectList

        );
        listview.setAdapter(listViewAdapter);
    }

    public void setActivity(){
        tvTest.setText(murderer);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                suspectNames);
        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                suspectGuess = (String)parent.getItemAtPosition(position);
                Log.d("suspectGuess", "onItemClick: " + suspectGuess);
                if(suspectGuess.equals(murderer)){;
                    Intent i = new Intent(GuessActivity.this, EndActivity.class);
                    i.putExtra("gameId", gameId);
                    i.putExtra("userDataPackage", usr);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),"you have guessed incorrectly",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void GetSuspects(int id){
        String url = baseUrl + "game/" + id +"/suspect";
        Log.d("TAG", "GetSuspects: " + url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            if (response.length() == 0)
                                Toast.makeText(GuessActivity.this, "No suspects found", Toast.LENGTH_SHORT).show();
                            else{
                                for(int i=0;i<response.length();i++){

                                    JSONObject _response = response.getJSONObject(i);
                                    suspect.setMurderer(_response.getBoolean("isMurderer"));



                                    JSONObject _suspect = _response.getJSONObject("suspect");
                                    if(_response.getBoolean("isMurderer"))
                                        murderer = _suspect.getString("susName");

                                    suspect.setSusId(_suspect.getInt("susId"));
                                    suspect.setSusName(_suspect.getString("susName"));
                                    suspect.setSusDescription(_suspect.getString("susDescription"));
                                    suspect.setSusImgUrl(_suspect.getString("susImgUrl"));

                                    suspects.add(suspect);
                                    suspectNames.add(suspect.getSusName());
                                }
                                setActivity();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(GuessActivity.this, "Er was eens een error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}

