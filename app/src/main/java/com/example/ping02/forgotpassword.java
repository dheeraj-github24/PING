package com.example.ping02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgotpassword extends AppCompatActivity {

    Button btn_reset, btn_verify;
    EditText sendmail;
    TextView verify_msg;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        sendmail=findViewById(R.id.editTextTextEmailAddress2);
        btn_reset=findViewById(R.id.sendmail);

        auth=FirebaseAuth.getInstance();
        FirebaseUser fu=auth.getCurrentUser();

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=sendmail.getText().toString();

                if(email.equals("")){
                    Toast.makeText(forgotpassword.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }else {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(forgotpassword.this, "Please check your Email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(forgotpassword.this, login.class));
                            }else {
                                String error=task.getException().getMessage();
                                Toast.makeText(forgotpassword.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}