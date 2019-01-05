package com.example.arno.cluego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    RequestQueue mRequestQueue;
    StringRequest stringRequest;
    ArrayList<String> foundClueList;
    ArrayList<String> foundClueImageList;
    List<Clue> clueList;
    ListView listView;

    Game game = new Game();

    //private String url = "https://clugo.azurewebsites.net/api/clue";
    public InventoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inventory, container, false);

        Bundle bundle = getArguments();
        if (bundle != null){
            game = (Game) bundle.getSerializable("gameData");
        }
        else {
            game = (Game)getActivity().getIntent().getSerializableExtra("gameData");
        }
        final GridView inventory = (GridView) v.findViewById(R.id.inventory);
        final ImageView selecteditem = (ImageView) v.findViewById(R.id.item_picture);
        final TextView selecteditemdes = (TextView) v.findViewById(R.id.item_description);
        foundClueList = new ArrayList<String>();
        foundClueImageList = new ArrayList<String>();
        clueList = game.getClues();

        for (int i = 0; i < clueList.size(); i++) {
            if(clueList.get(i).isFound())
                foundClueList.add(clueList.get(i).getClueName());
        }
        TextView tvTest = v.findViewById(R.id.item_description);
        tvTest.setText(String.valueOf(foundClueList.size()) + "clues found, images komen later nog wel.");




        /*mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("responseJson", "onResponse: " + response );
                try {
                    JSONArray clueArray = new JSONArray(response);
                    for (int i = 0; i <= clueArray.length(); i++) {
                        JSONObject singleClue = new JSONObject(clueArray.getString(i));
                        final String clue = singleClue.getString("clueName");
                        boolean foundClue = singleClue.getBoolean("found");
                        final String clueimage = singleClue.getString("clueImgUrl");
                        Log.e("SingleClue", "onResponse: " + singleClue.toString());
                        Log.e("clueclue", "onResponse: " + clue);
                        if (foundClue == true) {
                            foundClueList.add(clue);
                            foundClueImageList.add(clueimage);
                            inventory.setAdapter(new ItemAdapter(getActivity(),foundClueImageList));
                            inventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View v,
                                                        int position, long id) {
                                    Picasso.get().load(foundClueImageList.get(position)).into(selecteditem);
                                    selecteditemdes.setText(foundClueList.get(position));
                                }});
                        }
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("yoyoyo", "Error: " + error.toString());
            }
        });
        mRequestQueue.add(stringRequest);
        listView = (ListView)v.findViewById(R.id.ListView_guess);*/



        return v;
    }

    //public void makeListViewAdapter(){


    // TODO: Rename method, update argument and hook method into UI event

}

