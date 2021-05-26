package com.example.ping02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
// new comment added here

    //ashish check!!!!!!!!!!!!!
    Button login, register,email_verify;
    TextView verify_msg;

    FirebaseUser fu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showloginpage();
            }
        });

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showregisterpage();
            }
        });
    }

    public void showloginpage(){
        Intent intenttologin = new Intent(this, login.class);
        startActivity(intenttologin);
    }

    public void showregisterpage(){
        Intent intenttoregister = new Intent(this, signup.class);
        startActivity(intenttoregister);

    }
}