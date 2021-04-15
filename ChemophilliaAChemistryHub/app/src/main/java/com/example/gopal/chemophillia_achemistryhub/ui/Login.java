package com.example.gopal.chemophillia_achemistryhub.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gopal.chemophillia_achemistryhub.MainActivity;
import com.example.gopal.chemophillia_achemistryhub.R;
import com.example.gopal.chemophillia_achemistryhub.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {

    Button loginBtn;
    Button askSignUp;
    EditText emailIds;
    EditText passTxt;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        askSignUp = findViewById(R.id.askSignUp);
        emailIds = findViewById(R.id.emailTxt);
        passTxt = findViewById(R.id.passTxt);

        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException e){

            setContentView(R.layout.activity_main);

        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailIds.getText().toString();
                String password = passTxt.getText().toString();
                firebaseAuth = FirebaseAuth.getInstance();

                if(email.isEmpty()){
                    emailIds.setError("Email cannot be empty");
                    emailIds.requestFocus();
                }else if(password.isEmpty()){
                    passTxt.setError("Password Cannot be empty");
                    passTxt.requestFocus();
                }else if(!email.isEmpty() && !password.isEmpty()){

                    firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(Login.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this , MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(Login.this, "Login Failed \n Passwords or username is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        askSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this , SignUp.class));
            }
        });


    }
}
