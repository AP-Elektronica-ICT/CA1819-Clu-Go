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

import java.util.zip.Inflater;

public class DetailFragment extends Fragment {

    public DetailFragment() {
        // Required empty public constructor
    }
    TextView suspect_detail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        suspect_detail = (TextView)v.findViewById(R.id.suspect_detail);
        return v;
    }

    public void SetDescription(String Description){
        suspect_detail.setText(Description);
    }


}
