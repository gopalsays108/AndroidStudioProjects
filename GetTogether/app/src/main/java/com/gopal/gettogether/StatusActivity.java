package com.gopal.gettogether;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.gopal.gettogether.GetTogether.makeOnline;

public class StatusActivity extends AppCompatActivity {

    private TextInputEditText status;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_status );
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseAuth != null)
            uid = firebaseAuth.getUid();

        Toolbar toolbar = findViewById( R.id.statusToolbar );
        setSupportActionBar( toolbar );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( "Account Status" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }

        String oldStatus = getIntent().getStringExtra( "status" );

        status = findViewById( R.id.statusInput );
        Button saveBtn = findViewById( R.id.statusSetting );
        status.setText( oldStatus );


        saveBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String statusCurrent = status.getEditableText().toString();

                databaseReference = FirebaseDatabase.getInstance().getReference( "Users" )
                        .child( uid );
                databaseReference.child( "status" ).setValue( statusCurrent ).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    finish();
                                } else {
                                    if (task.getException() != null) {
                                        String error = task.getException().getMessage();
                                        Toast.makeText( StatusActivity.this, error, Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            }
                        }
                );

            }
        } );
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth != null) {
            makeOnline();
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
