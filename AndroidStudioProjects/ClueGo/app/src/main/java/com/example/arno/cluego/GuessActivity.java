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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.Suspect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GuessActivity extends AppCompatActivity {
    ArrayList<String>suspectList;
    RequestQueue mRequestQueue;
    StringRequest stringRequest;
    String suspectGuess;
    ListView listview;
    String murderer;
    TextView tvTest;
    int userId;

    Game game = new Game();
    final ArrayList<String> suspectNames = new ArrayList<String>();

    private String url = "https://clugo.azurewebsites.net/api/suspect";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        suspectList = new ArrayList<String>();
        mRequestQueue = Volley.newRequestQueue(this);

        tvTest = findViewById(R.id.tv_test);
        listview = findViewById(R.id.ListView_guess);

        userId = getIntent().getIntExtra("userId", 0);
        game = (Game)getIntent().getSerializableExtra("gameData");

        tvTest.setText(game.getMurderer());

        try{
            final List<Suspect> suspects = game.getSuspects();
            for (int i = 0; i <suspects.size() ; i++) {
                Suspect suspect = suspects.get(i);
                suspectNames.add(suspect.getSusName());
            }
        }
        catch (NullPointerException ex)
        {
            Toast.makeText(this, "List not loaded.", Toast.LENGTH_SHORT).show();;
        }

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
                if(suspectGuess.equals(game.getMurderer())){;
                    Intent i = new Intent(GuessActivity.this, EndActivity.class);
                    i.putExtra("userId", userId);
                    startActivity(i);
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
