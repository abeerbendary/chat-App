package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.widget.Toolbar;
public class ReserPasswordActivity extends AppCompatActivity {
Toolbar toolbar;
Button btn_reset;
EditText send_email;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reser_password);
        toolbar = findViewById(R.id.Toolbar);
        btn_reset=findViewById(R.id.btn_reset);
        send_email=findViewById(R.id.send_email);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth=FirebaseAuth.getInstance();
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email =send_email.getText().toString();
                if(Email.equals("")){
                    Toast.makeText(ReserPasswordActivity.this, "All fileds ara required !", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ReserPasswordActivity.this, "check your Email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ReserPasswordActivity.this,login.class));
                            }else {
                                String Error=task.getException().toString();
                                Toast.makeText(ReserPasswordActivity.this, Error, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}