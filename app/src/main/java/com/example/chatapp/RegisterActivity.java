package com.example.chatapp;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.HashMap;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText username, email, password;
    Button btn_Register;
    FirebaseAuth auth;
    DatabaseReference reference ;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        username = findViewById(R.id.UserName);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        btn_Register = findViewById(R.id.btn_register);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String Email = email.getText().toString();
                String Pass = password.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Pass)) {
                    Toast.makeText(RegisterActivity.this, "Enter all data", Toast.LENGTH_SHORT).show();
                } else if (Pass.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "enter password grater than 6", Toast.LENGTH_SHORT).show();
                } else {
                    Register(name, Email, Pass);
                }
            }
        });
    }

    public void Register(String username, String email, String pasword) {
        auth.createUserWithEmailAndPassword(email, pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userid = firebaseUser.getUid();
                    reference=FirebaseDatabase.getInstance().getReference("Users").child(userid);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username);
                    hashMap.put("imageUrl", "defuat");
                    hashMap.put("search", username.toLowerCase());
                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else {
                                task.getException();
                                int x=0;
                                x++;
                            }
                        }
                    });
                } else {
                    task.getException();

                    Toast.makeText(RegisterActivity.this, "yoyu cant register woth email,password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}