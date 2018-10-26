package com.example.chpoetie.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chpoetie.navigation.dummy.DummyContent;
import com.example.chpoetie.navigation.dummy.DummyContent.DummyItem;

import java.util.List;

public class SuspectFragment extends Fragment {

   public SuspectFragment(){

    }

    String[] menuItems = {"John", "Paul", "Morgan", "Ibrahim", "Arno","Antoine","Jopperman"};

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
          if(position == 0){

          }
          else if(position == 1){

          }
          else if(position == 2){

          }
          else if(position == 3){

          }
          else if(position == 4){

          }
          else if(position == 5){

          }
          else if(position == 6){

          }
          else if(position == 7){

          }

         }
        });

       return view;
    }
}
