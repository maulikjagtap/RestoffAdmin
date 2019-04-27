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

class MainclothAdepter  extends RecyclerView.Adapter<MainclothAdepter.MainHolder> {
    private Context context;
    private ArrayList<VerticalModel> verticalModelArrayList;
    public MainclothAdepter(Context context, ArrayList<VerticalModel> verticalModelArrayList) {
        this.context = context;
        this.verticalModelArrayList = verticalModelArrayList;
    }


    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stationarymainrow,viewGroup,false);
        return new MainclothAdepter.MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder mainHolder, int i) {
        VerticalModel verticalModel=verticalModelArrayList.get(i);
        String title=verticalModel.getTitle();
        ArrayList<Horizontalmodel> horizontalmodelArrayList=verticalModel.getHorizontalmodelArrayList();
        mainHolder.textView.setText(title);
        ClothHorizontalrvAdepter  clothHorizontalrvAdepter=new ClothHorizontalrvAdepter(context,horizontalmodelArrayList);
        mainHolder.recyclerView.setHasFixedSize(true);
        mainHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        mainHolder.recyclerView.setAdapter(clothHorizontalrvAdepter);

    }

    @Override
    public int getItemCount() {
        return verticalModelArrayList.size();
    }

    public class MainHolder  extends RecyclerView.ViewHolder{
        private RecyclerView recyclerView;
        private TextView textView;
        public MainHolder(@NonNull View itemView) {

            super(itemView);
            recyclerView=itemView.findViewById(R.id.stationmainrv);
            textView=itemView.findViewById(R.id.stationmaintexview);
        }
    }
}
