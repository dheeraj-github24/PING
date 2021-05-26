package com.example.ping02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class welcomepage extends AppCompatActivity {


    //Aur batao bhai!
    //GFG3

    Timer timer;
    private TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        test = findViewById(R.id.emailid);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intenttomainpage = new Intent(welcomepage.this, mainpage.class);
                startActivity(intenttomainpage);
                finish();
            }
        }, 1000);

    }
}