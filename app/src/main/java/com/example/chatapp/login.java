package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class login extends AppCompatActivity {
    Toolbar toolbar;
    MaterialEditText email, password;
    Button login;
    FirebaseAuth auth;
    TextView ResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = findViewById(R.id.Toolbar);
        auth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        login = findViewById(R.id.btn_login);
        ResetPassword=findViewById(R.id.forget_password);
        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.getContext(),ReserPasswordActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String Pass = password.getText().toString();
                if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Pass)) {
                    Toast.makeText(login.this, "Enter all data", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(com.example.chatapp.login.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(login.this, "auth failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }
            }
        });
    }


}