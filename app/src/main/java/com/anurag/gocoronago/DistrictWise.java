package com.anurag.gocoronago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DistrictWise extends AppCompatActivity {
    private TextView state;
    private ExpandableLinearLayout ex;
    private RequestQueue requestQueue,queue;
    private StateModel stateModel;
    private List<StateModel> stateModelList;
    private List<CountMode> countModeList;
    private CountMode countMode;
    private StateAdapter stateAdapter;
    private RecyclerView recyclerView,recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_wise);


        recyclerView2=findViewById(R.id.recyclerv1);
        recyclerView=findViewById(R.id.recyclerview1);

        stateModelList=new ArrayList<>();
        countModeList=new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        loadData();

    }

    private void loadData() {
        requestQueue= Volley.newRequestQueue(this);
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://api.covid19india.org/v2/state_district_wise.json", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                    try {
                       for(int i=0;i<response.length();i++){
                           JSONObject jsonObject=response.getJSONObject(i);
                           String statename=jsonObject.getString("state");
                           String statecode=jsonObject.getString("statecode");
                           stateModel=new StateModel(statename,statecode);
                           stateModelList.add(stateModel);
//                           JSONArray jsonArray=jsonObject.getJSONArray("districtData");
//                           for(int j=0;j<jsonArray.length();j++){
//                               JSONObject o =jsonArray.getJSONObject(j);
//                               String ds=o.getString("district");
//                               String c=o.getString("confirmed");
//                               String a=o.getString("active");
//                               String r=o.getString("recovered");
//                               String d=o.getString("deceased");
//
//
//                               countMode=new CountMode(ds,c,a,r,d);
//                               countModeList.add(countMode);
//
//
//                           }

                           Log.d("test", "onResponse: "+statename);
                           stateAdapter=new StateAdapter(DistrictWise.this,stateModelList);

                           recyclerView.setAdapter(stateAdapter);
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
        requestQueue.add(jsonArrayRequest);
    }
}
