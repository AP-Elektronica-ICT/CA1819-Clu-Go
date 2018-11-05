package com.example.arno.cluegologin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.places.Places;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GameFragment extends Fragment {
    View view;
    private GoogleMap mMap;
    private Location location;
    private GoogleApiClient googleApiClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1;

    private static final String TAG = MapsActivity.class.getName();
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private String url= "http://192.168.1.10:45457/api/location";


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
// Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_game, container, false);

            /*googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addApi(LocationServices.API)
                    .build();

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);*/
// get the reference of Button

            return view;
    }

    /*private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient);
        if(null != locationAvailability && locationAvailability.isLocationAvailable()){
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if(location != null){
                LatLng currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,17));
            }
        }
        getLocationList();
    }

    public void getMapAsync (OnMapReadyCallback callback){

    }

    @Override
    protected void onStart() {

        super.onStart();
        googleApiClient.connect();
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(googleApiClient != null && googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        getLocationList();
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.221228, 4.399698))
                .title("Standbeeld van Brabo"));
    }
    @Override
    public void onConnected(@Nullable Bundle bundle){setUpMap();};

    @Override
    public void onConnectionSuspended(int i){

    }
    @Override
    public void onLocationChanged(Location location){

    }

    public void getLocationList(){
        mRequestQueue = Volley.newRequestQueue(this);

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.i(TAG,"Response: " + response.toString());

                try {
                    JSONArray locationList = new JSONArray(response);
                    for (int i = 0; i <= locationList.length(); i++)
                    {
                        JSONObject singleLoc =new JSONObject(locationList.getString(i));
                        String locName = singleLoc.getString("locName");
                        Double locLat = singleLoc.getDouble("locLat");
                        Double locLong = singleLoc.getDouble("locLong");
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(locLat, locLong))
                                .title(locName));
                        Log.d("String","lat: " + locLat.toString() +"long: " + locLong.toString());
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error: " + error.toString());
            }
        });

        mRequestQueue.add(stringRequest);
    }*/
}