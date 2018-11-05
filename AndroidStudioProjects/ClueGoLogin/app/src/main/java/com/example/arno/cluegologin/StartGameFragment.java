package com.example.arno.cluegologin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class StartGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public StartGameFragment() {
        // Required empty public constructor
    }

    String description = "A crime has been commited the king has been killed by Jopperman presumably";
    // TODO: Rename and change types and number of parameters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_start_of_game, container, false);
        /*TextView start_text = (TextView)v.findViewById(R.id.start);
        start_text.setText(description);*/

        return v;

    }

}
