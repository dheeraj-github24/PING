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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Adapter extends RecyclerView.Adapter<User_Adapter.ViewHolder> {
    private Context mContext;
    private List<User> mUsers;

    public User_Adapter(Context context, List<User> user){
        this.mContext=context;
        this.mUsers=user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new User_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user=mUsers.get(position);
        holder.username.setText(user.getFirstname());
        if(user.getDesignation().equals("")){
            holder.designation.setText("");
        }else {
            holder.designation.setText(user.getDesignation());
        }
        if(user.getImageURL().equals("default")){
            holder.profpic.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(mContext).load(user.getImageURL()).into(holder.profpic);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,chatinterface.class);
                intent.putExtra("Id",user.getid());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public TextView designation;
        public CircleImageView profpic;

        public ViewHolder(View itemView){
            super(itemView);

            username=itemView.findViewById(R.id.users);
            designation=itemView.findViewById(R.id.designation);
            profpic=itemView.findViewById(R.id.profilepic);
        }
    }
}