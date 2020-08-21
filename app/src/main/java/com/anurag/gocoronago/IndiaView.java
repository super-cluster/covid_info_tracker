package com.anurag.gocoronago;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anurag.gocoronago.model.ModelMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IndiaView extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    private RequestQueue queue;
    private List<ModelMap> modelMaps;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_india_view,container,false);

        mapView=view.findViewById(R.id.mapView);
        modelMaps=new ArrayList<>();

        Bundle mapViewBundle =null;
        if(savedInstanceState!=null){
            mapViewBundle=savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }


        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getLocation();

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(),R.raw.mapstyle));
        mMap.setMaxZoomPreference(2);

        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(20,81)));

    }

    public void getLocation(){

        queue= Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://coronavirus-tracker-api.herokuapp.com/v2/locations", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("locations");


                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        JSONObject jsonObject1=jsonObject.getJSONObject("coordinates");
                        JSONObject jsonObject2=jsonObject.getJSONObject("latest");




                                    double lon =jsonObject1.getDouble("longitude");
                                    double lat=jsonObject1.getDouble("latitude");
                                    int count=jsonObject2.getInt("confirmed");

                                    Log.d("count", "onResponse: "+lon+"\n"+lat);

                                        if(count>=10000) {
                                            CircleOptions circleOptions = new CircleOptions();
                                            circleOptions.center(new LatLng(lat, lon));
                                            circleOptions.radius(200000);
                                            circleOptions.fillColor(Color.RED);
                                            circleOptions.strokeWidth(0);
                                            mMap.addCircle(circleOptions);
                                        }else if(count<=10000&&count>1000){
                                            CircleOptions circleOptions = new CircleOptions();
                                            circleOptions.center(new LatLng(lat, lon));
                                            circleOptions.radius(100000);
                                            circleOptions.fillColor(Color.RED);
                                            circleOptions.strokeWidth(0);
                                            mMap.addCircle(circleOptions);
                                        }else {
                                            CircleOptions circleOptions = new CircleOptions();
                                            circleOptions.center(new LatLng(lat, lon));
                                            circleOptions.radius(40000);
                                            circleOptions.fillColor(Color.RED);
                                            circleOptions.strokeWidth(0);
                                            mMap.addCircle(circleOptions);
                                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);



    }
//    private class GeocoderHandler extends Handler {
//        @Override
//        public void handleMessage(Message message) {
//            String locationAddress;
//            switch (message.what) {
//                case 1:
//                    Bundle bundle = message.getData();
//                    locationAddress = bundle.getString("address");
//                    break;
//                default:
//                    locationAddress = null;
//            }
//            latLongTV.setText(locationAddress);
//        }
//    }
}
