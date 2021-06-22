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
import com.example.ping02.Model.Intel;
import com.example.ping02.Model.User;
import com.example.ping02.R;
import com.example.ping02.chatinterface;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.myviewholder> {

    public static final int MSG_LEFT=0;
    public static final int MSG_RIGHT=1;

    private List<Intel> mIntel;
    private Context mContext;
    ;

    FirebaseUser fuser;


    public Message_Adapter(Context mContext,List<Intel> mIntel) {
        super();
        this.mIntel=mIntel;
        this.mContext=mContext;

    }

    @NonNull
    @Override
    public Message_Adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chatinterfaceuser, parent, false);
            return new Message_Adapter.myviewholder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chatinterfaceother, parent, false);
            return new Message_Adapter.myviewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        Intel intel=mIntel.get(position);
        holder.show_message.setText(intel.getMessage());
    }

    @Override
    public int getItemCount() {
        return mIntel.size();
    }

    static class myviewholder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        public TextView show_message;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.profilepic);
            show_message=itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(mIntel.get(position).getSender().equals(fuser.getUid())){
            return MSG_RIGHT;
        }else{
            return  MSG_LEFT;
        }
    }
}

