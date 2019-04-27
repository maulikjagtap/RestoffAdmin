package com.example.design.restoffowner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class User_RegistionActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener {
    private Button signup;
    private Button login;
    private Button forgotpass;
    private EditText password;
    private EditText email;
    private EditText shopname;
    private EditText shopaddress;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Spinner spinner;
    public String type_iteam;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__registion_);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("It Just Take Few Seconds Wait..");
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        signup=findViewById(R.id.RegistrationScreen_button_singup);
        login=findViewById(R.id.RegistrationScreen_button_login);
        forgotpass=findViewById(R.id.RegistratonScreen_forgot_password_button);
        email=findViewById(R.id.RegistrationScreen_edittext_shopemail);
        password=findViewById(R.id.RegistrationScreen_edittext_shoppassword);
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        forgotpass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {

            case R.id.RegistrationScreen_button_singup:
                signup();
                break;
            case R.id.RegistrationScreen_button_login:
                login();
                break;
            case R.id.ForgotpasssScreen_reset_button:
                forgotpassword();
                break;


        }


    }
    private void forgotpassword() {
        startActivity(new Intent(User_RegistionActivity.this,ForgotPassword_Activity.class));
    }

    private void signup() {
        progressDialog.show();
        final String useremail=email.getText().toString().trim();
        final String userepass=password.getText().toString().trim();
        if(TextUtils.isEmpty(useremail))
        {
            Toast.makeText(this, "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userepass))
        {
            Toast.makeText(this, "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userepass.length()<6)
        {
            Toast.makeText(this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(useremail,userepass).addOnCompleteListener(User_RegistionActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful())
                {



                    Toast.makeText(User_RegistionActivity.this, "Something is  wrong check your email and password",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.dismiss();
                    Intent userdata=new Intent(User_RegistionActivity.this,Userpicandotherdata.class);
                    userdata.putExtra("user_email",useremail);
                    startActivity(userdata);


                }
            }
        });

    }

    private void login() {
        Intent loginactivity=new Intent(User_RegistionActivity.this,User_loginactivity.class);
        startActivity(loginactivity);
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      String iteam = parent.getItemAtPosition(position).toString();
        type_iteam=iteam;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Select Type", Toast.LENGTH_SHORT).show();

    }
}
