package com.anurag.gocoronago;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class LiveCount extends Fragment {
    private RequestQueue requestQueue;
    private MapView mapView;
    private ProgressBar progressBar,progressBar1;
    private RequestQueue requestQueue2;
    private TextView confirmed,rec,death,confirmed1,rec1,death1;
    private CardView cardView,cardView1;
    private GoogleMap mMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";


    public LiveCount() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment





        View view =inflater.inflate(R.layout.fragment_live_count, container, false);


        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue2= Volley.newRequestQueue(getContext());
        progressBar = view.findViewById(R.id.spin_kit);
        progressBar1 = view.findViewById(R.id.spin_kit1);
        confirmed= view.findViewById(R.id.Confimed);
        rec=view.findViewById(R.id.recovered);
        cardView=view.findViewById(R.id.cardViewIndia);
        cardView1=view.findViewById(R.id.cardView9);
        death=view.findViewById(R.id.death);
        confirmed1=view.findViewById(R.id.Confimed1);
        rec1=view.findViewById(R.id.recovered1);
        death1=view.findViewById(R.id.death1);




        Sprite circle=new Circle();
        Sprite cicle1=new Circle();
        progressBar.setIndeterminateDrawable(cicle1);
        progressBar1.setIndeterminateDrawable(circle);




        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(".Details"));
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(".Global"));
            }
        });



        load();

        return view;
    }

    private void load() {

        progressBar1.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.covid19api.com/summary", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("Global");


                    Log.d("respon", "onResponse: " + jsonObject);

                    String totalConfirmed = jsonObject.getString("TotalConfirmed");
                    String totalRecovered = jsonObject.getString("TotalRecovered");
                    String totalDeaths = jsonObject.getString("TotalDeaths");


                    confirmed1.setText(totalConfirmed);
                    rec1.setText(totalRecovered);
                    death1.setText(totalDeaths);


                    progressBar1.setVisibility(View.INVISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue2.add(jsonObjectRequest);


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, "https://api.rootnet.in/covid19-in/stats/latest", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("JsonArray", "onResponse: " + response.getJSONObject("data").getJSONObject("summary").getString("total"));
                    int total=response.getJSONObject("data").getJSONObject("summary").getInt("total");
                    int recoveres=response.getJSONObject("data").getJSONObject("summary").getInt("discharged");
                    int deth=response.getJSONObject("data").getJSONObject("summary").getInt("deaths");

                    Log.d("confirmed", "onResponse: "+total);


                    confirmed.setText(""+total);
                    rec.setText(""+recoveres);
                    death.setText(""+deth);


                    progressBar.setVisibility(View.INVISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: " + error);
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void onStart() {
        load();
        super.onStart();
    }

    @Override
    public void onResume() {
        load();
        super.onResume();
    }
}
