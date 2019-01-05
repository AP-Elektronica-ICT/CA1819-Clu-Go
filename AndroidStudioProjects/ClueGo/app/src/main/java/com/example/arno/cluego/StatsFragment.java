package com.example.arno.cluego;

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

import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.User;


public class StatsFragment extends Fragment {
    private static final String TAG = "StatsFragment";
    TextView tvDistance, tvGamesPlayed, tvCluesFound, tvTitle;
    ImageView ivTitle;
    View view;

    User usr = new User();

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

        Bundle bundle = getArguments();
        if (bundle != null){
            usr = (User)bundle.getSerializable("userDataPackage");
        }else{
            usr = (User)getActivity().getIntent().getSerializableExtra("userDataPackage");
        }
        SetStats(usr);

        return view;
    }
    public void SetStats(User usr){
        tvGamesPlayed.setText(String.valueOf(usr.getGamesPlayed()));
        tvTitle.append(usr.getUsername() + "!");
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
}
