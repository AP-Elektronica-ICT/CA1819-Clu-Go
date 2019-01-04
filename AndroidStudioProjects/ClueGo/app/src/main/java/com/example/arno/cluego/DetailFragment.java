package com.example.arno.cluego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {
    Button btnBack;

    public DetailFragment() {


    }
    TextView suspect_detail, suspect_name;
    ImageView suspect_image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        btnBack = v.findViewById(R.id.btn_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        String name = getArguments().getString("name");
        String detail = getArguments().getString("detail");
        String image = getArguments().getString("image");

        suspect_name = (TextView)v.findViewById(R.id.suspect_name);
        suspect_detail = (TextView)v.findViewById(R.id.suspect_detail);
        suspect_image = (ImageView)v.findViewById(R.id.suspect_image);
        SetName(name);
        SetDescription(detail);
        Picasso.get().load(image).into(suspect_image);
        return v;
    }

    public void SetName(String Name){

        suspect_name.setText(Name);
    }

    public void SetDescription(String Description){

        suspect_detail.setText(Description);
    }
}
