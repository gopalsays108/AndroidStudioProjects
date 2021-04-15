package com.gopal.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.gopal.photoblog.MainActivity;
import com.gopal.photoblog.R;
import com.gopal.photoblog.SetUpActivity;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class RegisterActivity extends AppCompatActivity {

    private ShowHidePasswordEditText password;
    private ShowHidePasswordEditText confirmPassword;

    private EditText userName;
    private EditText email;
    private String enteredEmail;
    private String enteredPassword;
    private Button registerBtn;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        password = findViewById( R.id.regsiterPassword );
        confirmPassword = findViewById( R.id.registerPasswordConfirm );
        userName = findViewById( R.id.userNameRegister );
        email = findViewById( R.id.registerEmailAddress );
        registerBtn = findViewById( R.id.registerBtn );
        progressBar = findViewById( R.id.progressBarRegister );
        firebaseAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        } );

        findViewById( R.id.haveAnAccountBtn ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );

    }

    private void checkCredentials() {

        String enteredUserName = userName.getText().toString().trim();
        enteredEmail = email.getText().toString().trim();
        enteredPassword = password.getText().toString();
        String enteredConfirmPassword = confirmPassword.getText().toString();

        String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
        if (enteredUserName.isEmpty()){
            userName.setError( "Enter Name" );
            userName.requestFocus();
        }else if(enteredEmail.isEmpty()){
            email.setError( "Enter Email" );
            email.requestFocus();
        }else if (!enteredEmail.matches( emailPatterns )){
            Toast.makeText( this, "Email pattern is wrong", Toast.LENGTH_SHORT ).show();
        }else if (enteredPassword.isEmpty()){
            Toast.makeText( this, "Enter Password", Toast.LENGTH_SHORT ).show();
        }else if (enteredPassword.length() < 8){
            Toast.makeText( this, "password length is short\nPassword must have 8 character", Toast.LENGTH_SHORT ).show();
        }else if (enteredConfirmPassword.isEmpty()){
            Toast.makeText( this, "Enter password to confirm", Toast.LENGTH_SHORT ).show();
        }else if (!enteredPassword.equals( enteredConfirmPassword )){
            Toast.makeText( this, "Password not matched", Toast.LENGTH_SHORT ).show();
        }else {
            progressBar.setVisibility( View.VISIBLE );
            registerBtn.setVisibility( View.INVISIBLE );
            startRegistering();
        }

    }

    private void startRegistering() {

        firebaseAuth.createUserWithEmailAndPassword( enteredEmail , enteredPassword ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Intent intent = new Intent( getApplicationContext() , SetUpActivity.class );
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity( intent );
                    finish();
                }else {
                    if (task.getException() != null) {
                        progressBar.setVisibility( View.INVISIBLE );
                        registerBtn.setVisibility( View.VISIBLE );
                        String error = task.getException().getMessage();
                        Toast.makeText( RegisterActivity.this, error, Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        } );
    }

    private void mainIntent() {
        Intent mainIntent = new Intent( getApplicationContext(), MainActivity.class );
        startActivity( mainIntent );
        finish();
    }
}