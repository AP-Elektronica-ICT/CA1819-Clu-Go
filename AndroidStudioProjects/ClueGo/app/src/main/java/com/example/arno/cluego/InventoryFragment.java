package com.example.arno.cluego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Helpers.InventoryAdapter;
import com.example.arno.cluego.Helpers.SuccessCallBack;
import com.example.arno.cluego.Objects.Clue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class InventoryFragment extends Fragment implements InventoryAdapter.ItemClickListener {
    Clue clue = new Clue();
    List<Clue> clueList = new ArrayList<>();
    String baseUrl;
    InventoryAdapter adapter;


    TextView tvInfo;
    int gameId;
    public InventoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_inventory, container, false);
        gameId = getActivity().getIntent().getIntExtra("gameId", 0);
        tvInfo = v.findViewById(R.id.tv_info);
        baseUrl = getResources().getString(R.string.baseUrl);
        getClues(v, new SuccessCallBack() {
            @Override
            public void onSuccess() {
                RecyclerView recyclerView = v.findViewById(R.id.rvClues);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new InventoryAdapter(getContext(), clueList);
                adapter.setClickListener(InventoryFragment.this);
                recyclerView.setAdapter(adapter);

                adapter.setOnDataChangeListener(new InventoryAdapter.OnDataChangeListener(){
                    public void onDataChanged(int price){
                    }
                });
            }
        });

        return v;
    }

    public void getClues(final View v, final SuccessCallBack callBack){
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
                            if (response.length() == 0){
                                tvInfo.setVisibility(View.VISIBLE);
                                tvInfo.setText("You have not found any clues yet. Go to a marked location on the map and solve the puzzle to get started!");
                            }
                            else{
                                tvInfo.setVisibility(View.GONE);
                                for(int i=0;i<response.length();i++){
                                    clue = new Clue();
                                    JSONArray _response = response.getJSONArray(i);
                                    JSONObject _clue = _response.getJSONObject(0);

                                    clue.setClueId(_clue.getInt("clueId"));
                                    clue.setClueDescription(_clue.getString("clueDescription"));
                                    clue.setClueImgUrl(_clue.getString("clueImgUrl"));
                                    clue.setClueName(_clue.getString("clueName"));

                                    clueList.add(clue);
                                    Log.d("TAG", clueList.toString());
                                }
                                callBack.onSuccess();
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

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getClue(position).getClueName() + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

}

