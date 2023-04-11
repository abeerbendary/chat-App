package com.example.chatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.Adapter.UserAdapter;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.ChatList;
import com.example.chatapp.Model.User;
import com.example.chatapp.Notifications.Token;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> users;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    List<String>userlist;
    List<ChatList>chatList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView=view.findViewById(R.id.recylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userlist=new ArrayList<>();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        chatList=new ArrayList<>();
        databaseReference =FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ChatList chatListitem=dataSnapshot.getValue(ChatList.class);
                    chatList.add(chatListitem);
                }

                FillChatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        UpdateToken(FirebaseInstanceId.getInstance().getToken());
//        databaseReference= FirebaseDatabase.getInstance().getReference("Chats");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                userlist.clear();
//                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    Chat chat=dataSnapshot.getValue(Chat.class);
//                    if(chat.getReceiver().equals(firebaseUser.getUid())){
//                        userlist.add(chat.getSender());
//                    }
//                    if(chat.getSender().equals(firebaseUser.getUid())){
//                        userlist.add(chat.getReceiver());
//                    }
//                }
//                readchat();
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        return view;
    }


    private void UpdateToken(String token){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1=new Token(token);
        databaseReference.child(firebaseUser.getUid()).setValue(token1);
    }

    private void FillChatList() {
        users=new ArrayList<>();
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user=dataSnapshot.getValue(User.class);
                    for(ChatList chatLists:chatList){
                        if(user.getId().equals(chatLists.getId())){
                            users.add(user);
                        }
                    }
//                    if(chatList.(user.getId())){
//                        users.add(user);
//                    }
                }
                userAdapter=new UserAdapter(getContext(),users,true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    private void readchat() {
//        users=new ArrayList<>();
//        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                users.clear();
//                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    User user=dataSnapshot.getValue(User.class);
//                    if(userlist.contains(user.getId())){
//                        if(users.size()!=0){
//                            if(!users.contains(user.getId())){
//                                users.add(user);
//                            }
//                        }else {
//                            users.add(user);
//                        }
//                    }
//                }
//                userAdapter=new UserAdapter(getContext(),users,true);
//                recyclerView.setAdapter(userAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}