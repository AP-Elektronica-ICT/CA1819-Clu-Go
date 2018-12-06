package com.example.arno.cluegologin;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.List;

public class SuspectFragment extends Fragment {

 public SuspectFragment(){

 }

 public void GoingInDetail(String detail, String name) {
  FragmentManager manager = getFragmentManager();
  DetailFragment newDetail = new DetailFragment();
  Bundle args = new Bundle();
  args.putString("detail", detail);
  args.putString("name",name);
  newDetail.setArguments(args);
  manager.beginTransaction().replace(R.id.fragment_container, newDetail).addToBackStack(null).commit();
 }

 String[] menuItems = {"John", "Paul", "Morgan", "Ibrahim", "Arno","Antoine","Jopperman","Sandman"};
 String [] suspectDetail = {"very long dark hair pink eyes","very good looking boi","very deadly boi","very fat boi","very smeky","very dangerous","deadly to the touch","non cooperating psychopath"};

 @Override
 public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

  View view = inflater.inflate(R.layout.suspect_list, container, false);

  ListView listView = (ListView) view.findViewById(R.id.suspect_list_view);

  ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
          getActivity(),
          android.R.layout.simple_list_item_1,
          menuItems

  );

  listView.setAdapter(listViewAdapter);
  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    GoingInDetail(suspectDetail[position], menuItems[position]);

   }
  });

  return view;
 }
}
