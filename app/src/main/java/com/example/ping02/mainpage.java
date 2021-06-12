package com.example.ping02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ping02.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class mainpage extends AppCompatActivity {


    private FloatingActionButton settings;
    private ImageView chatdrawer;
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

        chatdrawer = findViewById(R.id.chatmenu);
        chatdrawer.setOnClickListener(v -> showchatdrawer());
        settings = findViewById(R.id.settingsfbutton);
        settings.setOnClickListener(v -> showsettingspage());
        prof_img=findViewById(R.id.profilepicturechatdrawer);
        username=findViewById(R.id.usernameautologin);
                
        search=findViewById(R.id.searchfloatingbutton);                 //

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
        Intent intenttochatinterface = new Intent(this, chatinterface2.class);          //
        startActivity(intenttochatinterface);                                           //
    }                                                                                   //

    private void showsearchbar() {                                                      //
        Intent intenttosearchbar = new Intent(this, searchforglobal.class);             //
        startActivity(intenttosearchbar);                                               //
    }                                                                                   //

    private void showchatdrawer() {                                                    
        Intent intenttochatdrawer = new Intent(this, chatdrawers.class);               
        startActivity(intenttochatdrawer);                                              
    }   

    public void showsettingspage(){
        Intent intenttosettings = new Intent(this, settings.class);
        startActivity(intenttosettings);
    }

}
