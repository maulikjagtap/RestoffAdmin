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

class HorizontalrvAdepter  extends RecyclerView.Adapter<HorizontalrvAdepter.SubHolder> {
    private Context context;
    private ArrayList<Horizontalmodel> horizontalmodelArrayList;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public HorizontalrvAdepter(Context context, ArrayList<Horizontalmodel> horizontalmodelArrayList) {
        this.context = context;
        this.horizontalmodelArrayList = horizontalmodelArrayList;
    }

    @NonNull
    @Override
    public SubHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontalsubrow,viewGroup,false);
        return new SubHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubHolder subHolder, int i) {
        Horizontalmodel horizontalmodel =horizontalmodelArrayList.get(i);
        subHolder.textView.setText(horizontalmodel.getName());
        Glide.with(context).load(horizontalmodel.getMyuri()).into(subHolder.imageView);
        Toast.makeText(context, ""+horizontalmodel.getMyuri(), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, ""+horizontalmodel.getDescription(), Toast.LENGTH_SHORT).show();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        final Query query= databaseReference.child("StationaryIteams").child(firebaseAuth.getCurrentUser().getUid()).orderByChild("name").equalTo(horizontalmodel.getName());
        subHolder.delete.setOnClickListener(new View.OnClickListener() {
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
        subHolder.update.setOnClickListener(new View.OnClickListener() {
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
        return horizontalmodelArrayList.size();
    }

    public class SubHolder  extends  RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageView;
        private Button delete;
        private Button update;
        public SubHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.horizontalsubrowtext);
            imageView=itemView.findViewById(R.id.horizontalsubrowimage);
            delete=itemView.findViewById(R.id.horizontalsubrowdelete);
            update=itemView.findViewById(R.id.horizontalsubrowupdate);
        }
    }
}
