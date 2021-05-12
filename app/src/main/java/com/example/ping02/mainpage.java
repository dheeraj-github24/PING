package com.example.ping02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mainpage extends AppCompatActivity {

    private FloatingActionButton settings;
    private FloatingActionButton testcase;
    private ImageView chatdrawer;
    
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

     
        chatdrawer = findViewById(R.id.chatmenu);
        chatdrawer.setOnClickListener(v -> showchatdrawer());
        testcase = findViewById((R.id.floatingActionButton2));
        testcase.setOnClickListener(v -> showchatbox());
        settings = findViewById(R.id.settingsfbutton);
        settings.setOnClickListener(v -> showsettingspage());
    }

    private void showchatdrawer() {
        Intent intenttochatdrawer = new Intent(this, chatdrawers.class);
        startActivity(intenttochatdrawer);
    }

    private void showchatbox() {
        Intent intenttochatbox = new Intent( this, chatinterface.class);
        startActivity(intenttochatbox);
    }
    public void showsettingspage(){
        Intent intenttosettings = new Intent(this, settings.class);
        startActivity(intenttosettings);
    }

}
