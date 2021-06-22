package com.example.ping02.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ping02.Model.User;
import com.example.ping02.R;
import com.example.ping02.chatinterface;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Adapter extends FirebaseRecyclerAdapter<User,User_Adapter.myviewholder> {
        private Context context;

    public User_Adapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull User user) {
        holder.users.setText(user.getFirstname());
        if(user.getImageURL().equals("default")){
            holder.circleImageView.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(holder.circleImageView.getContext()).load(user.getImageURL()).into(holder.circleImageView);
        }

        holder.itemView.setOnClickListener(v -> {
            {
                Intent intent=new Intent(v.getContext(), chatinterface.class);
                intent.putExtra("Id",user.getid());
                v.getContext().startActivity(intent);

            }
        });

    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new myviewholder(view);
    }

    static class myviewholder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView users;
        //TextView designation;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.profilepic);
            users=itemView.findViewById(R.id.users);
           // designation=itemView.findViewById(R.id.designation);
        }
    }
}
