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

class MainRestaurantAdepter  extends RecyclerView.Adapter<MainRestaurantAdepter.MainHolder> {
    private Context context;
    private ArrayList<VerticalModel> verticalModelArrayList;

    public MainRestaurantAdepter(Context context, ArrayList<VerticalModel> verticalModelArrayList) {
        this.context = context;
        this.verticalModelArrayList = verticalModelArrayList;
    }
    @NonNull
    @Override
    public MainRestaurantAdepter.MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stationarymainrow,viewGroup,false);
        return new MainRestaurantAdepter.MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRestaurantAdepter.MainHolder mainHolder, int i) {
        VerticalModel verticalModel=verticalModelArrayList.get(i);
        String title=verticalModel.getTitle();
        ArrayList<Horizontalmodel> horizontalmodelArrayList=verticalModel.getHorizontalmodelArrayList();
        mainHolder.textView.setText(title);
        RestaurantHorizontalrvAdepter  restaurantHorizontalrvAdepter=new RestaurantHorizontalrvAdepter(context,horizontalmodelArrayList);
        mainHolder.recyclerView.setHasFixedSize(true);
        mainHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        mainHolder.recyclerView.setAdapter(restaurantHorizontalrvAdepter);

    }

    @Override
    public int getItemCount() {
        return verticalModelArrayList.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder{
        private RecyclerView recyclerView;
        private TextView textView;
        public MainHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.stationmainrv);
            textView=itemView.findViewById(R.id.stationmaintexview);
        }
    }
}
