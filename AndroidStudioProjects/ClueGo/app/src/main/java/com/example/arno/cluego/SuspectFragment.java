package com.example.arno.cluego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.Suspect;

import java.util.ArrayList;
import java.util.List;

public class SuspectFragment extends Fragment {

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
     
  final View view = inflater.inflate(R.layout.suspect_list, container, false);
     final ListView listView = (ListView) view.findViewById(R.id.suspect_list_view);
     final ArrayList<String> Suspect_Names = new ArrayList<String>();

     Bundle bundle = getArguments();
     Game currentGame = (Game) bundle.getSerializable("game");

    List<Suspect> suspects = currentGame.getSuspects();


     for (int i = 0; i <suspects.size() ; i++) {

         Suspect suspect = suspects.get(i);
         String name = suspect.getSusName();
         String weapon = suspect.getSusWeapon();
         String description = suspect.getSusDescription();
         Suspect_Names.add(name);


     }
     ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
             getActivity(),
             android.R.layout.simple_list_item_1,
             Suspect_Names);
     listView.setAdapter(listViewAdapter);


  return view;
 }

}
