package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatapp.Adapter.ViewPageAdapter;
import com.example.chatapp.Fragments.ChatsFragment;
import com.example.chatapp.Fragments.ProfileFragment;
import com.example.chatapp.Fragments.UsersFragment;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView imageView;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Toolbar toolbar;
    ViewPager  viewPager;
    TabLayout  tabLayout;


    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        databaseReference =FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
                int unread=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Chat chat=dataSnapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(firebaseUser.getUid())&& !chat.isIsseen()){
                        unread++;
                    }
                }
                if (unread==0){
                    viewPageAdapter.addFragment(new ChatsFragment(), "Chats");
                }else {
                    viewPageAdapter.addFragment(new ChatsFragment(), "("+unread+") Chats");
                }
                viewPageAdapter.addFragment(new UsersFragment(), "Users");
                viewPageAdapter.addFragment(new ProfileFragment(), "Profile");
                viewPager.setAdapter(viewPageAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference = database.getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getValue();
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageUrl().equals("defuat")) {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getApplicationContext()).load(user.getImageUrl()).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbars);
          viewPager = findViewById(R.id.viewpager);
       tabLayout = findViewById(R.id.Tab_layout);
        setSupportActionBar(toolbar);
//         toolbar.setTitle("");
        imageView = findViewById(R.id.profileimage);
        username = findViewById(R.id.username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Toast.makeText(this, "item select" + item.getItemId(), Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
        }
        return false;
    }

    public void status(String status) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        databaseReference.updateChildren(hashMap);
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }
}