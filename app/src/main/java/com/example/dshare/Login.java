package com.example.dshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {
    ImageView backbtn;
    Button loginb;
    EditText inputemail , inputpass;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginb = findViewById(R.id.loginbtn);
        loginb.setOnClickListener(this);

        backbtn = findViewById(R.id.imageView3);
        backbtn.setOnClickListener(this);

        inputemail = (EditText) findViewById(R.id.editTextEmail);
        inputpass = (EditText) findViewById(R.id.editTextTextPassword);

        progressBar = findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginbtn:
                userLogin();
                break;

            case R.id.imageView3:
                startActivity(new Intent(Login.this,MainActivity.class));

        }
    }

    private void  userLogin(){
        String  email = inputemail.getText().toString().trim();
        String  pass = inputpass.getText().toString().trim();

        if(email.isEmpty()){
            inputemail.setError("Email is required");
            inputemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputemail.setError("Please enter a valid email ");
            inputemail.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            inputpass.setError("Password is required");
            inputpass.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(Login.this,Userscreen1.class));
                }
                else{
                    Toast.makeText(Login.this, "Failed to login", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }


}