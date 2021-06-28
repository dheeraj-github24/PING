package com.example.ping02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.ping02.Adapter.User_Adapter;
import com.example.ping02.Model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class searchforglobal extends AppCompatActivity {

    private RecyclerView rv;
    private EditText sv;
    private User_Adapter user_adapter;
    private List<User> mUsers;
    private FloatingActionButton groupmaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchforglobal);

        groupmaker=findViewById(R.id.groupfbutton);
        groupmaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupmaking();
            }
        });
        rv=findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mUsers=new ArrayList<>();
        readUsers();

        sv=findViewById(R.id.searchView);
        sv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searchUsers(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchUsers(String s) {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Query query=FirebaseDatabase.getInstance().getReference("Users").orderByChild("Firstname")
                .startAt(s.toUpperCase())
                .endAt(s.toLowerCase()+'\uf8ff');

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    User user=snapshot1.getValue(User.class);
                    assert user != null;
                    assert firebaseUser != null;
                    if(!user.getid().equals(firebaseUser.getUid())){
                        mUsers.add(user);
                    }
                }
                user_adapter=new User_Adapter(getApplicationContext(),mUsers);
                rv.setAdapter(user_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void groupmaking() {
        Intent intenttogroupmaking = new Intent(this, group.class);
        startActivity(intenttogroupmaking);
    }

    private void readUsers() {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (sv.getText().toString().equals("")) {
                    mUsers.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        User user = snapshot1.getValue(User.class);

                        assert user != null;
                        if (!user.getid().equals(firebaseUser.getUid())) {
                            mUsers.add(user);
                        }
                    }
                    user_adapter = new User_Adapter(getApplicationContext(), mUsers);
                    rv.setAdapter(user_adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}