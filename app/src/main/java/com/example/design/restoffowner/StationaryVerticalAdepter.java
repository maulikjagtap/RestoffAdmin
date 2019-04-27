package com.example.design.restoffowner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class StationaryVerticalAdepter  extends RecyclerView.Adapter<StationaryVerticalAdepter.StationayVerticalHolder> {
    private  Context  context;
    private ArrayList<StationaryVerticalModel> stationaryVerticalModelArrayList;

    public StationaryVerticalAdepter(Context context, ArrayList<StationaryVerticalModel> stationaryVerticalModelArrayList) {
            this.context=context;
            this.stationaryVerticalModelArrayList=stationaryVerticalModelArrayList;
    }

    @NonNull
    @Override
    public StationayVerticalHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stationarymainrow,viewGroup,false);
        return new StationayVerticalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationayVerticalHolder stationayVerticalHolder, int i) {
        StationaryVerticalModel stationaryVerticalModel=stationaryVerticalModelArrayList.get(i);
        String title=stationaryVerticalModel.getTitle();
        stationayVerticalHolder.textView.setText(title);
        ArrayList<StationaryIteamModel> stationaryIteamModelArrayList=stationaryVerticalModel.getStationaryIteamModelArrayList();
        StationaryHorizontalAdepter stationaryHorizontalAdepter=new StationaryHorizontalAdepter(context,stationaryIteamModelArrayList);
        stationayVerticalHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        stationayVerticalHolder.recyclerView.setAdapter(stationaryHorizontalAdepter);



    }

    @Override
    public int getItemCount() {
        return stationaryVerticalModelArrayList.size();
    }

    public class StationayVerticalHolder extends  RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private TextView textView;
        public StationayVerticalHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.stationmainrv);
            textView=itemView.findViewById(R.id.stationmaintexview);
        }
    }
}
