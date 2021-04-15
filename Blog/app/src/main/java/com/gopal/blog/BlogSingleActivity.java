package com.gopal.blog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BlogSingleActivity extends AppCompatActivity {

    String postKey = null;
    private ImageView blogImage;
    private TextView blogTitle, blogDesc;
    private DatabaseReference databaseReference;
    private Button deleteblog;
    private FirebaseAuth firebaseAuth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_blog_single );
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        blogImage = findViewById( R.id.blogImage );
        blogTitle = findViewById( R.id.blogTitle );
        blogDesc = findViewById( R.id.blogDesc );
        deleteblog = findViewById( R.id.delete );

        deleteblog.setEnabled( false );

        databaseReference = FirebaseDatabase.getInstance().getReference( "Blog" );
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
            uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference.keepSynced( true );

        postKey = getIntent().getStringExtra( "blogKey" );

        // Log.i( "KEY" , postKey );
        if (postKey != null)
            databaseReference.child( postKey ).addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String postTitle = (String) snapshot.child( "title" ).getValue();
                    String postDesc = (String) snapshot.child( "desc" ).getValue();
                    String postImage = (String) snapshot.child( "url" ).getValue();
                    String postUid = (String) snapshot.child( "uid" ).getValue();

                    blogTitle.setText( postTitle );
                    blogDesc.setText( postDesc );
                    Glide.with( getApplicationContext() ).load( postImage ).into( blogImage );

                    if (uid.equals( postUid )) {
                        deleteblog.setEnabled( true );
                        deleteblog.setVisibility( View.VISIBLE );
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );

        deleteblog.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder( BlogSingleActivity.this )
                        .setTitle( "Delete" ).setMessage( "Are you sure want to delete this title" )
                        .setPositiveButton( "Delete",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        databaseReference.child( postKey ).removeValue();
                                        dialog.dismiss();
                                        finish();
                                    }
                                } ).setNegativeButton( "No", null ).setIcon( android.R.drawable.ic_dialog_alert ).
                        show();
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
