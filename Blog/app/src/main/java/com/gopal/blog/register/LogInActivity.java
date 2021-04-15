package com.gopal.blog.register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gopal.blog.MainActivity;
import com.gopal.blog.R;

public class LogInActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferenceUsers;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 103;
    String personName;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_log_in );

        email = findViewById( R.id.emailSignIn );
        password = findViewById( R.id.passwordSignIn );
        SignInButton signInButton = findViewById( R.id.googleSignInBtn );
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferenceUsers.keepSynced( true ); //keeps data offline
        TextView doNotHaveAnAccount = findViewById( R.id.dontHaveAnAccountSignUp );
        Button signInBtn = findViewById( R.id.signInBtn );

        signInBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eMailId = email.getText().toString();
                String pass = password.getText().toString();

                if (eMailId.isEmpty()) {
                    email.setError( "Email id empty" );
                    email.requestFocus();
                } else if (pass.isEmpty()) {
                    password.setError( "entetr pass" );
                    password.requestFocus();
                } else {
                    signIn();
                }
            }
        } );

        doNotHaveAnAccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), RegisterActivity.class ) );
                finish();
            }
        } );

        //------ Google sign in-------//
        //google configure
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN )
                .requestIdToken( getString( R.string.default_web_client_id ) ).requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient( this, googleSignInOptions );

        signInButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        } );

    }

    private void signIn() {

        firebaseAuth.signInWithEmailAndPassword( email.getText().toString(), password.getText().toString() )
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //created this to check when we use google login feature
                            checkUserExist();
                        } else {
                            Toast.makeText( LogInActivity.this, "Error \n Try Again", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
    }


    private void googleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult( signInIntent, RC_SIGN_IN );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent( data );
            handleSignInResult( task );
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> googleSignInAccountTask) {

        String TAG = "MainActivity";
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = googleSignInAccountTask.getResult( ApiException.class );
            if (account != null) {
                Log.d( TAG, "firebaseAuthWithGoogle:" + account.getId() );
                firebaseAuthWithGoogle( account );
            }
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Log.w( TAG, "Google sign in failed", e );
            // ...
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential( acct.getIdToken(), null );
        firebaseAuth.signInWithCredential( credential ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText( LogInActivity.this, "success", Toast.LENGTH_SHORT ).show();
//                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI();
                }
            }
        } );

    }

    private void updateUI() {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount( getApplicationContext() );
        if (googleSignInAccount != null) {
            personName = googleSignInAccount.getDisplayName();
            uri = googleSignInAccount.getPhotoUrl();
           // Log.i( "info", String.valueOf( uri ) );

            // String givenName = googleSignInAccount.getGivenName();
            //String perssonFamily = googleSignInAccount.getFamilyName();
            //String personEmail = googleSignInAccount.getEmail();
            //String personId = googleSignInAccount.getId();
            checkUserExist();
//            Intent intent = new Intent( getApplicationContext(), SetUpActivity.class );
//            intent.putExtra( "name", personName );
//            intent.setData( uri );
//           // intent.putExtra("imageUri", uri.toString());
//            startActivity( intent );
//            finish();

        }
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
                        Intent intent = new Intent( getApplicationContext(), SetUpActivity.class );
                        intent.putExtra( "name", personName );
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
