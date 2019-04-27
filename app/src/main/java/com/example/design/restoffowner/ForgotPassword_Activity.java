package com.example.design.restoffowner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword_Activity extends AppCompatActivity {
    private EditText email;
    private Button resetpass;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_);
        email=findViewById(R.id.ForgotpasssScreen_email_edittext);
        resetpass=findViewById(R.id.ForgotpasssScreen_reset_button);
        firebaseAuth=FirebaseAuth.getInstance();
        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useemail = email.getText().toString().trim();
                if (TextUtils.isEmpty(useemail)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.sendPasswordResetEmail(useemail) .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword_Activity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPassword_Activity.this,User_loginactivity.class));
                        } else {
                            Toast.makeText(ForgotPassword_Activity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });
    }
}
