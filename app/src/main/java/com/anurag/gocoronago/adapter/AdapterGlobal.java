package com.anurag.gocoronago.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anurag.gocoronago.R;
import com.anurag.gocoronago.model.ModelGlobal;

import java.util.List;

public class AdapterGlobal extends RecyclerView.Adapter<AdapterGlobal.MyHolder> {
private Context context;
private List<ModelGlobal> modelGlobals;

    public AdapterGlobal(Context context, List<ModelGlobal> modelGlobals) {
        this.context = context;
        this.modelGlobals = modelGlobals;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        String country=modelGlobals.get(i).getCountry();
        String confirmed=modelGlobals.get(i).getTotalConfirmed();
        String recovered=modelGlobals.get(i).getTotalRecovered();
        String deaths=modelGlobals.get(i).getTotalDeaths();

        holder.layout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.recycler_anim));


        holder.country.setText(country);
        holder.rec.setText(recovered);
        holder.total.setText(confirmed);
        holder.death.setText(deaths);



    }

    @Override
    public int getItemCount() {
        return modelGlobals.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        private TextView country,total,rec,death;
        private LinearLayout layout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            country=itemView.findViewById(R.id.state);
            total=itemView.findViewById(R.id.confirm);
            rec=itemView.findViewById(R.id.recover);
            death=itemView.findViewById(R.id.deat);
            layout=itemView.findViewById(R.id.stateLayout);

        }
    }
}
