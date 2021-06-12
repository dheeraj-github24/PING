package com.example.ping02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class mainpage extends AppCompatActivity {


    private FloatingActionButton settings;
    private CircleImageView prof_img;
    private TextView username;
    private FloatingActionButton search;                //
    private FloatingActionButton trial;                 //
    
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);


        settings = findViewById(R.id.settingsfbutton);
        settings.setOnClickListener(v -> showsettingspage());
        prof_img=findViewById(R.id.profilepicturechatdrawer);
        username=findViewById(R.id.usernameautologin);
                
        search=findViewById(R.id.floatingActionButton);                 //
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showsearchbar();
            }
        });

        trial=findViewById(R.id.floatingActionButton2);                 //
        trial.setOnClickListener(new View.OnClickListener() {           //
            @Override                                                   //
            public void onClick(View v) {                               //
                showchatinterface();                                    //
            }                                                           //   


        });                                                             //

        search.setOnClickListener(new View.OnClickListener() {          //
            @Override                                                   //
            public void onClick(View v) {                               //  
                showsearchbar();                                        //
            }

        });
//        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
//        databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user=snapshot.getValue(User.class);
//                username.setText(user.getFname());
//                if(user.getImageURL().equals("default")){
//                    prof_img.setImageResource(R.mipmap.ic_launcher);
//                }else {
//                    Glide.with(mainpage.this).load(user.getImageURL()).into(prof_img);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    private void showchatinterface() {                                                  //
        Intent intenttochatinterface = new Intent(this, chatinterface.class);          //
        startActivity(intenttochatinterface);                                           //
    }                                                                                   //

    private void showsearchbar() {                                                      //
        Intent intenttosearchbar = new Intent(this, searchforglobal.class);             //
        startActivity(intenttosearchbar);                                               //
    }                                                                                   //


    public void showsettingspage(){
        Intent intenttosettings = new Intent(this, settings.class);
        startActivity(intenttosettings);
    }

}
