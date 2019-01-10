package com.example.arno.cluego;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Helpers.RequestHelper;
import com.example.arno.cluego.Helpers.SuccessCallBack;
import com.example.arno.cluego.Objects.Clue;
import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.ItemAdapter;
import com.example.arno.cluego.Objects.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class InventoryFragment extends Fragment {
    Clue clue = new Clue();
    List<Clue> clueList = new ArrayList<>();
    String baseUrl;


    TextView tvTest;
    int gameId;
    public InventoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_inventory, container, false);
        gameId = getActivity().getIntent().getIntExtra("gameId", 0);
        tvTest = v.findViewById(R.id.tv_test);
        baseUrl = getResources().getString(R.string.baseUrl);
        getClues(v);

        return v;
    }

    public void getClues(final View v){
        clueList.clear();
        String url = baseUrl + "game/" +gameId+"/found";
        Log.d("gameidiv", "getClues: " + gameId);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("invFrag", "onResponse: " + response.toString());
                        try{
                            if (response.length() == 0)
                                Toast.makeText(getContext(), "No clues found yet", Toast.LENGTH_SHORT).show();
                            else{
                                for(int i=0;i<response.length();i++){
                                    JSONArray _response = response.getJSONArray(i);
                                    JSONObject _clue = _response.getJSONObject(0);

                                    clue.setClueId(_clue.getInt("clueId"));
                                    clue.setClueDescription(_clue.getString("clueDescription"));
                                    clue.setClueImgUrl(_clue.getString("clueImgUrl"));
                                    clue.setClueName(_clue.getString("clueName"));

                                    clueList.add(clue);

                                    tvTest.append(clue.getClueName() + "\n");
                                }
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getContext(), "Er was eens een error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }
}

