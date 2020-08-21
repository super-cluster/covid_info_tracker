package com.anurag.gocoronago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.anurag.gocoronago.adapter.StateAdapter;
import com.anurag.gocoronago.data.Data;
import com.anurag.gocoronago.data.stateModelAsyncResponce;
import com.anurag.gocoronago.model.StateModel;


import java.util.ArrayList;


public class DistrictWise extends AppCompatActivity {
    private StateAdapter stateAdapter;
    private RecyclerView recyclerView,recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_wise);


        recyclerView2=findViewById(R.id.recyclerv1);
        recyclerView=findViewById(R.id.recyclerview1);


        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        loadData();

    }

    private void loadData() {
        new Data().getStateModel(new stateModelAsyncResponce() {
            @Override
            public void processFinished(ArrayList<StateModel> stateModelArrayList) {
                stateAdapter=new StateAdapter(DistrictWise.this,stateModelArrayList);
                recyclerView.setAdapter(stateAdapter);
            }
        });
    }
}
