package com.doorbeen.gopal.menuadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private TextView email;
    private TextView fullName;
    private TextView password;
    private TextView confirmPassword;
    private FirebaseAuth firebaseAuth;
    private Button signUpBtn;
    private ProgressBar progressBar;
    private TextView haveAnAccount;
    private String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        email = findViewById( R.id.signUpEmail );
        fullName = findViewById( R.id.signUpFullname );
        password = findViewById( R.id.signUpPassword );
        confirmPassword = findViewById( R.id.signUpConfirmPass );
        firebaseAuth = FirebaseAuth.getInstance();
        signUpBtn = findViewById( R.id.signUp );
        haveAnAccount = findViewById( R.id.haveAnAccoutnSignUp );
        progressBar = findViewById( R.id.progressBar_signUp );


        signUpBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailID = email.getText().toString();
                final String fullNames = fullName.getText().toString();
                String passwords = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();

                if (emailID.isEmpty()) {
                    email.setError( "Enter Email" );
                    email.requestFocus();
                } else if (fullNames.isEmpty()) {
                    fullName.setError( "Enter name" );
                    fullName.requestFocus();
                } else if (passwords.isEmpty()) {
                    password.setError( "pass" );
                    password.requestFocus();
                } else if (!(passwords.length() >= 8)) {
                    password.setError( "Password Too Short" );
                    password.requestFocus();
                } else if (confirmPass.isEmpty()) {
                    confirmPassword.setError( "Write Password For Confirmation" );
                    confirmPassword.requestFocus();
                } else if (!emailID.isEmpty() && !passwords.isEmpty()) {
                    if (email.getText().toString().matches( emailPatterns )) {
                        if (passwords.equals( confirmPass )) {

                            progressBar.setVisibility( View.VISIBLE );
                            signUpBtn.setEnabled( false );
                            //todo : send data to firebase
                            firebaseAuth.createUserWithEmailAndPassword( emailID, passwords ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Map<Object, String> userData = new HashMap<>();
                                        userData.put( "Fullname", fullNames );
                                        String uid;
                                        FirebaseUser firebaseUser;
                                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        if (firebaseUser != null) {
                                            uid = firebaseUser.getUid();
                                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                            firebaseDatabase.getReference().child( "MenuAdmins" ).child( "RestaurantsAdminsNames" ).child( uid )
                                                    .push().setValue( userData ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText( RegisterActivity.this, "Done", Toast.LENGTH_SHORT ).show();
                                                    startActivity( new Intent( RegisterActivity.this, CategoryActivity.class ) );
                                                    finish();
                                                }
                                            } );
                                        } else {
                                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                            firebaseDatabase.getReference().child( "MenuAdmins" ).child( "RestaurantsAdminsNames" )
                                                    .push().setValue( userData ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText( RegisterActivity.this, "Done", Toast.LENGTH_SHORT ).show();
                                                    startActivity( new Intent( RegisterActivity.this, CategoryActivity.class ) );
                                                    finish();
                                                }
                                            } );

                                        }
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText( RegisterActivity.this, error, Toast.LENGTH_SHORT ).show();
                                        progressBar.setVisibility( View.INVISIBLE );
                                        signUpBtn.setEnabled( true );
                                    }
                                }
                            } );

                        } else {
                            Toast.makeText( RegisterActivity.this, "Password Didnyt match", Toast.LENGTH_SHORT ).show();
                        }
                    } else {
                        Toast.makeText( RegisterActivity.this, "Email Pattern is wrong", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        } );

        haveAnAccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( RegisterActivity.this, MainActivity.class ) );
                finish();
            }
        } );


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity( new Intent( RegisterActivity.this, MainActivity.class ) );
            finish();
            return true;
        }
        return super.onKeyDown( keyCode, event );
    }
}
