package com.anurag.gocoronago;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Data extends AppCompatActivity {
    private List<CountMode> countModeList;
    private CountMode countMode;

    private void loadData() {
        RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(this);
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://api.covid19india.org/v2/state_district_wise.json", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject=response.getJSONObject(i);
                        JSONArray jsonArray=jsonObject.getJSONArray("districtData");
                        for(int j=0;j<jsonArray.length();j++){
                            JSONObject o =jsonArray.getJSONObject(j);
                            String ds=o.getString("district");
                            String c=o.getString("confirmed");
                            String a=o.getString("active");
                            String r=o.getString("recovered");
                            String d=o.getString("deceased");


                            countMode=new CountMode(ds,c,a,r,d);
                            countModeList.add(countMode);


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
        requestQueue.add(jsonArrayRequest);
    }
}
