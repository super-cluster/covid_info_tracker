package com.anurag.gocoronago.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anurag.gocoronago.R;
import com.anurag.gocoronago.model.ModelHelp;

import java.util.List;

public class AdapterHelp extends RecyclerView.Adapter<AdapterHelp.MyHolder> {
    private Context context;
    private List<ModelHelp> modelHelps;

    public AdapterHelp(Context context, List<ModelHelp> modelHelps) {
        this.context = context;
        this.modelHelps = modelHelps;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.row_helpline,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        String State=modelHelps.get(i).getLoc();
        String Number=modelHelps.get(i).getNumber();

        holder.state.setText(State);
        holder.number.setText(Number);

    }

    @Override
    public int getItemCount() {
        return modelHelps.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        private TextView state,number;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            state=itemView.findViewById(R.id.statehelp);
            number=itemView.findViewById(R.id.Number);


        }
    }
}
