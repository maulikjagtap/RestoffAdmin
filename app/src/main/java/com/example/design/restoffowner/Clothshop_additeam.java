package com.example.design.restoffowner;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class Clothshop_additeam extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private View  view;
    private static final int PICK_IMG_CODE = 100;
    private ImageButton iteamimage;
    private ImageButton choose_image;
    private Spinner category;
    private EditText iteamname;
    private EditText iteamdescription;
    private EditText iteamprice;
    private Button additeam;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri selectimge;
    private String myuri;
    private EditText dialogedittext;
    private  Button dialogbutton;
    private List<String> type;
    private Button addmenu;
    private String itemkey;

    final Clothshop_additeam context = this;
    private String type_iteam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.clothshop_additeam,container,false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        iteamimage=view.findViewById(R.id.cloth_iteam_Imagebutton_iteamimage);
        choose_image=view.findViewById(R.id.cloth_iteam_Imagebutton_chooseimage);
        iteamdescription=view.findViewById(R.id.cloth_iteam_edittext_iteamdescription);
        iteamname=view.findViewById(R.id.cloth_iteam_edittext_iteamname);
        iteamprice=view.findViewById(R.id.cloth_iteam_edittext_price);
        category=view.findViewById(R.id.cloth_iteam_spinner_category);
        additeam=view.findViewById(R.id.cloth_iteam_button_additeam);
        addmenu=view.findViewById(R.id.cloth_menuadditeam);
        type = new ArrayList<String>();
        type.add("Select Type");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, type);
        category.setSelection(0);
        category.setAdapter(dataAdapter);
        addmenu.setOnClickListener(this);
        additeam.setOnClickListener(this);
        choose_image.setOnClickListener(this);
        category.setOnItemSelectedListener(this);
        databaseReference.child("ClothIteamMenu").child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot clothdata :dataSnapshot.getChildren())
                        {
                            ClothMenuModel clothMenuModel=clothdata.getValue(ClothMenuModel.class);
                            type.add(clothMenuModel.getIteamcategory());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cloth_iteam_Imagebutton_chooseimage:
                ChooseImage();
                break;
            case R.id.cloth_iteam_button_additeam:
                clothiteam();
                break;
            case R.id.cloth_menuadditeam:
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.addmenuiteamdailog);
                dialogedittext=dialog.findViewById(R.id.addmenuitramdialog_edittext);
                dialogbutton=dialog.findViewById(R.id.addmenuitramdialog_button);
                dialogbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String iteam =dialogedittext.getText().toString();
                        ClothMenuModel clothMenuModel=new ClothMenuModel(iteam);
                        databaseReference.child("ClothIteamMenu").child(firebaseAuth.getCurrentUser().getUid()).push().setValue(clothMenuModel);

                        dialog.dismiss();


                    }
                });
                dialog.show();

        }

    }

    private void clothiteam() {
        final String name=iteamname.getText().toString();
        final String description=iteamdescription.getText().toString();
        final String  price=iteamprice.getText().toString();
        if(TextUtils.isEmpty(type_iteam))
        {
            Toast.makeText(getContext(), "select type of Cetegory!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(getContext(), "Enter name of  iteam!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(description))
        {
            Toast.makeText(getContext(), "Enter  iteam description!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(price))
        {
            Toast.makeText(getContext(), "Enter price of iteam!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(myuri))
        {
            Toast.makeText(getContext(), "Enter pic of  plant pic!", Toast.LENGTH_SHORT).show();
            return;
        }

        final Query query= databaseReference.child("ClothIteamMenu").child(firebaseAuth.getCurrentUser().getUid()).orderByChild("iteamcategory").equalTo(type_iteam);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    itemkey=snapshot.getKey();
                    ClothIteamModel clothIteamModel=new ClothIteamModel(name,description,price,myuri);
                    databaseReference.child("ClothIteams").child(firebaseAuth.getCurrentUser().getUid()).child(itemkey).push().setValue(clothIteamModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Iteam is Added Sucessfully Add New iteam OtherWise Go to Home", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Iteam is Not Added Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void ChooseImage() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission((Activity) getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            return;
        }
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select Picture"),PICK_IMG_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==PICK_IMG_CODE && resultCode==RESULT_OK && data != null && data.getData()!=null )
        {
            selectimge=data.getData();
            iteamimage.setImageURI(selectimge);
            uploadImage();
        }
    }

    private void uploadImage() {
        final  StorageReference reference=storageReference.child("Image/"+ UUID.randomUUID().toString());
        reference.putFile(selectimge).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                String path=reference.getPath();
//                        Toast.makeText(Userpicandotherdata.this, ""+path, Toast.LENGTH_SHORT).show();
                StorageReference storageRef =
                        FirebaseStorage.getInstance().getReference();
                storageRef.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        myuri= uri.toString();
                        Toast.makeText(getContext(), ""+myuri, Toast.LENGTH_SHORT).show();


                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "no", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type_iteam=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
