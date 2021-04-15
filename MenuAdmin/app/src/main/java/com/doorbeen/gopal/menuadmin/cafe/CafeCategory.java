package com.doorbeen.gopal.menuadmin.cafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.doorbeen.gopal.menuadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class CafeCategory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseUser user;
    private  String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cafe_category );

        Toolbar toolbar = findViewById( R.id.toolbar_main_category );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "Cafe" );
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();



        recyclerView = findViewById( R.id.recyclerViewCafe );
        LinearLayoutManager  manager = new LinearLayoutManager( CafeCategory.this );
        manager.setOrientation( RecyclerView.VERTICAL );

        recyclerView.setLayoutManager( manager );

        List<CafeModel> cafeModelList = new ArrayList<>(  );

        cafeModelList.add( new CafeModel( "as" , "sdas" ,"sdsa" ,"dsd" ) );
        cafeModelList.add( new CafeModel( "as" , "sdas" ,"sdsa" ,"dsd" ) );
        cafeModelList.add( new CafeModel( "as" , "sdas" ,"sdsa" ,"dsd" ) );
        cafeModelList.add( new CafeModel( "as" , "sdas" ,"sdsa" ,"dsd" ) );
        cafeModelList.add( new CafeModel( "as" , "sdas" ,"sdsa" ,"dsd" ) );


        CafeAdapter cafeAdapter = new CafeAdapter( cafeModelList );
        recyclerView.setAdapter( cafeAdapter );

    }
}
