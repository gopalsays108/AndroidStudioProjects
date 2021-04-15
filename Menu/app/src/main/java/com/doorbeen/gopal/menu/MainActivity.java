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

import androidx.appcompat.widget.SearchView;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    //private EditText searchHere;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Adapter_category adapter_category;
    private List<Category_model> list;
    private SearchView searchView;
    private AdView restaurantsAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = findViewById( R.id.toolbar_categories );
        searchView = findViewById( R.id.searchResturant );
        progressBar = findViewById( R.id.progressBarRestaurant );

        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "Restaurants" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( false );

        MobileAds.initialize( MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        } );
        restaurantsAdview = findViewById( R.id.restaurantsAdView );
        AdRequest adRequest = new AdRequest.Builder().build();
        restaurantsAdview.loadAd( adRequest );


        recyclerView = findViewById( R.id.recyclerVewCategory );

        LinearLayoutManager layoutManager = new LinearLayoutManager( MainActivity.this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );

        recyclerView.setLayoutManager( layoutManager );

        list = new ArrayList<>();

        adapter_category = new Adapter_category( list );
        recyclerView.setAdapter( adapter_category );
        progressBar.setVisibility( View.VISIBLE );

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference( "MenuAdmins" )
                .child( "Restaurants" );

        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        String uidd = dataSnapshot1.getKey();

                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.child( "Names" ).getChildren()) {
                            //String key =dataSnapshot2.getKey();


                            //bc ab tune change krdiya hehegandu fir ho toh gya ni jo yeah
                            // yeah values adapter mmien ni jaarhi
                            //kaunse adapter bc cheda mt kiya kr
                            //kaunse adapter m s bta rhahub
                            // salute bro saluteee hehe :) bc shi m dimaag khrab kr diya tune
                            // gaandu bahot gussa aata hai teko
                            // shant rha kr dimag toh khaaunga hi ter c.... bc mujhe gussa iss baat pe aa rha hai ki tu kyo yhaan se listner bhej rha h
                            // aacah yeh dekh

                            String url = (String) dataSnapshot2.child( "url" ).getValue();
                            String name = (String) dataSnapshot2.child( "name" ).getValue();
                            String address = (String) dataSnapshot2.child( "address" ).getValue();
                            String city = (String) dataSnapshot2.child( "city" ).getValue();


                            list.add( new Category_model( url, name, address, city, dataSnapshot2.getKey() ) );
                        /*
                            list.add( new Category_model(
                                    dataSnapshot2.child( "url" ).toString() ,
                                    dataSnapshot2.child( "name" ).toString(),
                                    dataSnapshot2.child( "address" ).toString(),
                                    dataSnapshot2.child( "city" ).toString(),
                                    dataSnapshot2.getKey()));*/


                            //  adapter_category = new Adapter_category( list, null );
                            //    Adapter_category adapter_category = new Adapter_category(  list);
                            progressBar.setVisibility( View.INVISIBLE );
                            recyclerView.setAdapter( adapter_category );
                            adapter_category.notifyDataSetChanged();

                        }
                        progressBar.setVisibility( View.INVISIBLE );
                    }
                    progressBar.setVisibility( View.INVISIBLE );
                }
                progressBar.setVisibility( View.INVISIBLE );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT ).show();
                progressBar.setVisibility( View.INVISIBLE );
            }


        } );


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

    private void search(String str) {

        ArrayList<Category_model>  myList = new ArrayList<>(  );
        for (Category_model object : list ){

            if (object.getName().toLowerCase().contains( str.toLowerCase() ) || object.
                    getAddress().toLowerCase().contains( str.toLowerCase() ) || object.
                    getCity().toLowerCase().contains( str.toLowerCase() )){
                myList.add( object );
            }
        }
        Adapter_category adapter_category1 =new Adapter_category( myList );
        recyclerView.setAdapter( adapter_category1 );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }else if (item.getItemId() == R.id.aboutApp){
            startActivity( new Intent( MainActivity.this , AboutUsActivity.class ) );
        }else if (item.getItemId() == R.id.shareApp){
            Intent shareIntent= new Intent( Intent.ACTION_SEND );
            shareIntent.setType( "text/plain" );
            String sharebody = "Download this amazing online menu App.\n" +
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
