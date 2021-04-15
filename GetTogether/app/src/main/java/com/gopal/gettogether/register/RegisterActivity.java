package com.gopal.gettogether.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.gopal.gettogether.MainActivity;
import com.gopal.gettogether.R;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText displayName;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;
    Button createNewAccountBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private String uid;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        Toolbar toolbar = findViewById( R.id.register_toolbar );
        setSupportActionBar( toolbar );

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( "Create Account" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }
        //------Android Field--------
        displayName = findViewById( R.id.displayName );
        email = findViewById( R.id.userName );
        password = findViewById( R.id.password );
        confirmPassword = findViewById( R.id.confirmPassword );
        createNewAccountBtn = findViewById( R.id.createAccountBtn );
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById( R.id.progressBarRegister );


        createNewAccountBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegistration();
            }
        } );
    }

    private void startRegistration() {

        final String fullName = displayName.getEditableText().toString();
        String eMail = email.getEditableText().toString();
        String pass = password.getEditableText().toString();
        String confirmPass = confirmPassword.getEditableText().toString();
        String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

        if (fullName.isEmpty()) {
            displayName.setError( "Enter Name" );
            displayName.requestFocus();
        } else if (eMail.isEmpty()) {
            email.setError( "Enter E-mail" );
            email.requestFocus();
        } else if (pass.isEmpty()) {
            password.setError( "Enter Password" );
            password.requestFocus();
        } else if (!(pass.length() >= 8)) {
            Toast.makeText( this, "Password Length is small", Toast.LENGTH_SHORT ).show();
        } else if (confirmPass.isEmpty()) {
            confirmPassword.setError( "Re-enter Password" );
            confirmPassword.requestFocus();
        } else if (!(pass.equals( confirmPass ))) {
            Toast.makeText( this, "Password not matched", Toast.LENGTH_SHORT ).show();
        } else if (!(eMail.matches( emailPatterns ))) {
            Toast.makeText( this, "E-mail Pattern is wrong", Toast.LENGTH_SHORT ).show();
        } else {
            progressBar.setVisibility( View.VISIBLE );
            createNewAccountBtn.setVisibility( View.GONE );
            firebaseAuth.createUserWithEmailAndPassword( eMail, pass ).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(
                                        new OnCompleteListener<InstanceIdResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                                                if (task.getResult() != null) {

                                                    String deviceToken = task.getResult().getToken();

                                                    String status = "Hi there, I'm using Get Together Chat App";
                                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                                    if (firebaseUser != null)
                                                        uid = firebaseUser.getUid();
                                                    databaseReference = FirebaseDatabase.getInstance().getReference( "Users" )
                                                            .child( uid );

                                                    HashMap<String, String> userMap = new HashMap<>();
                                                    userMap.put( "name", fullName );
                                                    userMap.put( "status", status );
                                                    userMap.put( "image", "default" );
                                                    userMap.put( "deviceToken" , deviceToken );

                                                    databaseReference.setValue( userMap ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()) {

                                                                progressBar.setVisibility( View.INVISIBLE );
                                                                Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                                                                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                                                startActivity( intent );

                                                            } else {
                                                                if (task.getException() != null) {
                                                                    String error = task.getException().getMessage();
                                                                    Toast.makeText( RegisterActivity.this, error, Toast.LENGTH_SHORT ).show();
                                                                    progressBar.setVisibility( View.INVISIBLE );
                                                                    createNewAccountBtn.setVisibility( View.VISIBLE );
                                                                }
                                                            }
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                );


                            } else {
                                if (task.getException() != null) {
                                    String error = task.getException().getMessage();
                                    Toast.makeText( RegisterActivity.this, error, Toast.LENGTH_SHORT ).show();
                                    progressBar.setVisibility( View.INVISIBLE );
                                    createNewAccountBtn.setVisibility( View.VISIBLE );
                                }
                            }
                        }
                    }
            );
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected( item );
    }

}
