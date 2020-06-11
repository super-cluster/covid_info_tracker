package com.anurag.gocoronago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Details extends AppCompatActivity {
    private TextView confirmed,rec,death;
    private RequestQueue requestQueue;
    private CardView cardView;
    private RecyclerView recyclerView;
    private AdapterDetails adapterDetails;
    private ProgressBar progressBar;
    private List<ModelDetails> modelDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        progressBar=findViewById(R.id.spin_kit2);
        recyclerView=findViewById(R.id.recyler);
        confirmed= findViewById(R.id.Confimes);
        rec=findViewById(R.id.recoveres);
        death=findViewById(R.id.deats);
        cardView=findViewById(R.id.cardView);
        requestQueue= Volley.newRequestQueue(this);


        Sprite threeBounce=new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Details.this,DistrictWise.class));
            }
        });


        LinearLayoutManager layout=new LinearLayoutManager(this);
        layout.setStackFromEnd(true);
        layout.setReverseLayout(true);

        recyclerView.setLayoutManager(layout);

        modelDetails=new ArrayList<>();

        load();




        JsonObjectRequest jsonArrayRequest =new JsonObjectRequest(Request.Method.GET, "https://api.rootnet.in/covid19-in/stats/latest", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    Log.d("JsonArray", "onResponse: "+response.getJSONObject("data").getJSONArray("unofficial-summary").getJSONObject(0).getInt()
                    int total=response.getJSONObject("data").getJSONArray("unofficial-summary").getJSONObject(0).getInt("total");
                    int recoveres=response.getJSONObject("data").getJSONArray("unofficial-summary").getJSONObject(0).getInt("recovered");
                    int deth=response.getJSONObject("data").getJSONArray("unofficial-summary").getJSONObject(0).getInt("deaths");

                    Log.d("confirmed", "onResponse: "+total);


                    confirmed.setText(""+total);
                    rec.setText(""+recoveres);
                    death.setText(""+deth);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: "+error);
            }
        });
        requestQueue.add(jsonArrayRequest);








    }

    private void load() {
        RequestQueue requestQueue1=Volley.newRequestQueue(this);
        final JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, "https://api.rootnet.in/covid19-in/stats/latest", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONObject("data").getJSONArray("regional");
                    Log.d("size", "onResponse: "+jsonArray.length());
                    for(int i=jsonArray.length()-2;i>=0;i--){
                        JSONObject object=jsonArray.getJSONObject(i);

                        String state=object.getString("loc");
                        int totalConfirmed=object.getInt("totalConfirmed");
                        int discharged=object.getInt("discharged");
                        int death=object.getInt("deaths");
                        Log.d("test", "onResponse: "+state);


                        modelDetails.add(new ModelDetails(state,totalConfirmed,death,discharged));
                        adapterDetails=new AdapterDetails(Details.this,modelDetails);
                        recyclerView.setAdapter(adapterDetails);

                        progressBar.setVisibility(View.INVISIBLE);


                    }
//                    Collections.sort(modelDetails, new Comparator<ModelDetails>() {
//                        @Override
//                        public int compare(ModelDetails o1, ModelDetails o2) {
//                            return o1.getLoc().compareTo(o2.getLoc());
//                        }
//                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: "+error);
            }
        });
        requestQueue1.add(jsonObjectRequest);
    }
}
