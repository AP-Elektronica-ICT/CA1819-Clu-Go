package com.example.arno.cluego;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Helpers.SuccessCallBack;
import com.example.arno.cluego.Objects.Suspect;
import com.example.arno.cluego.Helpers.SuspectAdapter;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.ShowcaseViewApi;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.PointTarget;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class SuspectFragment extends Fragment {
    private int gameId;
    private String baseUrl;

    List<Suspect> suspects = new ArrayList<>();
    Suspect suspect;
    ArrayList<String> suspectList;
    final ArrayList<String> suspectNames = new ArrayList<>();
    boolean showTutorial;
    GridView gridview;

    ShowcaseView showcaseView;

 public SuspectFragment(){
    }

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

     final View view = inflater.inflate(R.layout.suspect_list, container, false);
        gridview = view.findViewById(R.id.gridview);

        baseUrl = getResources().getString(R.string.baseUrl);
        gameId = getActivity().getIntent().getIntExtra("gameId", 0);
        suspects.clear();

        GetSuspects(gameId, new SuccessCallBack() {
            @Override
            public void onSuccess() {
                int amtSus = suspects.size();

                for (int i = 0; i < amtSus; i++) {
                    Suspect _suspect;
                    _suspect = suspects.get(i);
                    String name = _suspect.getSusName();
                    suspectNames.add(name);
                }

                gridview.setAdapter(new SuspectAdapter(getActivity(), suspects, amtSus));


                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        showcaseView.hide();

                        GoingInDetail(suspects.get(position).getSusDescription(), suspects.get(position).getSusName(), suspects.get(position).getSusImgUrl());
                    }
                });
            }
        });

        showcaseView = new ShowcaseView.Builder(getActivity())
                .setTarget( new PointTarget(250, 380))
                .setContentTitle("Suspect list")
                .setContentText("After a lot of work, the police have managed to shorten the list of possible murderers to these suspects. Click on one of them to get more information about that suspect.")
                .hideOnTouchOutside()
                .singleShot(99)
                .build();
        showcaseView.hideButton();

        return view;
    }

    public void GoingInDetail(String detail, String name, String image) {
        FragmentManager manager = getFragmentManager();
        DetailFragment newDetail = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("detail", detail);
        args.putString("name",name);
        args.putString("image",image);
        newDetail.setArguments(args);
        manager.beginTransaction().replace(R.id.fragment_container, newDetail).addToBackStack(null).commit();
    }



   /* public ShowcaseView getShowcaseView() {
        return showcaseView;
    }*/

    public void GetSuspects(int id, final SuccessCallBack callBack){
        String url = baseUrl + "game/" + id +"/suspect";
        Log.d("TAG", "GetSuspects: " + url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            if (response.length() == 0)
                                Toast.makeText(getContext(), "No suspects found", Toast.LENGTH_SHORT).show();
                            else{
                                for(int i=0;i<response.length();i++){
                                    suspect = new Suspect();
                                    JSONObject _response = response.getJSONObject(i);
                                    JSONObject _suspect = _response.getJSONObject("suspect");

                                    suspect.setSusId(_suspect.getInt("susId"));
                                    suspect.setSusName(_suspect.getString("susName"));
                                    suspect.setSusDescription(_suspect.getString("susDescription"));
                                    suspect.setSusImgUrl(_suspect.getString("susImgUrl"));

                                    suspects.add(suspect);

                                    callBack.onSuccess();
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