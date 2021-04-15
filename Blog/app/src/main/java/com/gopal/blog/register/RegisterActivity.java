package com.gopal.blog.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gopal.blog.MainActivity;
import com.gopal.blog.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText name;
    private EditText password;
    private EditText confirmPasswords;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferenceUsers;
    private final String emailFormat = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        email = findViewById( R.id.enterEmailSignUp );
        name = findViewById( R.id.enterNameSignUp );
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference( "Users" );
        databaseReferenceUsers.keepSynced( true ); //keeps data offline
        TextView haveAnAccount = findViewById( R.id.alreadyHaveAnAccountSignIn );
        password = findViewById( R.id.enterPasswordSignUp );
        confirmPasswords = findViewById( R.id.enterConfirmPasswordSignUp );
        Button signUpBtn = findViewById( R.id.signUpBtn );


        signUpBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eMail = email.getText().toString();
                final String userName = name.getText().toString();
                String pass = password.getText().toString();
                String confirmPass = confirmPasswords.getText().toString();

                if (eMail.isEmpty()) {
                    email.setError( "Enter Email" );
                    email.requestFocus();
                } else if (userName.isEmpty()) {
                    name.setError( "Enter Name" );
                    name.requestFocus();
                } else if (pass.isEmpty()) {
                    password.setError( "Enter Password" );
                    password.requestFocus();
                } else if (!(pass.length() >= 8)) {
                    password.setError( "length  is small" );
                    password.requestFocus();
                } else if (!(pass.equals( confirmPass ))) {
                    Toast.makeText( getApplicationContext(), "Passwords do not matched", Toast.LENGTH_LONG ).show();
                } else if (!(eMail.matches( emailFormat ))) {
                    Toast.makeText( getApplicationContext(), "Email format is wrong", Toast.LENGTH_SHORT ).show();
                } else {

                    firebaseAuth.createUserWithEmailAndPassword( eMail, pass ).
                            addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
//                                        Map<Object, String> userData = new HashMap<>();
//                                        userData.put( "username", userName );
//                                        userData.put( "image" , "default" );
//                                        String uid;
//                                        FirebaseUser firebaseUser;
//                                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                                        if (firebaseUser != null) {
//                                            uid = firebaseUser.getUid();
//                                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                                            firebaseDatabase.getReference().child( "Users" ).child( uid )
//                                                    .setValue( userData ).addOnCompleteListener( new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
////                                                    Toast.makeText( RegisterActivity.this, "Done", Toast.LENGTH_SHORT ).show();
////                                                    Intent intent =  new Intent( RegisterActivity.this , SetUpActivity.class );
////                                                    startActivity( intent );
////                                                    finish();
//                                                    checkUserExist();
//
//                                                }
//                                            } );
//                                        } else {
//                                            Toast.makeText( RegisterActivity.this, "Try Again", Toast.LENGTH_SHORT ).show();
//                                        }
                                        checkUserExist();
                                    } else {
                                        if (task.getException() != null) {
                                            String error = task.getException().getLocalizedMessage();
                                            Toast.makeText( RegisterActivity.this, error, Toast.LENGTH_SHORT ).show();
                                        }
                                    }
                                }
                            } );

                }
            }
        } );

        haveAnAccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), LogInActivity.class ) );
                finish();
            }
        } );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent( getApplicationContext(), LogInActivity.class ) );
    }

    private void checkUserExist() {

        if (firebaseAuth.getCurrentUser() != null) {
            final String uid = firebaseAuth.getCurrentUser().getUid();
            databaseReferenceUsers.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild( uid )) {
                        Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        startActivity( intent );
                        finish();

                    } else {
//                        Intent setUp = new Intent( getApplicationContext(), SetUpActivity.class );
//                        setUp.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                        startActivity( setUp );
//                        finish();
                        String nameUser = name.getText().toString();
                        Intent intent = new Intent( getApplicationContext(), SetUpActivity.class );
                        intent.putExtra( "name", nameUser );
                        //  intent.setData( uri );
                        // intent.putExtra("imageUri", uri.toString());
                        startActivity( intent );
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        }
    }
}
