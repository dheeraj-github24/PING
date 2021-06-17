package com.example.ping02.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ping02.Model.User;
import com.example.ping02.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Adapter extends FirebaseRecyclerAdapter<User,User_Adapter.myviewholder> {

    public User_Adapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull User user) {
        holder.users.setText(user.getFirstname());
        holder.users.setText(user.getDesignation());
        if(user.getImageURL().equals("default")){
            holder.circleImageView.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(holder.circleImageView.getContext()).load(user.getImageURL()).into(holder.circleImageView);
        }

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView users;
        TextView designation;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.profilepic);
            users=itemView.findViewById(R.id.users);
            designation=itemView.findViewById(R.id.designation);
        }
    }
}
