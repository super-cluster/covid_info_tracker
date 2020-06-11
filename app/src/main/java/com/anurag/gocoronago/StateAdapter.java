package com.anurag.gocoronago;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyHolder> {
    private Context context;
    private List<StateModel> stateModelList;
    private CountMode countMode;

    public StateAdapter(Context context, List<StateModel> stateModelList) {
        this.context = context;
        this.stateModelList = stateModelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_district,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        String states=stateModelList.get(position).getState();
        String statecode1=stateModelList.get(position).getStatecode();
        holder.state.setText(states);


        holder.layout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.recycler_anim));





        holder.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!holder.expanded){
                   holder.recyclerView.setVisibility(View.GONE);
                   holder.expanded=true;
               }else{
                   holder.recyclerView.setVisibility(View.VISIBLE);
                   holder.recyclerView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animm));
                   holder.expanded=false;
               }

            }
        });




        final List<CountMode> countModeList=new ArrayList<>();






        RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(context);
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://api.covid19india.org/v2/state_district_wise.json", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject=response.getJSONObject(i);
                        JSONArray jsonArray=jsonObject.getJSONArray("districtData");

                        if(position==i) {
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject o = jsonArray.getJSONObject(j);
                                String ds = o.getString("district");
                                String c = o.getString("confirmed");
                                String a = o.getString("active");
                                String r = o.getString("recovered");
                                String d = o.getString("deceased");


                                countMode = new CountMode(ds, c, a, r, d);
                                countModeList.add(countMode);
                                CountAdapter countAdapter = new CountAdapter(context, countModeList);

                                LinearLayoutManager layoutManager = new LinearLayoutManager(context);

                                holder.recyclerView.setLayoutManager(layoutManager);
                                holder.recyclerView.setAdapter(countAdapter);
                            }

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

    @Override
    public int getItemCount() {
        return stateModelList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        private TextView state;
        private RecyclerView recyclerView;
        private Boolean expanded;
        private LinearLayout layout;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            state = itemView.findViewById(R.id.stateTv);
            recyclerView=itemView.findViewById(R.id.recyclerv1);
            expanded=true;
            layout=itemView.findViewById(R.id.tv);




        }
    }
}
