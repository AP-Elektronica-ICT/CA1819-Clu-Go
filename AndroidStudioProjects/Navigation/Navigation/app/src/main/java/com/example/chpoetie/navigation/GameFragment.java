package com.example.chpoetie.navigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class GameFragment extends Fragment {
        View view;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
// Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_game, container, false);
// get the reference of Button

            return view;
    }
}
