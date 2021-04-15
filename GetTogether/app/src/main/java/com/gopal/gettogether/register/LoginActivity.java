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

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText password;
    private Button loginInBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        Toolbar toolbar = findViewById( R.id.loginToolbar );
        setSupportActionBar( toolbar );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( "Login" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }
        //------ Android Field---------
        email = findViewById( R.id.loginEmail );
        password = findViewById( R.id.loginPassword );
        loginInBtn = findViewById( R.id.loginBtn );
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById( R.id.progressBarLogin );


        loginInBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eMail = email.getEditableText().toString();
                String pass = password.getEditableText().toString();
                String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

                if (eMail.isEmpty()) {
                    email.setError( "Enter Email" );
                    email.requestFocus();
                } else if (pass.isEmpty()) {
                    password.setError( "Enter password" );
                    password.requestFocus();
                } else if (!(eMail.matches( emailPatterns ))) {
                    Toast.makeText( LoginActivity.this, "Email Pattern is Wrong", Toast.LENGTH_SHORT ).show();
                } else {

                    progressBar.setVisibility( View.VISIBLE );
                    loginInBtn.setVisibility( View.INVISIBLE );

                    firebaseAuth.signInWithEmailAndPassword( eMail, pass ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            final DatabaseReference databaseReference;
                            FirebaseUser firebaseAuth;
                            final String currentUser;

                            databaseReference = FirebaseDatabase.getInstance().getReference().child( "Users" );
                            firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
                            if (firebaseAuth != null) {
                                currentUser = firebaseAuth.getUid();

                                if (task.isSuccessful()) {

                                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener( new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {

                                            if (task.isSuccessful()) {
                                                //String msg = getString(R.string.msg_token_fmt, token);

                                                // Get new Instance ID token
                                                if (task.getResult() != null) {

                                                    String token = task.getResult().getToken();
                                                    databaseReference.child( currentUser ).child( "deviceToken" ).setValue( token )
                                                            .addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {

                                                                        progressBar.setVisibility( View.INVISIBLE );
                                                                        Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                                                                        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                                                        startActivity( intent );
                                                                        finish();


                                                                    } else {
                                                                        Toast.makeText( LoginActivity.this, "Try Again", Toast.LENGTH_SHORT ).show();
                                                                    }
                                                                }
                                                            } );
                                                }
                                            } else {
                                                Toast.makeText( LoginActivity.this, "Error", Toast.LENGTH_SHORT ).show();
                                            }
                                        }
                                    } );
                                }

                            } else {
                                if (task.getException() != null) {
                                    String error = task.getException().getMessage();
                                    Toast.makeText( LoginActivity.this, error, Toast.LENGTH_SHORT ).show();
                                    progressBar.setVisibility( View.INVISIBLE );
                                    loginInBtn.setVisibility( View.VISIBLE );
                                }
                            }
                        }
                    } );
                }
            }
        } );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected( item );
    }
}
