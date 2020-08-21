package com.anurag.gocoronago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.anurag.gocoronago.adapter.AdapterDetails;
import com.anurag.gocoronago.controller.AppController;
import com.anurag.gocoronago.data.AnswerAsynResponce;
import com.anurag.gocoronago.data.Data;
import com.anurag.gocoronago.model.ModelDetails;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Details extends AppCompatActivity {
    private TextView confirmed,rec,death;
    private CardView cardView;
    private RecyclerView recyclerView;
    private AdapterDetails adapterDetails;
    private ProgressBar progressBar;
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

        load();




        JsonObjectRequest jsonArrayRequest =new JsonObjectRequest(Request.Method.GET, "https://api.rootnet.in/covid19-in/stats/latest", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    int total=response.getJSONObject("data").getJSONObject("summary").getInt("total");
                    int recoveres=response.getJSONObject("data").getJSONObject("summary").getInt("discharged");
                    int deth=response.getJSONObject("data").getJSONObject("summary").getInt("deaths");


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
        AppController.getInstance().getRequestQueue().add(jsonArrayRequest);

    }

    private void load() {
        new Data().getModelDetails(new AnswerAsynResponce() {
            @Override
            public void processFinished(ArrayList<ModelDetails> modelDetailsArrayList) {
                adapterDetails = new AdapterDetails(Details.this, modelDetailsArrayList);
                recyclerView.setAdapter(adapterDetails);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}
