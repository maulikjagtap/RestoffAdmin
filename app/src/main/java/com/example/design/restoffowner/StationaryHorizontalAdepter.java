package com.example.design.restoffowner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class StationaryHorizontalAdepter  extends RecyclerView.Adapter<StationaryHorizontalAdepter.StationayHorizontalHolder> {
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<StationaryIteamModel> stationaryIteamModelArrayList;
    public StationaryHorizontalAdepter(Context context, ArrayList<StationaryIteamModel> stationaryIteamModelArrayList) {
        this.context=context;
        this.stationaryIteamModelArrayList=stationaryIteamModelArrayList;
    }

    @NonNull
    @Override
    public StationayHorizontalHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontalsubrow,viewGroup,false);
        return new StationayHorizontalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationayHorizontalHolder stationayHorizontalHolder, int i) {
        StationaryIteamModel stationaryIteamModel=stationaryIteamModelArrayList.get(i);
        stationayHorizontalHolder.textView.setText(stationaryIteamModel.getName());
        Glide.with(context).load(stationaryIteamModel.myuri).into(stationayHorizontalHolder.imageView);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        final Query query= databaseReference.child("StationaryIteams").child(firebaseAuth.getCurrentUser().getUid()).orderByChild("name").equalTo(stationaryIteamModel.getName());
        stationayHorizontalHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder  alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Delete Iteam From List");
                alertDialog.setMessage("Are you sure you want to Delete list Iteam?");
                alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Not a single Operation has been Perfome", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();

            }
        });
        stationayHorizontalHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String key=dataSnapshot.getKey();
                        Intent updatedata=new Intent(context,UpdateStationiteam.class);
                        updatedata.putExtra("datakey",key);
                        context.startActivity(updatedata);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return stationaryIteamModelArrayList.size();
    }

    public class StationayHorizontalHolder extends RecyclerView.ViewHolder{
        private Button update;
        private Button delete;
        private ImageView imageView;
        private TextView textView;
        public StationayHorizontalHolder(@NonNull View itemView) {
            super(itemView);
            update=itemView.findViewById(R.id.horizontalsubrowupdate);
            delete=itemView.findViewById(R.id.horizontalsubrowdelete);
            imageView=itemView.findViewById(R.id.horizontalsubrowimage);
            textView=itemView.findViewById(R.id.horizontalsubrowtext);

        }
    }
}
