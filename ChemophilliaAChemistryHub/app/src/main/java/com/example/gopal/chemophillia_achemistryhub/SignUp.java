package com.example.gopal.chemophillia_achemistryhub;

import  androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gopal.chemophillia_achemistryhub.ui.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText emailID;
    EditText passwords;
    Button signupbtn;
    Button askLogin;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailID = findViewById(R.id.emailText);
        passwords = findViewById(R.id.passwordText);
        signupbtn = findViewById(R.id.signUP);
        askLogin = findViewById(R.id.askLogin);

        //to Hide action bar

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){

        setContentView(R.layout.activity_main);

    }


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailID.getText().toString();
                String password = passwords.getText().toString();
                firebaseAuth = FirebaseAuth.getInstance();

                if (email.isEmpty()) {
                    emailID.setError("Email can not be empty");   //to validate email whether empty or bot
                    emailID.requestFocus();
                } else if (password.isEmpty()) {
                    passwords.setError("Passwor Cannot be empty");
                    passwords.requestFocus();
                } else if (!email.isEmpty() && !password.isEmpty()) {

                    if (password.length() > 8) {

                        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Sign Up Failed \nPlease Check Email Format  ", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUp.this, Login.class));

                                }
                            }
                        });

                    } else {
                        passwords.setError("Password Must be Greater than 8 Digit");
                        passwords.requestFocus();
                    }

                }
            }
        });

        askLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });
    }
}
