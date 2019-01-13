package com.example.arno.cluego;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.User;

import org.json.JSONException;
import org.json.JSONObject;


public class StatsFragment extends Fragment {
    private static final String TAG = "StatsFragment";
    TextView tvDistance, tvGamesPlayed, tvCluesFound, tvTitle;
    ImageView ivTitle;
    View view;
    int userId;
    User usr = new User();
    String baseUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stats, container, false);
        tvCluesFound = view.findViewById(R.id.tv_CluesFound);
        tvDistance = view.findViewById(R.id.tv_Distance);
        tvGamesPlayed = view.findViewById(R.id.tv_GamesPlayed);
        tvTitle = view.findViewById(R.id.tv_Title);
        ivTitle = view.findViewById(R.id.iv_Title);
        baseUrl = getResources().getString(R.string.baseUrl);

            userId = getActivity().getIntent().getIntExtra("gameId", 0);
            GetUserData(userId);

            SetStats(usr);

        return view;
    }
    public void SetStats(User usr){
        tvGamesPlayed.setText(String.valueOf(usr.getGamesPlayed()));
        tvDistance.setText(String.valueOf(usr.getDistanceWalked() + " m"));
        tvCluesFound.setText(String.valueOf(usr.getCluesFound()));

        AutoAdjustImgView();
    }

    public void AutoAdjustImgView(){
        ViewTreeObserver vto = tvTitle.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                tvTitle.getViewTreeObserver().removeOnPreDrawListener(this);
                ivTitle.getLayoutParams().height = tvTitle.getMeasuredHeight();
                ivTitle.getLayoutParams().width = tvTitle.getMeasuredWidth();
                return true;
            }
        });
    }

    private void GetUserData(int userId) {

        RequestQueue mRequestQueue = Volley.newRequestQueue(getContext());
        String url= baseUrl + "user/" + userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LoginActivity", response);

                try{
                    JSONObject userObj = new JSONObject(response);

                    usr.setUserId(userObj.getInt("userId"));
                    usr.setUsername(userObj.getString("username"));
                    usr.setCluesFound(userObj.getInt("cluesFound"));
                    usr.setDistanceWalked(userObj.getInt("distanceWalked"));
                    usr.setGamesPlayed(userObj.getInt("gamesPlayed"));

                }catch(JSONException e)
                {
                    Log.d("JSonErr", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(stringRequest);
    }
}
