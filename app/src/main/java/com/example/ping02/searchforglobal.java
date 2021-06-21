package com.example.ping02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.ping02.Adapter.User_Adapter;
import com.example.ping02.Model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class searchforglobal extends AppCompatActivity {

    private RecyclerView rv;
    private SearchView sv;
    private User_Adapter user_adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchforglobal);

        rv=findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), User.class)
                        .build();

        user_adapter=new User_Adapter(options);
        rv.setAdapter(user_adapter);

       sv=findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processsearch(newText);
                return false;
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        user_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        user_adapter.stopListening();
    }


    private void processsearch(String newText) {
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("Users").orderByChild("Firstname")
                                        .startAt(newText).endAt(newText+"\uf8ff")
                                ,User.class)
                        .build();

        user_adapter=new User_Adapter(options);
        user_adapter.startListening();
        rv.setAdapter(user_adapter);
    }
}