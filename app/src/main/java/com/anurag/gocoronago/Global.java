package com.anurag.gocoronago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LauncherActivity;
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

public class Global extends AppCompatActivity {
    private TextView confirmed,rec,death;
    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private AdapterGlobal adapterGlobal;
    private List<ModelGlobal> modelGlobals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);

        progressBar=findViewById(R.id.spin_kit3);
        recyclerView=findViewById(R.id.recyler1);
        confirmed= findViewById(R.id.Confimed2);
        rec=findViewById(R.id.recovered2);
        death=findViewById(R.id.death2);
        requestQueue= Volley.newRequestQueue(this);



        Sprite threeBounce =new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);


        LinearLayoutManager layout=new LinearLayoutManager(this);
        layout.setStackFromEnd(true);
        layout.setReverseLayout(true);

        recyclerView.setLayoutManager(layout);

        modelGlobals=new ArrayList<>();

        load();




        JsonObjectRequest jsonArrayRequest =new JsonObjectRequest(Request.Method.GET, "https://api.covid19api.com/summary", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=response.getJSONObject("Global");
                    String totalConfirmed=jsonObject.getString("TotalConfirmed");
                    String totalRecovered=jsonObject.getString("TotalRecovered");
                    String totalDeaths=jsonObject.getString("TotalDeaths");


                    confirmed.setText(totalConfirmed);
                    rec.setText(totalRecovered);
                    death.setText(totalDeaths);


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
        final JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, "https://api.covid19api.com/summary", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONArray("Countries");
                    for(int i=jsonArray.length()-1;i>=0;i--){
                        JSONObject object=jsonArray.getJSONObject(i);

                        String state=object.getString("Country");
                        String totalConfirmed=object.getString("TotalConfirmed");
                        String discharged=object.getString("TotalRecovered");
                        String death=object.getString("TotalDeaths");


                        modelGlobals.add(new ModelGlobal(state,totalConfirmed,discharged,death));


                    }
//                    Collections.sort(modelDetails, new Comparator<ModelDetails>() {
//                        @Override
//                        public int compare(ModelDetails o1, ModelDetails o2) {
//                            return o1.getLoc().compareTo(o2.getLoc());
//                        }
//                    });
                    adapterGlobal=new AdapterGlobal(Global.this,modelGlobals);
                    recyclerView.setAdapter(adapterGlobal);
                    progressBar.setVisibility(View.INVISIBLE);

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
