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
import com.anurag.gocoronago.model.ModelDetails;

import java.util.List;

public class AdapterDetails extends RecyclerView.Adapter<AdapterDetails.MyHolder> {
    private Context context;
    private List<ModelDetails> modelDetails;

    public AdapterDetails(Context context, List<ModelDetails> modelDetails) {
        this.context = context;
        this.modelDetails = modelDetails;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout, parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {

        String state=modelDetails.get(i).getLoc();
        int totalCase=modelDetails.get(i).getTotalConfirmed();
        int recoveredCase=modelDetails.get(i).getDischared();
        int deathCase=modelDetails.get(i).getDeaths();



        holder.layout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.recycler_anim));


        holder.state.setText(state);
        holder.rec.setText(""+recoveredCase);
        holder.total.setText(""+totalCase);
        holder.death.setText(""+deathCase);

    }

    @Override
    public int getItemCount() {
        return modelDetails.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        private TextView state,total,rec,death;
        private LinearLayout layout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            state=itemView.findViewById(R.id.state);
            total=itemView.findViewById(R.id.confirm);
            rec=itemView.findViewById(R.id.recover);
            death=itemView.findViewById(R.id.deat);
            layout=itemView.findViewById(R.id.stateLayout);

        }
    }
}
