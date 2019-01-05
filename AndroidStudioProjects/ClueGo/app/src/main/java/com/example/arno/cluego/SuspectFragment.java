package com.example.arno.cluego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.Suspect;
import com.example.arno.cluego.Objects.SuspectAdapter;

import java.util.ArrayList;
import java.util.List;

public class SuspectFragment extends Fragment {
    Game currentGame = new Game();
 public SuspectFragment(){

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

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

     Bundle bundle = getArguments();
     if (bundle != null)
        currentGame = (Game) bundle.getSerializable("gameData");
     else {
         currentGame = (Game)getActivity().getIntent().getSerializableExtra("gameData");
     }


     final ArrayList<String> Suspect_Names = new ArrayList<String>();
     final List<Suspect> suspects = currentGame.getSuspects();
     int amtSus = suspects.size();


        Bundle bundle = getArguments();
        if (bundle != null)
            currentGame = (Game) bundle.getSerializable("gameData");
        else {
            currentGame = (Game)getActivity().getIntent().getSerializableExtra("gameData");
        }


        final ArrayList<String> Suspect_Names = new ArrayList<String>();
        final List<Suspect> suspects = currentGame.getSuspects();
        int amtSus = suspects.size();

        for (int i = 0; i < suspects.size(); i++) {

            Suspect suspect = suspects.get(i);
            String name = suspect.getSusName();
            Suspect_Names.add(name);
        }

        gridview.setAdapter(new SuspectAdapter(getContext(), suspects, amtSus));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GoingInDetail(suspects.get(position).getSusDescription(), suspects.get(position).getSusName(), suspects.get(position).getSusImgUrl());
            }
        });
        return view;
    }

}