package com.example.arno.cluegologin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.arno.cluegologin.Objects.Game;
import com.example.arno.cluegologin.Objects.Suspect;

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





  /*final ArrayList<String> Suspect_Names = new ArrayList<String>();

  final ArrayList<String> Suspect_Descriptions = new ArrayList<String>();

  final ArrayList<String> Suspect_Images = new ArrayList<String>();

  String url = "https://citygame.azurewebsites.net/api/suspect";

  final ListView listView = (ListView) view.findViewById(R.id.suspect_list_view);
     RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
     StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
             Log.e("responseJson", "onResponse: " + response );
             try {
                 JSONArray Suspects_Array = new JSONArray(response);
                 for (int i = 0; i <= Suspects_Array.length(); i++)
                 {
                     JSONObject Suspect = new JSONObject(Suspects_Array.getString(i));
                     final String Name = Suspect.getString("susName");
                     final String Description = Suspect.getString("susDescription");
                     final String ImageUrl = Suspect.getString("susImgUrl");
                     Log.e("Suspect"+i, "onResponse: " + Suspect.getString("susName"));

                     Suspect_Names.add(Name);
                     Suspect_Descriptions.add(Description);
                     Suspect_Images.add(ImageUrl);
                     ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                             getActivity(),
                             android.R.layout.simple_list_item_1,
                             Suspect_Names

                     );

                     listView.setAdapter(listViewAdapter);
                     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                         @Override
                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                             GoingInDetail(Suspect_Descriptions.get(position), Suspect_Names.get(position) ,Suspect_Images.get(position) );

                         }
                     });
                     //listView.setLongClickable(true);
                     /*listView.setOnLongClickListener(new AdapterView.OnItemLongClickListener(){
                         @Override
                         public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                             view.setBackgroundColor(Color.GRAY);
                             return true;
                         }
                     });
                 }

             } catch (JSONException e){
                 e.printStackTrace();
             }
         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
             Log.i("Error Response", "Error: " + error.toString());
         }
     });
     mRequestQueue.add(stringRequest);
*/

  return view;
 }

}
