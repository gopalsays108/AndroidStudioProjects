package com.doorbeen.gopal.menuadmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText registeredEmail;
    private Button resetBtn;
    private String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private TextView goBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reset_password );
        registeredEmail = findViewById( R.id.forgotPasswordEmail );
        resetBtn = findViewById( R.id.resetPasswordButton );
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById( R.id.progressBarReset );
        goBack = findViewById( R.id.goBackReset );


        resetBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registeredEmail.getText().toString();
                if (email.isEmpty()) {
                    registeredEmail.setError( "Enter Registered E-mail" );
                    registeredEmail.requestFocus();
                } else if (!(email.isEmpty())) {
                    if (registeredEmail.getText().toString().matches( emailPatterns )) {

                        resetBtn.setEnabled( false );


                        progressBar.setVisibility( View.VISIBLE );
                        firebaseAuth.sendPasswordResetEmail( email ).addOnCompleteListener( new OnCompleteListener<Void>() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility( View.INVISIBLE );
                                    Toast.makeText( ResetPasswordActivity.this, "Email sent \n Successfully", Toast.LENGTH_SHORT ).show();

                                } else {
                                    progressBar.setVisibility( View.INVISIBLE );
                                    String error = task.getException().getMessage();
                                    Toast.makeText( ResetPasswordActivity.this, error, Toast.LENGTH_LONG ).show();

                                }
                                resetBtn.setEnabled( true );
                                //   resetPasswordBtn.setTextColor(R.color.colorAccent);
                            }
                        } );

                    } else {
                        Toast.makeText( ResetPasswordActivity.this, "Email Format is Incorrect", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        } );

        goBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( ResetPasswordActivity.this, MainActivity.class ) );
                finish();
            }
        } );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity( new Intent( ResetPasswordActivity.this, MainActivity.class ) );
            finish();
        }

        return super.onKeyDown( keyCode, event );
    }
}
