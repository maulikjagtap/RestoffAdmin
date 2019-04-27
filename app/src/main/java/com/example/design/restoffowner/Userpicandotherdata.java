package com.example.design.restoffowner;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Userpicandotherdata extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final int PICK_IMG_CODE = 100;
    private  String useremail;
    private ImageButton user_profile;
    private ImageButton image_chooser;
    private EditText shopname;
    private EditText shopaddress;
    private EditText shopdesc;
    private EditText shopcontact;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Button setuser_profile;
    private Spinner spinner;
    public String type_iteam;
    private Uri selectedFileIntent;
    private String myuri;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpicandotherdata);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("It Just Take Few Seconds Wait..");
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
         useremail= getIntent().getStringExtra("user_email");
         user_profile=findViewById(R.id.UserprofileScreen_Imagebutton_profileimage);
         image_chooser=findViewById(R.id.UserprofileScreen_Imagebutton_chooseimage);
        shopname=findViewById(R.id.UserprofileScreen_edittext_shopname);
        shopaddress=findViewById(R.id.UserprofileScreen_edittext_shopaddress);
        setuser_profile=findViewById(R.id.UserprofileScreen_button_setprofile);
        shopdesc=findViewById(R.id.UserprofileScreen_edittext_shopdescription);
        shopcontact=findViewById(R.id.UserprofileScreen_edittext_shopcontact);
        spinner=findViewById(R.id.UserprofileScreen_spinner_type);
       
        List<String> type = new ArrayList<String>();
        type.add("Select Type");
        type.add("Restaurant");
        type.add("BeautyParlour");
        type.add("Stationary");
        type.add("ClothShop");
        type.add("Gym");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, type);
        spinner.setSelection(0);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
        setuser_profile.setOnClickListener(this);
        image_chooser.setOnClickListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        type_iteam = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Select Type", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
           case  R.id.UserprofileScreen_Imagebutton_chooseimage :
                ChooseImage();
               break;
            case R.id.UserprofileScreen_button_setprofile:
                Setprofile();
                break;
            
        }
        
    }

    private void Setprofile() {
        progressDialog.show();

        final String shopename=shopname.getText().toString().trim();
        final String shopeaddress=shopaddress.getText().toString().trim();
        final String shopdescription=shopdesc.getText().toString().trim();
        final String shopcont=shopcontact.getText().toString().trim();

        if (TextUtils.isEmpty(shopename))
        {
            Toast.makeText(this, "Enter Shope Name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(shopeaddress))
        {
            Toast.makeText(this, "Enter Shop Address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(shopcont))
        {
            Toast.makeText(this, "Enter Shop Contact Number!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(shopdescription))
        {
            Toast.makeText(this, "Enter Short  Description of Shop!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(myuri.toString()))
        {
            Toast.makeText(this, "Choose Image", Toast.LENGTH_SHORT).show();
            return;
        }
        User user=new User(shopename,useremail,type_iteam,shopeaddress,shopdescription,shopcont,myuri);
        if(type_iteam.equalsIgnoreCase("Restaurant"))
        {
            databaseReference.child("Restaurant").child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
        }
       else if (type_iteam.equalsIgnoreCase("BeautyParlour"))
        {
            databaseReference.child("BeautyParlour").child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
        }
        else if (type_iteam.equalsIgnoreCase("Stationary"))
        {
            databaseReference.child("Stationary").child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
        }
        else if (type_iteam.equalsIgnoreCase("ClothShop"))
        {
            databaseReference.child("ClothShop").child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
        }
        else if(type_iteam.equalsIgnoreCase("Gym"))
        {
            databaseReference.child("Gym").child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
        }

        databaseReference.child("OwnerUser").child(firebaseAuth.getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Intent loginactvity=new Intent(Userpicandotherdata.this,User_loginactivity.class);
                startActivity(loginactvity);
                finish();
            }
        });

    }

    private void ChooseImage() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
        !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
            return;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMG_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==PICK_IMG_CODE && resultCode==RESULT_OK && data != null && data.getData()!=null )
        {
            selectedFileIntent = data.getData();

            Log.e("url",""+selectedFileIntent);
            user_profile.setImageURI(selectedFileIntent);
            uploadImage();
        }
    }

    private void uploadImage() {

        final StorageReference reference = storageReference.child("Images/"+UUID.randomUUID().toString());
        reference.putFile(selectedFileIntent)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(Userpicandotherdata.this, "yes", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(Userpicandotherdata.this, ""+reference.getDownloadUrl(), Toast.LENGTH_SHORT).show();
                        Log.e("url1",""+reference);
                        String path=reference.getPath();
//                        Toast.makeText(Userpicandotherdata.this, ""+path, Toast.LENGTH_SHORT).show();
                        StorageReference storageRef =
                                FirebaseStorage.getInstance().getReference();
                        storageRef.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                myuri= uri.toString();

                            }
                        });



                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Userpicandotherdata.this, "no", Toast.LENGTH_SHORT).show();


            }
        });


    }


}
