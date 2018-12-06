package com.example.arno.cluegologin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class InventoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public InventoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inventory, container, false);
        final GridView inventory = (GridView) v.findViewById(R.id.inventory);
        final ImageView selecteditem = (ImageView) v.findViewById(R.id.item_picture);
        final TextView selecteditemdes = (TextView) v.findViewById(R.id.item_description);
            /*try {
                URL url = new URL("https://asp20181130123423.azurewebsites.net/api/Suspects");
                HttpsURLConnection myConnection =
                        (HttpsURLConnection) url.openConnection();
                InputStream responseBody = myConnection.getInputStream();
                InputStreamReader responseBodyReader =
                        new InputStreamReader(responseBody, "UTF-8");
                JsonReader jsonReader = new JsonReader(responseBodyReader);
                String nice = jsonReader.toString();
                selecteditemdes.setText(nice);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/



        final int[] InventoryItems = {
                R.drawable.hat,
                R.drawable.knife,
                R.drawable.mask,
                R.drawable.pipe,
                R.drawable.whip
        };
        final String[] itemdes = {
                "A Hat",
                "A Knife",
                "A Mask",
                "C'est ne pas un pipe",
                "A Whip"
        };
        inventory.setAdapter(new ItemAdapter(getActivity(),InventoryItems));
        inventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                selecteditem.setImageResource(InventoryItems[position]);
                selecteditemdes.setText(itemdes[position]);

            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event

}

