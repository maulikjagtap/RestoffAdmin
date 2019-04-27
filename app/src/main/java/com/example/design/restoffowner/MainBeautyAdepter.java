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

class MainBeautyAdepter  extends RecyclerView.Adapter<MainBeautyAdepter.MainHolder> {
    private Context context;
    private ArrayList<VerticalModel> verticalModelArrayList;
    public MainBeautyAdepter(Context context, ArrayList<VerticalModel> verticalModelArrayList) {
        this.context = context;
        this.verticalModelArrayList = verticalModelArrayList;
    }

    @NonNull
    @Override
    public MainBeautyAdepter.MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stationarymainrow,viewGroup,false);
        return new MainBeautyAdepter.MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainBeautyAdepter.MainHolder mainHolder, int i) {
        VerticalModel verticalModel=verticalModelArrayList.get(i);
        String title=verticalModel.getTitle();
        ArrayList<Horizontalmodel> horizontalmodelArrayList=verticalModel.getHorizontalmodelArrayList();
        mainHolder.textView.setText(title);
        BeautytHorizontalrvAdepter  beautytHorizontalrvAdepter=new BeautytHorizontalrvAdepter(context,horizontalmodelArrayList);
        mainHolder.recyclerView.setHasFixedSize(true);
        mainHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        mainHolder.recyclerView.setAdapter(beautytHorizontalrvAdepter);

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
