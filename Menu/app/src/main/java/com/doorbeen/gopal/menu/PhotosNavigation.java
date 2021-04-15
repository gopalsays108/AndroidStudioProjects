package com.doorbeen.gopal.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PhotosNavigation extends AppCompatActivity {

    private List<PhotoModel> listPhoto;
    private DatabaseReference db;
    private PhotoAdapter photoAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_photos_navigation );

        Toolbar toolbar = findViewById( R.id.toolbar_photo );
        progressBar = findViewById( R.id.progressBarPhoto );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( getIntent().getStringExtra( "title" ) );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        recyclerView = findViewById( R.id.recyclerVewPhoto );


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( PhotosNavigation.this );
        linearLayoutManager.setOrientation( RecyclerView.HORIZONTAL );
        recyclerView.setLayoutManager( linearLayoutManager );

        listPhoto = new ArrayList<>();

        photoAdapter = new PhotoAdapter( listPhoto );
        recyclerView.setAdapter( photoAdapter );

        progressBar.setVisibility( View.VISIBLE );
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference( "MenuAdmins" )
                .child( "Restaurants" );

        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.child( "Names" ).child( getIntent().getStringExtra( "key" ) ).child( "Photo" )
                                .getChildren()) {

                            String url = (String) dataSnapshot2.child( ("url") ).getValue();

                            listPhoto.add( new PhotoModel( url ) );
                            progressBar.setVisibility( View.INVISIBLE );
                            recyclerView.setAdapter( photoAdapter );
                            photoAdapter.notifyDataSetChanged();
                        }
                        progressBar.setVisibility( View.INVISIBLE );
                    }
                    progressBar.setVisibility( View.INVISIBLE );

                } else {
                    progressBar.setVisibility( View.INVISIBLE );
                    Toast.makeText( PhotosNavigation.this, "Something went wrong", Toast.LENGTH_SHORT ).show();
                }
                progressBar.setVisibility( View.INVISIBLE );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility( View.INVISIBLE );
                Toast.makeText( PhotosNavigation.this, "Something went wrong", Toast.LENGTH_SHORT ).show();

            }
        } );

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }else if(item.getItemId() == R.id.aboutApp){
            startActivity( new Intent( PhotosNavigation.this , AboutUsActivity.class ) );
        }else if (item.getItemId() == R.id.shareApp){
            Intent shareIntent= new Intent( Intent.ACTION_SEND );
            shareIntent.setType( "text/plain" );
            String sharebody = "Download this amazing online menu App.\n " +
                    "App that shows all nearby restaurants menu :- https://play.google.com/store/apps/details?id=com.doorbeen.gopal.menu&hl=en";
            String shareSub = "Doorbeen App";

            shareIntent.putExtra( Intent.EXTRA_SUBJECT , shareSub);
            shareIntent.putExtra( Intent.EXTRA_TEXT , sharebody );

            startActivity( Intent.createChooser( shareIntent , "Share using" ) );
        }
        return super.onOptionsItemSelected( item );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.dialog_menu ,menu );
        return super.onCreateOptionsMenu( menu );
    }
}
