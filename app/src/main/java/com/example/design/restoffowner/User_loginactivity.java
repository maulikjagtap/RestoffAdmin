package com.example.design.restoffowner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class User_loginactivity extends AppCompatActivity implements View.OnClickListener {
    private Button signup;
    private Button login;
    private Button forgotpass;
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
//        if (firebaseAuth.getCurrentUser() != null) {
//            startActivity(new Intent(User_loginactivity.this, MainActivity.class));
//            finish();
//        }
       progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("It Just Take Few Seconds Wait..");
        setContentView(R.layout.activity_user_loginactivity);
        firebaseAuth=FirebaseAuth.getInstance();
        signup=findViewById(R.id.User_loginScreen_signup_button);
        login=findViewById(R.id.User_loginScreen_login_button);
        forgotpass=findViewById(R.id.User_loginScreen_forgot_password_button);
        email=findViewById(R.id.User_loginScreen_email_edittext);
        password=findViewById(R.id.User_loginScreen_pass_edittext);

        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        forgotpass.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.User_loginScreen_login_button:
                login();
                break;
            case R.id.User_loginScreen_signup_button:
                signup();
                break;
            case R.id.User_loginScreen_forgot_password_button:
                forgotpassword();
                break;
        }


    }





    private void signup() {
        Intent registrationactivity=new Intent(User_loginactivity.this,User_RegistionActivity.class);
        startActivity(registrationactivity);
        finish();
    }

    private void login() {
        String useremail = email.getText().toString();
        final String userpassword = password.getText().toString();
        if (TextUtils.isEmpty(useremail)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userpassword)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(User_loginactivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    // there was an error
                    if (password.length() < 6) {
                        Toast.makeText(User_loginactivity.this,"Password is Wrong", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(User_loginactivity.this,"Login Fail", Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Intent intent = new Intent(User_loginactivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void forgotpassword() {
        startActivity(new Intent(User_loginactivity.this,ForgotPassword_Activity.class));
    }

}
