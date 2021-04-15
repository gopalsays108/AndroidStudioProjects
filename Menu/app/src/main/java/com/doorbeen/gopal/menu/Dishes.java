package com.doorbeen.gopal.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Dishes extends AppCompatActivity {

    private List<DishesModel> listDish;
    BottomNavigationView bottomNavigationView;
    private DatabaseReference db;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dishes );

        Toolbar toolbar = findViewById( R.id.toolbar_dishes );
        searchView = findViewById( R.id.searchDish );
        progressBar = findViewById( R.id.progressBardISH );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( getIntent().getStringExtra( "title" ) );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        BottomNavigationView bottomNavigationView = findViewById( R.id.bottomNavigationView );

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dish_navigation:
                        break;

                    case R.id.photo_navigation:
                        Intent intent = new Intent( Dishes.this, PhotosNavigation.class );
                        intent.putExtra( "key", getIntent().getStringExtra( "key" ) );
                        intent.putExtra( "title", getIntent().getStringExtra( "title" ) );
                        startActivity( intent );
                        break;
                }
                return true;
            }
        } );

        recyclerView = findViewById( R.id.recyclerVewDishes );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( Dishes.this );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );

        recyclerView.setLayoutManager( linearLayoutManager );

        listDish = new ArrayList<>();

        final DishesAdapter dishesAdapter = new DishesAdapter( listDish );
        recyclerView.setAdapter( dishesAdapter );
        progressBar.setVisibility( View.VISIBLE );


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference( "MenuAdmins" )
                .child( "Restaurants" );

        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.child( "Names" ).child( getIntent().getStringExtra( "key" ) ).child( "Sets" )
                                .getChildren()) {

                            String url = (String) dataSnapshot2.child( ("url2") ).getValue();
                            String dish = (String) dataSnapshot2.child( ("dish") ).getValue();
                            String price = (String) dataSnapshot2.child( ("price") ).getValue();
                            String type = (String) dataSnapshot2.child( ("type") ).getValue();

                            listDish.add( new DishesModel( url, dish, type, price ) );

                            progressBar.setVisibility( View.INVISIBLE );
                            recyclerView.setAdapter( dishesAdapter );
                            dishesAdapter.notifyDataSetChanged();

                        }
                        progressBar.setVisibility( View.INVISIBLE );


                    }
                    progressBar.setVisibility( View.INVISIBLE );


                } else {
                    progressBar.setVisibility( View.INVISIBLE );
                }
                progressBar.setVisibility( View.INVISIBLE );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility( View.INVISIBLE );

            }
        } );

        /*   db = FirebaseDatabase.getInstance().getReference().child( "Names" ).
        child( getIntent().getStringExtra( "key" ) ).child( "Sets" );
        db.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    String dish = ds.child( "dish" ).getValue().toString();
                    String type  = ds.child( "type" ).getValue().toString();
                    String price = ds.child( "price" ).getValue().toString();
                    String url = ds.child( "url2" ).getValue().toString();

                    DishesModel dishesModel = new DishesModel( url,dish,type, price);
                    listDish.add( dishesModel );
                }
                dishesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );*/

        if (searchView != null) {
            searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search( newText );
                    return true;
                }
            } );
        }
    }

    private void search(String newText) {
        ArrayList<DishesModel> arrayList = new ArrayList<>();
        for (DishesModel object : listDish) {
            if (object.getDish().toLowerCase().contains( newText.toLowerCase() ) ||
                    object.getType().toLowerCase().contains( newText.toLowerCase() ) ||
                    object.getPrice().toLowerCase().contains( newText.toLowerCase() )) {

                arrayList.add( object );
            }
        }

        DishesAdapter dishesAdapter1 = new DishesAdapter( arrayList );
        recyclerView.setAdapter( dishesAdapter1 );
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }else if (item.getItemId() == R.id.aboutApp){
            startActivity( new Intent( Dishes.this , AboutUsActivity.class ) );
        }else if (item.getItemId() == R.id.shareApp){
            Intent shareIntent= new Intent( Intent.ACTION_SEND );
            shareIntent.setType( "text/plain" );
            String sharebody = "Download this amazing online menu App. \n" +
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
        getMenuInflater().inflate( R.menu.dialog_menu , menu );
        return super.onCreateOptionsMenu( menu );
    }
}
