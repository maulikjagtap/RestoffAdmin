package com.example.design.restoffowner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public  class Restaurant_home extends Fragment {
    private View view;
    private RecyclerView mainrecycle;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<VerticalModel> verticalModelArrayList;
    private ArrayList<TitleModle> titleModleArrayList;
    private String iteamkey;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.restaurant_home,container,false);
        mainrecycle = view.findViewById(R.id.restaurantrecycle);
        shimmerFrameLayout=view.findViewById(R.id.facebookshimmer);
        verticalModelArrayList = new ArrayList<>();
        titleModleArrayList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mainrecycle.setLayoutManager(layoutManager);
        final MainRestaurantAdepter mainrvAdepter = new MainRestaurantAdepter(getContext(), verticalModelArrayList);

        mainrecycle.setAdapter(mainrvAdepter);

        databaseReference.child("RestaurantIteamMenu")
                .child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RestaurantMenuModel restaurantMenuModel = snapshot.getValue(RestaurantMenuModel.class);
                    iteamkey=snapshot.getKey();
                    final VerticalModel verticalModel=new VerticalModel();
//                    Toast.makeText(getActivity(), ""+iteamkey, Toast.LENGTH_SHORT).show();
                    verticalModel.setTitle(restaurantMenuModel.getIteamcategory());
                    final ArrayList<Horizontalmodel> horizontalmodelArrayList = new ArrayList<>();
                    databaseReference.child("restaurantIteams").child(firebaseAuth.getCurrentUser().getUid())
                            .child(iteamkey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                                Log.e("mydata", snapshot1.getValue().toString());
                                Horizontalmodel horizontalmodel = snapshot1.getValue(Horizontalmodel.class);
                                horizontalmodelArrayList.add(horizontalmodel);
                            }
                            verticalModel.setHorizontalmodelArrayList(horizontalmodelArrayList);
                            verticalModelArrayList.add(verticalModel);
                            mainrvAdepter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    mainrecycle.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

}
