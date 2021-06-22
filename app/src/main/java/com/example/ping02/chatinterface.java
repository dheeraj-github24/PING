package com.example.ping02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ping02.Adapter.Message_Adapter;
import com.example.ping02.Model.Intel;
import com.example.ping02.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatinterface extends AppCompatActivity {

    private TextView username;
    private CircleImageView profilepic;
    private EditText textbar;
    private Button send;
    private Button sendfile;

    Intent intent;
    FirebaseUser fuser;
    DatabaseReference reference;

    Message_Adapter message_adapter;
    List<Intel> mIntel;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatinterface);

        username=findViewById(R.id.username);
        profilepic=findViewById(R.id.profilepicture);
        textbar=findViewById(R.id.edit_gchat_message);
        send=findViewById(R.id.button_gchat_send);
        sendfile=findViewById(R.id.sendfile);
        recyclerView=findViewById(R.id.chat_recyclerview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent=getIntent();
        final String userid=intent.getStringExtra("Id");
        fuser= FirebaseAuth.getInstance().getCurrentUser();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=textbar.getText().toString();
                if(!msg.equals("")){
                    sendMessage(fuser.getUid(),userid,msg);
                }else {
                    Toast.makeText(chatinterface.this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
                }
                textbar.setText("");
            }
        });

        reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                username.setText(user.getFirstname());
                if(user.getImageURL().equals("default")){
                    profilepic.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Glide.with(chatinterface.this).load(user.getImageURL()).into(profilepic);
                }

                readMessage(fuser.getUid(),userid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendMessage(String sender, String receiver, String msg) {
        reference=FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> map=new HashMap<>();
        map.put("Sender",sender);
        map.put("Receiver",receiver);
        map.put("Message",msg);
        reference.child("Intel").push().setValue(map);
    }

    private void readMessage(String myid, String userid){
        mIntel=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Intel");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mIntel.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Intel intel=snapshot1.getValue(Intel.class);
                    if(intel.getReceiver().equals(myid) && intel.getSender().equals(userid) ||
                        intel.getReceiver().equals(userid) && intel.getSender().equals(myid)){
                        mIntel.add(intel);
                    }

                    message_adapter=new Message_Adapter(chatinterface.this,mIntel);
                    recyclerView.setAdapter(message_adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}