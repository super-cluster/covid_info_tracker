package com.anurag.gocoronago.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.anurag.gocoronago.R;
import com.anurag.gocoronago.model.CountModel;

import java.util.List;

public class CountAdapter extends RecyclerView.Adapter<CountAdapter.MyHolder> {
    private Context context;
    private List<CountModel> countModeList;

    public CountAdapter(Context context, List<CountModel> countModeList) {
        this.context = context;
        this.countModeList = countModeList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_count,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String ds=countModeList.get(position).getDistrict();
        String c=countModeList.get(position).getConfirmed();
        String a=countModeList.get(position).getActive();
        String r=countModeList.get(position).getRecovered();
        String d=countModeList.get(position).getDeceased();



        holder.district.setText(ds);
        holder.cases.setText(c);
        holder.active.setText(a);
        holder.rec.setText(r);
        holder.death.setText(d);




    }



    @Override
    public int getItemCount() {
        return countModeList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        private TextView district,cases,active,rec,death;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            district=itemView.findViewById(R.id.districtTv);
            cases=itemView.findViewById(R.id.confirmedTv1);
            active=itemView.findViewById(R.id.activeTv1);
            rec=itemView.findViewById(R.id.recoveredTv1);
            death=itemView.findViewById(R.id.deathsTv);





        }
    }
}
