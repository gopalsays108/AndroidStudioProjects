package com.gopal.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.gopal.photoblog.MainActivity;
import com.gopal.photoblog.R;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class LoginActivity extends AppCompatActivity {

    private ShowHidePasswordEditText passwordEditText;
    private EditText email;
    private String enteredEmail;
    private String enteredPassword;
    String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private ProgressBar progressBar;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        passwordEditText = findViewById( R.id.loginPassword );
        email = findViewById( R.id.loginEmailAddress );
        loginBtn = findViewById( R.id.loginBtn );
        Button doNotHaveAnAccountBtn = findViewById( R.id.doNotHaveAnAccountBtn );
        progressBar = findViewById( R.id.progressBarLogin );

        doNotHaveAnAccountBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );

        loginBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredential();
            }
        } );

        doNotHaveAnAccountBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext() , RegisterActivity.class ) );
            }
        } );
    }

    //------- todo : to check login credentials---------
    private void checkCredential() {

        enteredEmail = email.getText().toString();
        enteredPassword = passwordEditText.getText().toString();

        if (enteredEmail.isEmpty()) {
            email.setError( "Enter Email" );
            email.requestFocus();
        } else if (enteredPassword.isEmpty()) {
            Toast.makeText( this, "Enter Password", Toast.LENGTH_SHORT ).show();
        } else if (enteredPassword.length() < 8) {
            Toast.makeText( this, "Password length is small", Toast.LENGTH_SHORT ).show();
        } else if (!(enteredEmail.matches( emailPatterns ))) {
            Toast.makeText( this, "Email pattern not matches", Toast.LENGTH_SHORT ).show();
        } else {
            progressBar.setVisibility( View.VISIBLE );
            loginBtn.setVisibility( View.INVISIBLE );
            startLogin();
        }
    }

    //-------- todo : if login credential is correct start login
    private void startLogin() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword( enteredEmail, enteredPassword ).
                addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            progressBar.setVisibility( View.INVISIBLE );
                            loginBtn.setVisibility( View.VISIBLE );
                            mainIntent();

                        } else {
                            if (task.getException() != null) {

                                progressBar.setVisibility( View.INVISIBLE );
                                loginBtn.setVisibility( View.VISIBLE );
                                String error = task.getException().toString();
                                Toast.makeText( LoginActivity.this, error, Toast.LENGTH_LONG ).show();

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