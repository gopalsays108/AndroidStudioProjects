package com.example.gopal.menuuser;




import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Uri image;
    private FirebaseUser user;
    private Adapter_category adapter_category;
    private String downloadUrl;
    String uid;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private List<Category_model> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = findViewById( R.id.toolbar_categories );

        // for search now searchHere = findViewById(R.id.search_categories);

        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "Select" );
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();




        recyclerView = findViewById( R.id.recyclerVewCategory );

        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );

        recyclerView.setLayoutManager( layoutManager );

        list = new ArrayList<>();

        adapter_category = new Adapter_category( list,  new Adapter_category.DeleteListener() {
            @Override
            public void onDelete(final String key, final int position) {

                new AlertDialog.Builder( MainActivity.this ).setTitle( "Delte ?" ).
                        setMessage( "R U Sure" ).setPositiveButton( "Delte", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        myRef.child( "User" ).child( uid ).child( "Names" ).child(  key ).removeValue().addOnCompleteListener( new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    myRef.child( "Sets" ).child( list.get( position ).getName() ).removeValue().addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                list.remove( position );
                                                adapter_category.notifyDataSetChanged();

                                            }else{
                                                Toast.makeText( MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT ).show();

                                            }

                                        }
                                    } );



                                    Toast.makeText( MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT ).show();

                                } else {
                                    Toast.makeText( MainActivity.this, "Failed to delete", Toast.LENGTH_SHORT ).show();
                                }
                            }
                        } );




                        myRef.child( "Names" ).child(  key ).removeValue().addOnCompleteListener( new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    myRef.child( "Sets" ).child( list.get( position ).getName() ).removeValue().addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                list.remove( position );
                                                adapter_category.notifyDataSetChanged();

                                            }else{
                                                Toast.makeText( MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT ).show();

                                            }

                                        }
                                    } );



                                    Toast.makeText( MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT ).show();

                                } else {
                                    Toast.makeText( MainActivity.this, "Failed to delete", Toast.LENGTH_SHORT ).show();
                                }
                            }
                        } );




                    }
                } ).setNegativeButton( "Camcel" , null ).setIcon( android.R.drawable.ic_dialog_alert ).show();



            }
        } );
        recyclerView.setAdapter( adapter_category );

        myRef.child( "Name" ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //list.add( dataSnapshot1.getValue( Category_model.class ) );
                    list.add( new Category_model(
                            dataSnapshot1.child( "url" ).getValue().toString(),
                            dataSnapshot1.child( "name" ).getValue().toString(),
                            dataSnapshot1.child( "address" ).getValue().toString(),
                            dataSnapshot1.child( "city" ).getValue().toString(),
                            dataSnapshot1.getKey() ) );
                }
                adapter_category.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText( MainActivity.this, "Database Error", Toast.LENGTH_SHORT ).show();

            }
        } );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add) {
            ////dialog

        }

        return super.onOptionsItemSelected( item );
    }







}
