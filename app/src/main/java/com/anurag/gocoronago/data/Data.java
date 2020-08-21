package com.anurag.gocoronago.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.anurag.gocoronago.controller.AppController;
import com.anurag.gocoronago.model.ModelDetails;
import com.anurag.gocoronago.model.StateModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Data {
    ArrayList<ModelDetails> modelDetailsArrayList = new ArrayList<>();
    ArrayList<StateModel> stateModelArrayList=new ArrayList<>();
    private String url = "https://api.rootnet.in/covid19-in/stats/latest";

    public List<ModelDetails> getModelDetails(final AnswerAsynResponce callback) {

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("regional");
                    Log.d("size", "onResponse: " + jsonArray.length());
                    for (int i = jsonArray.length() - 2; i >= 0; i--) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String state = object.getString("loc");
                        int totalConfirmed = object.getInt("totalConfirmed");
                        int discharged = object.getInt("discharged");
                        int death = object.getInt("deaths");


                        modelDetailsArrayList.add(new ModelDetails(state, totalConfirmed, death, discharged));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(callback!=null)callback.processFinished(modelDetailsArrayList);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
         AppController.getInstance().getRequestQueue().add(jsonObjectRequest);
         return modelDetailsArrayList;
    }

    public List<StateModel> getStateModel(final stateModelAsyncResponce callback) {

        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://api.covid19india.org/v2/state_district_wise.json", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject=response.getJSONObject(i);
                        String statename=jsonObject.getString("state");
                        String statecode=jsonObject.getString("statecode");

                        stateModelArrayList.add( new StateModel(statename,statecode));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(callback!=null)callback.processFinished(stateModelArrayList);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().getRequestQueue().add(jsonArrayRequest);
        return stateModelArrayList;
    }
}
