package com.example.chatapp.Notifications;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String refresh_token= FirebaseInstanceId.getInstance().getToken();
        if(firebaseUser!=null){
            updateToken(refresh_token);
        }
    }

    private void updateToken(String refresh_token) {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Tokens");
        Token token=new Token(refresh_token);
        databaseReference.child(firebaseUser.getUid()).setValue(token);
    }
}
