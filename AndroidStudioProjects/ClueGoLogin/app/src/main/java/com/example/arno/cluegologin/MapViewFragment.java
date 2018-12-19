package com.example.arno.cluegologin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.arno.cluegologin.Objects.Game;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapViewFragment extends Fragment {
    private MapFragmentListener listener;
    public interface MapFragmentListener{
        void onInputMapSent(JSONObject game);
    }


    boolean hasBeen;
    Marker destMarker;
    MapView mMapView;
    RequestQueue mRequestQueue;
    StringRequest stringRequest;
    JSONObject allLocations;
    Location policeOfficeLocation;
    private final static int LOCATION_REQUEST_CODE = 101;
    private GoogleMap googleMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public JSONObject gameFromStartGameFragment;

    private String url ="https://clugo.azurewebsites.net/api/location";
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);
                // For showing a move to my location button

                Bundle bundle = getArguments();
                Game gameFromStart = (Game) bundle.getSerializable("game");

                List<com.example.arno.cluegologin.Objects.Location> locationsFromGame = gameFromStart.getLocations();

                for (int i = 0; i <locationsFromGame.size() ; i++) {

                    double locLat = locationsFromGame.get(i).getLocLat();
                    double locLng = locationsFromGame.get(i).getLoclong();
                    String locName = locationsFromGame.get(i).getLocName();

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(locLat, locLng))
                            .title(locName));
                }

                LatLng center1 = new LatLng(locationsFromGame.get(1).getLocLat(),locationsFromGame.get(1).getLoclong());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center1, 15));

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    mMap.setMyLocationEnabled(true);

                // For zooming automatically to the location of the marker

                final LocationManager locationManager= (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

                final LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {


                        if(destMarker != null){
                            Location markerLoc = new Location("Destination");
                            markerLoc.setLatitude(destMarker.getPosition().latitude);
                            markerLoc.setLongitude(destMarker.getPosition().longitude);
                            float distance = location.distanceTo(markerLoc);

                            Log.e("distancevalue", destMarker.getTitle());

                            if(distance<20 && hasBeen==false){
                                hasBeen=true;
                                if(destMarker.getTitle().equals("Politiekantoor")){
                                    Intent intent = new Intent(getActivity(), GuessActivity.class);
                                    startActivity(intent);
                                }
                                Log.e("toast","locations are the same");

                                Intent intent = new Intent(getActivity(), PuzzleActivity.class);
                                startActivity(intent);
                                Toast.makeText(getActivity(),"You have arrived at your destination",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getActivity(), PuzzleActivity.class);

                                startActivity(i);
                            }
                            if(distance>20)
                            {
                                Toast.makeText(getActivity(), "not at destination", Toast.LENGTH_LONG).show();
                                Log.e("toast","locations are not the same" + distance);

                            }}
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);

                // For dropping a marker at a point on the Map

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        destMarker = marker;
                        marker.showInfoWindow();
                        return true;
                    }
                });
                GoogleMap.InfoWindowAdapter infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        Location markerLoc = new Location("Destination");
                        markerLoc.setLatitude(destMarker.getPosition().latitude);
                        markerLoc.setLongitude(destMarker.getPosition().longitude);
                        float dist = googleMap.getMyLocation().distanceTo(markerLoc);

                        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v = getLayoutInflater().inflate(R.layout.custom_window_info,null);
                        TextView distanceText = (TextView)v.findViewById(R.id.distance);
                        TextView titleText = (TextView)v.findViewById(R.id.title);
                        distanceText.setText(String.format(Math.round(dist)+" meters"));
                        titleText.setText(marker.getTitle());
                        return v;
                    }
                };
                googleMap.setInfoWindowAdapter(infoWindowAdapter);
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String url = getRequestUrl(googleMap.getMyLocation(), marker.getPosition());
                        TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
                        taskRequestDirections.execute(url);

                    }
                });

            }

        });


        return rootView;
    }
    public void updateGame(JSONObject newGame){
        gameFromStartGameFragment = newGame;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MapFragmentListener){
            listener =(MapFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    +"mustimplement MapFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener =null;
    }

    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(getActivity(),
                permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{permissionType}, requestCode
            );
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Unable to show location - permission required", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private String getRequestUrl(Location origin, LatLng dest){
        String str_org = "origin=" + origin.getLatitude()+","+origin.getLongitude();
        String str_dest = "destination=" + dest.latitude+","+dest.longitude;

        String mode = "mode=walking";
        String output = "json";
        String api_key = "key=AIzaSyBCqW3-1sRfO1_aCvsYJSqY7KclRAOJJbI";
        String params = mode + "&" + str_org + "&" + str_dest +"&"+api_key;
        String url = "https://maps.googleapis.com/maps/api/directions/" + output+"?"+params;
        return url;
    }
    private String requestDirection(String reqUrl){
        String responseString ="";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection=null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while((line = bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    public class TaskRequestDirections extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings){
            String responseString = "";
            try{
                responseString = requestDirection(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
            Log.e("responseurl",responseString);
            return responseString;
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            TaskParser taskParser= new TaskParser();
            taskParser.execute(s);
        }
    }
    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>>{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {

            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>>routes=null;
            try{
                jsonObject = new JSONObject(strings[0]);
                DirDataParser dirDataParser= new DirDataParser();
                routes = dirDataParser.parse(jsonObject);
            }catch (JSONException e){
                e.printStackTrace();
            }
            return routes;
        }
        @Override
        protected void onPostExecute(List<List<HashMap<String,String>>>lists){
            ArrayList points = null;

            PolylineOptions polylineOptions = null;
            for(List<HashMap<String,String>>path:lists){
                points = new ArrayList();
                polylineOptions = new PolylineOptions();
                for(HashMap<String,String>point : path){
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));
                    points.add(new LatLng(lat,lon));
                    Log.e("points",points.toString());
                }
                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                Log.e("polylines", polylineOptions.toString());
            }
            if(polylineOptions!=null){
                googleMap.addPolyline(polylineOptions);
            }
            else{
                Log.e("TAG","Directions not found");
            }
        }
    }

}
