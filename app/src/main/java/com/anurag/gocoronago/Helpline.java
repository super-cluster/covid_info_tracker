package com.anurag.gocoronago;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anurag.gocoronago.adapter.AdapterHelp;
import com.anurag.gocoronago.model.ModelHelp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Helpline extends Fragment {
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<ModelHelp> modelHelps;
    private TextView state,number;


    public Helpline() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_check, container, false);

        state=view.findViewById(R.id.statehelp);
        number=view.findViewById(R.id.Number);
        recyclerView=view.findViewById(R.id.recyclerContac);

        LinearLayoutManager layout=new LinearLayoutManager(getContext());
        layout.setStackFromEnd(true);
        layout.setReverseLayout(true);

        recyclerView.setLayoutManager(layout);

        modelHelps=new ArrayList<>();
        load();





        return view;
    }

    private void load() {

        requestQueue= Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://api.rootnet.in/covid19-in/contacts", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONObject("data").getJSONObject("contacts").getJSONArray("regional");

                    for(int i=jsonArray.length()-1;i>=0;i--){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String stateName=jsonObject.getString("loc");
                        String Numb=jsonObject.getString("number");

                        Log.d("number", "onResponse: "+Numb);

                        modelHelps.add(new ModelHelp(stateName,Numb));

                    }

                    AdapterHelp adapterHelp=new AdapterHelp(getContext(),modelHelps);
                    recyclerView.setAdapter(adapterHelp);




                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("number", "onResponse: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
