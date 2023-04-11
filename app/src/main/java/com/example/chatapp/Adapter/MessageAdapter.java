package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.MassageActivity;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.User;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    Context context;
    List<Chat> chats;
    String image_url;
    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> chats,String image_url) {
        this.context = context;
        this.chats = chats;
        this.image_url=image_url;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
            View View = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(View);
        }else {
            View View = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(View);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat=chats.get(position);
        holder.show_message.setText(chat.getMessage());
        if(image_url.equals("defuat")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(context).load(image_url).into(holder.profile_image);
        }
        if(chats.size()-1==position){
            if(chat.isIsseen()){
                holder.seen.setText("Seen");
            }else {
                holder.seen.setText("Delivered");
            }
        }else {
            holder.seen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {
      firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
      if(chats.get(position).getSender().equals(firebaseUser.getUid())){
          return MSG_TYPE_RIGHT;
      }else {
          return MSG_TYPE_LEFT;
      }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView show_message;
        ImageView profile_image;
        TextView seen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            seen=itemView.findViewById(R.id.seen);
        }

    }

}