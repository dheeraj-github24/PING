package com.example.ping02;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ping02.Adapter.Message_Adapter;
import com.example.ping02.Model.Intel;
import com.example.ping02.Model.User;
import com.example.ping02.Notifications.APIService;
import com.example.ping02.Notifications.Client;
import com.example.ping02.Notifications.Data;
import com.example.ping02.Notifications.Response;
import com.example.ping02.Notifications.Sender;
import com.example.ping02.Notifications.Token;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class chatinterface extends AppCompatActivity {

    private TextView username;
    private TextView typing;
    private CircleImageView profilepic;
    private EditText textbar;
    private Button send;
    private Button sendfile;
    private String userid;
    private boolean typecheck=false;

    Intent intent;
    FirebaseUser fuser;
    DatabaseReference reference,typeref;
    ValueEventListener seenListener;

    Message_Adapter message_adapter;
    List<Intel> mIntel;
    RecyclerView recyclerView;

    String mUid;
    APIService apiService;
    boolean notify=false;

    private static final int PICKFILE_RESULT_CODE = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        apiService= Client.getRetrofit("https://fcm.googleapis.com").create(APIService.class);

        intent=getIntent();
        userid=intent.getStringExtra("Id");
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        mUid=fuser.getUid();
        reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify=true;
                String msg=textbar.getText().toString();
                final Intel intel=new Intel(fuser.getUid(),userid,msg);
                Long time = intel.setTimestamp(new Date().getTime());
                if(!msg.equals("")){
                    sendMessage(fuser.getUid(),userid,msg,time);
                }else {
                    Toast.makeText(chatinterface.this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
                }
                textbar.setText("");
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                username.setText(user.getFirstname());
                if(user.getImageURL().equals("default")){
                    profilepic.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profilepic);
                }

                readMessage(fuser.getUid(),userid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        typeref=FirebaseDatabase.getInstance().getReference("Typing");
        typing=findViewById(R.id.typing);
        typeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()) {
                    Intel intel = snapshot1.getValue(Intel.class);
                    if (snapshot.child(userid).hasChild(intel.getReceiver())){
                        typing.setVisibility(View.VISIBLE);
                    }else {
                        typing.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        textbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Typing();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        seenMessage(userid);

        updateToken(FirebaseInstanceId.getInstance().getToken());
    }

    private void Typing() {
        typecheck=true;
        typeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Intel intel = snapshot1.getValue(Intel.class);
                    if (typecheck) {
                        if (snapshot.child(intel.getReceiver()).hasChild(userid)) {
                            typecheck=false;
                        }else {
                            typeref.child(intel.getReceiver()).child(userid).setValue(true);
                            typecheck=false;
                        }
                    }else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void seenMessage(final String userid){
        reference=FirebaseDatabase.getInstance().getReference("Intel");
        seenListener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    Intel intel=snapshot1.getValue(Intel.class);
                    assert intel != null;
                    if(intel.getReceiver().equals(fuser.getUid()) && intel.getSender().equals(userid)){
                        HashMap<String,Object> seenmap=new HashMap<>();
                        seenmap.put("isseen",true);
                        snapshot1.getRef().updateChildren(seenmap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String sender, String receiver, String msg, Long time) {
        reference=FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> map=new HashMap<>();
        map.put("Sender",sender);
        map.put("Receiver",receiver);
        map.put("Message",msg);
        map.put("isseen",false);
        map.put("Timestamp", time);
        reference.child("Intel").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        String info=msg;
        DatabaseReference database=FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                if(notify){
                    sendNotification(receiver,user.getFirstname(),msg);
                }
                notify=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotification(String receiver, String firstname, String msg) {
        DatabaseReference allTokens=FirebaseDatabase.getInstance().getReference("Tokens");
        Query query=allTokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    Token token=snapshot1.getValue(Token.class);
                    Data data=new Data(fuser.getUid(),firstname+":"+msg,"New Message ",userid,R.mipmap.ic_launcher);

                    Sender sender=new Sender(data,token.getToken());
                    apiService.sendNotification(sender)
                    .enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            Toast.makeText(chatinterface.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    public void updateToken(String token){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken=new Token(token);
        ref.child(mUid).setValue(mToken);
    }
}