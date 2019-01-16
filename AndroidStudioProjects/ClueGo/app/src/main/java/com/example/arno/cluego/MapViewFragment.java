package com.example.arno.cluego;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Helpers.DirDataParser;
import com.example.arno.cluego.Helpers.SuccessCallBack;
import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.GameLocation;
import com.example.arno.cluego.Objects.User;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.PointTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
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

import static com.android.volley.VolleyLog.TAG;


public class MapViewFragment extends Fragment {
    private MapFragmentListener listener;
    public interface MapFragmentListener{
        void onInputMapSent(JSONObject game);
    }

    ShowcaseView showcaseView;

    User usr = new User();
    String baseUrl;

    Marker destMarker;
    MapView mMapView;
    int gameId;
    String locName;

    ShowcaseView scv1;

    GameLocation gameLocation;
    List<GameLocation> visitedLocations = new ArrayList<>();
    List<GameLocation> notVisitedLocations = new ArrayList<>();
    GameLocation policeStation = new GameLocation();
    private final static int LOCATION_REQUEST_CODE = 101;
    private GoogleMap googleMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public JSONObject gameFromStartGameFragment;

    int tutorialIndex;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_maps, container, false);

        gameId = getActivity().getIntent().getIntExtra("gameId", 0);
        if (gameId == 0)
            Toast.makeText(getContext(),"GameId not found", Toast.LENGTH_SHORT).show();


        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        baseUrl = getResources().getString(R.string.baseUrl);

        GetLocations(gameId, new SuccessCallBack() {
            @Override
            public void onSuccess() {
                try {
                    MapsInitializer.initialize(getActivity().getApplicationContext());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //mMapView.onResume();
                mMapView.getMapAsync(new OnMapReadyCallback() {

                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        googleMap = mMap;



                        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);
                        final BitmapDescriptor greenIcon = BitmapDescriptorFactory.fromResource(R.drawable.green_marker);
                        final BitmapDescriptor policeOffice = BitmapDescriptorFactory.fromResource(R.drawable.police_shield);

                        Marker policeMarker = googleMap.addMarker(new MarkerOptions()
                                .position(policeStation.getLocLtLng())
                                .title(policeStation.getLocName())
                                .draggable(true)
                                .icon(policeOffice));

                        for (int i = 0; i < notVisitedLocations.size() ; i++) {
                            googleMap.addMarker(new MarkerOptions()
                                    .position(notVisitedLocations.get(i).getLocLtLng())
                                    .title(notVisitedLocations.get(i).getLocName())
                                    .draggable(true));
                        }
                        for (int i = 0; i <visitedLocations.size() ; i++) {

                            googleMap.addMarker(new MarkerOptions()
                                    .position(visitedLocations.get(i).getLocLtLng())
                                    .title(visitedLocations.get(i).getLocName() + "(*)")
                                    .icon(greenIcon)
                                    .draggable(true));
                        }
                        try {
                            LatLng center1 = notVisitedLocations.get(0).getLocLtLng();
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center1, 15));
                        }catch(IndexOutOfBoundsException ex) {
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.219,4.401694), 15));
                        }

                        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                        textPaint.setColor(Color.YELLOW);
                        textPaint.setTextSize(54);

                        scv1 = new ShowcaseView.Builder(getActivity())
                                .setTarget(new PointTarget( (rootView.getWidth()/2),(rootView.getHeight()/2)))
                                .setContentText("Click on one of the locations to get a little more information about it. " +
                                        "Click on the marker information window to set it as your active location.")
                                .setContentTitle("Point of interest.")
                                .setContentTextPaint(textPaint)
                                .singleShot(8)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (tutorialIndex == 0) {
                                            scv1.setContentText("Walk over there and solve the puzzle to gather more clues and hints about the murder." +
                                                    " Once you've solved a puzzle, the marker will turn green.");
                                            tutorialIndex++;
                                        }
                                        else if (tutorialIndex == 1)
                                            MoveTutorialToPolice();
                                        else
                                            scv1.hide();
                                    }
                                })
                                .build();
                        scv1.setButtonText("Next");

                        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        // This aligns button to the bottom left side of screen
                        lps.addRule(RelativeLayout.ALIGN_PARENT_END);
                        lps.addRule(RelativeLayout.ALIGN_BOTTOM);
                        // Set margins to the button, we add 16dp margins here
                        int margin = ((Number) (getResources().getDisplayMetrics().density * 16)).intValue();
                        lps.setMargins(margin, margin, margin, margin);
                        scv1.setButtonPosition(lps);
                        scv1.setScrollContainer(true);


                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        googleMap.setMyLocationEnabled(true);

                        try {
                            boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));
                            if (success)
                                Log.e(TAG, "onMapReady: " );
                            if (!success) {
                                Log.e("MapsActivityRaw", "Style parsing failed.");
                            }
                        } catch (Resources.NotFoundException e) {
                            Log.e("MapsActivityRaw", "Can't find style.", e);
                        }


                        final LocationManager locationManager= (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

                        final LocationListener locationListener = new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                if(destMarker != null && !destMarker.getTitle().contains("(*)")){
                                    Location markerLoc = new Location("Destination");
                                    markerLoc.setLatitude(destMarker.getPosition().latitude);
                                    markerLoc.setLongitude(destMarker.getPosition().longitude);
                                    float distance = location.distanceTo(markerLoc);

                                    Log.e("distancevalue", destMarker.getTitle());

                                    if(distance<100000){
                                        if(destMarker.getTitle().equals("Politiekantoor")){
                                            destMarker = null;
                                            Intent intent = new Intent(getActivity(), GuessActivity.class);
                                            intent.putExtra("gameId", gameId);
                                            intent.putExtra("userDataPackage", usr);
                                            startActivity(intent);

                                        }
                                        else{
                                            locName = destMarker.getTitle();
                                            destMarker = null;
                                            Intent intent = new Intent(getActivity(), PuzzleActivity.class);
                                            intent.putExtra("gameId", gameId);
                                            intent.putExtra("locName", locName);
                                            startActivity(intent);
                                        }
                                    }
                                }
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

                        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                            @Override
                            public void onMarkerDragStart(Marker marker) {

                            }

                            @Override
                            public void onMarkerDrag(Marker marker) {

                            }

                            @Override
                            public void onMarkerDragEnd(Marker marker) {

                            }
                        });

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
                                try{
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
                                }catch(NullPointerException ex){
                                    return  null;
                                }
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
                    Toast.makeText(getActivity(), "Unable to show gameLocation - permission required", Toast.LENGTH_LONG).show();
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


    public void GetLocations (int gameId, final SuccessCallBack callBack){
            String url = baseUrl + "location/" + gameId;
            Log.d("TAG", "GetSuspects: " + url);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try{
                                if (response.length() == 0)
                                    Toast.makeText(getContext(), "No locations found", Toast.LENGTH_SHORT).show();
                                else{

                                    for(int i=0;i<response.length();i++){
                                        GameLocation gameLocation = new GameLocation();
                                        JSONObject _locations = response.getJSONObject(i);
                                        Log.d(TAG, "onResponse: " + response);

                                        gameLocation.setVisited(_locations.getBoolean("visited"));
                                        JSONObject _location = _locations.getJSONObject("location");
                                        Log.d(TAG, "onResponse: " + _location);

                                        gameLocation.setLocId(_location.getInt("locId"));
                                        gameLocation.setLocName(_location.getString("locName"));
                                        gameLocation.setLocDescription(_location.getString("locDescription"));
                                        gameLocation.setLocLtLng(_location.getDouble("locLat"), _location.getDouble("locLong"));

                                        if (gameLocation.getLocName().contains("kantoor"))
                                            policeStation = gameLocation;
                                        else if(gameLocation.isVisited())
                                            visitedLocations.add(gameLocation);
                                        else
                                            notVisitedLocations.add(gameLocation);
                                    }
                                }
                                callBack.onSuccess();
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            Toast.makeText(getContext(), "Er was eens een error", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
        }


    private void MoveTutorialToPolice(){
        try {
            LatLng center1 = policeStation.getLocLtLng();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center1, 15));
            tutorialIndex++;
        }catch(IndexOutOfBoundsException ex) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.219,4.401694), 15));
        }
        scv1.setContentTitle("Police Station");
        scv1.setContentText("Once you've found enough evidence and think you know who the real murderer is, go to the police station and declare your findings!");
        scv1.setHideOnTouchOutside(true);
    }
    }

