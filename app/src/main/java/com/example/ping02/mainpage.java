package com.example.ping02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ping02.Adapter.User_Adapter;
import com.example.ping02.Model.Intel;
import com.example.ping02.Model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class mainpage extends AppCompatActivity {


    private FloatingActionButton settings;
    private CircleImageView prof_img;
    private TextView username;
    private FloatingActionButton search;
    private FloatingActionButton chat;

    private RecyclerView rv;
    private User_Adapter user_adapter;
    private List<String> userlist;
    private List<User> mUser;


    FirebaseUser firebaseUser;
    DatabaseReference databaseReference,reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        settings = findViewById(R.id.settingsfbutton);
        settings.setOnClickListener(v -> showsettingspage());
        prof_img=findViewById(R.id.profilepicturechatdrawer);
        username=findViewById(R.id.usernameautologin);

        rv=findViewById(R.id.mainpage_recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        userlist=new ArrayList<>();

        reference=FirebaseDatabase.getInstance().getReference("Intel");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userlist.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    Intel intel=snapshot1.getValue(Intel.class);

                    if(intel.getSender().equals(firebaseUser.getUid())){
                        userlist.add(intel.getReceiver());
                    }
                    if(intel.getReceiver().equals(firebaseUser.getUid())){
                        userlist.add(intel.getSender());
                    }
                }
                readIntel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        search=findViewById(R.id.floatingActionButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showsearchbar();
            }
        });


        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                username.setText(user.getFirstname()+" "+user.getLastname());
                if(user.getImageURL().equals("default")){
                    prof_img.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Glide.with(mainpage.this).load(user.getImageURL()).into(prof_img);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void readIntel() {
        mUser=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUser.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    User user=snapshot1.getValue(User.class);

                    for(String id:userlist){
                        if(user.getid().equals(id)){
                            if(mUser.size()!=0){
                                for(User user1:mUser){
                                    if(!user.getid().equals(user1.getid())&&!mUser.contains(user)){
                                        mUser.add(user);
                                        break;
                                    }
                                }
                            } else {
                                mUser.add(user);
                            }
                        }
                    }

                }
                user_adapter=new User_Adapter(getApplicationContext(),mUser);
                rv.setAdapter(user_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showchatinterface() {
        Intent intenttochatinterface = new Intent(this, chatinterface.class);
        startActivity(intenttochatinterface);
    }

    private void showsearchbar() {
        Intent intenttosearchbar = new Intent(this, searchforglobal.class);
        startActivity(intenttosearchbar);
    }


    public void showsettingspage(){
        Intent intenttosettings = new Intent(this, settings.class);
        startActivity(intenttosettings);
    }

}