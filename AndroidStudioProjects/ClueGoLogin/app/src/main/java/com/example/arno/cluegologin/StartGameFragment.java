package com.example.arno.cluegologin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class StartGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public StartGameFragment() {
        // Required empty public constructor
    }

    String description = "A crime has been commited the king has been killed by Jopperman presumably";
    TextView gameinfo;
    Button startButton;
    // TODO: Rename and change types and number of parameters

    private void CreateGame(int GID){

        try {
            URL url = new URL("http://localhost:58638/api/game/gameinfo/" + GID);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            }
            else {

                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(in);
                String output;

                while ((output= br.readLine()) != null){
                    gameinfo.setText(output);
                }
                conn.disconnect();


            }
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_start_of_game, container, false);
        /*TextView start_text = (TextView)v.findViewById(R.id.start);
        start_text.setText(description);*/

        gameinfo =(TextView)v.findViewById(R.id.textView);
        startButton =(Button)v.findViewById(R.id.button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateGame(1);
            }
        });


        return v;

    }

}
