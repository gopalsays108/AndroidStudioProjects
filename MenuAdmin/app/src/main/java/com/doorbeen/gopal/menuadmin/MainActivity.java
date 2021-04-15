package com.doorbeen.gopal.menuadmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private TextView email, password;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private TextView signUp;
    private TextView forgot;
    private String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        email = findViewById( R.id.email );
        password = findViewById( R.id.password );
        signUp = findViewById( R.id.dontHaveAnAccount );
        progressBar = findViewById( R.id.progressBar_signIn );
        final Button loginBtn = findViewById( R.id.loginbutton );
        forgot = findViewById( R.id.forgotPassword );

        firebaseAuth = FirebaseAuth.getInstance();

        final Intent intent = new Intent( this, MainCategory.class );

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity( intent );
            finish();
            return;
        }
        loginBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = email.getText().toString();
                String pass = password.getText().toString();

                if (emails.isEmpty()) {
                    email.setError( "Email is Required" );
                    email.requestFocus();
                } else if (pass.isEmpty()) {
                    password.setError( "Password is Required" );
                    password.requestFocus();
                } else if (!(pass.length() >= 8)) {
                    password.setError( "Password Length Is Too Small" );
                    password.requestFocus();
                } else if (!(emails.isEmpty()) && !(pass.isEmpty())) {
                    if (email.getText().toString().matches( emailPatterns )) {

                        progressBar.setVisibility( View.VISIBLE );
                        loginBtn.setEnabled( false );
                        loginBtn.setTextColor( Color.argb( 50, 255, 255, 255 ) );
                        firebaseAuth.signInWithEmailAndPassword( email.getText().toString(), password.getText().toString() ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText( MainActivity.this, "Success", Toast.LENGTH_SHORT ).show();
                                    startActivity( intent );
                                    finish();


                                } else {

                                    String error = task.getException().getMessage();
                                    Toast.makeText( MainActivity.this, error, Toast.LENGTH_SHORT ).show();
                                    progressBar.setVisibility( View.INVISIBLE );
                                    loginBtn.setEnabled( true );
                                    loginBtn.setTextColor( R.color.white );
                                }
                            }
                        } );

                    } else {
                        Toast.makeText( MainActivity.this, "E-mail Format Is Wrong", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        } );

        signUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent( MainActivity.this, RegisterActivity.class );
                startActivity( intent1 );
                finish();
            }
        } );


        forgot.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( MainActivity.this , ResetPasswordActivity.class ) );
                finish();
            }
        } );
    }
}
