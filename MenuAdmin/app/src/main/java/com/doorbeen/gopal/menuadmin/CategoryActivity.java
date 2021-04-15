package com.doorbeen.gopal.menuadmin;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Dialog categoryDialogue;
    private CircleImageView addImage;
    private EditText enter_category_name;
    private EditText enter_addrress_name;
    private EditText enter_city_name;
    private Uri image;
    private ProgressBar progressBarRes;
    private AdView restaurantsAdview;
    private Bitmap compressedImage;
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
        setContentView( R.layout.activity_category );
        Toolbar toolbar = findViewById( R.id.toolbar_categories );
        progressBarRes = findViewById( R.id.progressBar_restuarant );

        MobileAds.initialize( CategoryActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        } );
        restaurantsAdview = findViewById( R.id.restaurantsAdView );
        AdRequest adRequest = new AdRequest.Builder().build();
        restaurantsAdview.loadAd( adRequest );


        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "Restaurants" );
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        setCategoryDialogue();


        recyclerView = findViewById( R.id.recyclerVewCategory );

        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );

        recyclerView.setLayoutManager( layoutManager );


        list = new ArrayList<>();

        adapter_category = new Adapter_category( list, new Adapter_category.DeleteListener() {
            @Override
            public void onDelete(final String key, final int position) {

                new AlertDialog.Builder( CategoryActivity.this ).setTitle( "Delete ?" ).
                        setMessage( "Are you sure you want to delete this item?" ).setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        myRef.child( "MenuAdmins" ).child( "Restaurants" ).child( uid )
                                .child( "Names" ).child( key ).removeValue().addOnCompleteListener( new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    list.remove( position );
                                    adapter_category.notifyDataSetChanged();

                                } else {
                                    Toast.makeText( CategoryActivity.this, "Failed to delete", Toast.LENGTH_SHORT ).show();
                                }
                            }
                        } );
                    }
                } ).setNegativeButton( "No", null ).setIcon( android.R.drawable.ic_dialog_alert ).show();


            }
        } );
        //recyclerView.setAdapter( adapter_category );
        //ab kr ..hua?

        //done for now
        recyclerView.setAdapter( adapter_category );
        progressBarRes.setVisibility( View.VISIBLE );

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference( "MenuAdmins" ).child( "Restaurants" )
                .child( uid );
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot dataSnapshot2 : dataSnapshot.child( "Names" ).getChildren()) {
                        //String key =dataSnapshot2.getKey();
                        String url = (String) dataSnapshot2.child( "url" ).getValue();
                        String city = (String) dataSnapshot2.child( "city" ).getValue();
                        String name = (String) dataSnapshot2.child( "name" ).getValue();
                        String address = (String) dataSnapshot2.child( "address" ).getValue();


                        list.add( new Category_model( url, name, address, city, dataSnapshot2.getKey() ) );

                        recyclerView.setAdapter( adapter_category );
                        adapter_category.notifyDataSetChanged();
                        progressBarRes.setVisibility( View.INVISIBLE );

                    }
                    progressBarRes.setVisibility( View.INVISIBLE );

                } else {
                    progressBarRes.setVisibility( View.INVISIBLE );
                    Toast.makeText( CategoryActivity.this, "Add Item", Toast.LENGTH_SHORT ).show();
                }
                progressBarRes.setVisibility( View.INVISIBLE );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBarRes.setVisibility( View.INVISIBLE );
                Toast.makeText( CategoryActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.dialog_menu, menu );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add) {
            ////dialog

            categoryDialogue.show();

        }else if (item.getItemId() == R.id.logOut){
            FirebaseAuth.getInstance().signOut();
            startActivity( new Intent( CategoryActivity.this , MainActivity.class ) );
            finish();
        }else if(item.getItemId() == R.id.aboutUs){
            startActivity( new Intent( CategoryActivity.this , AboutUsActivity.class ) );

        }else if (item.getItemId() == R.id.shareApp){
            Intent shareIntent= new Intent( Intent.ACTION_SEND );
            shareIntent.setType( "text/plain" );
            String sharebody = "Download this amazing online menu App. \n" +
                    "App that shows all nearby restaurants menu:- https://play.google.com/store/apps/details?id=com.doorbeen.gopal.menuadmin&hl=en";
            String shareSub = "Doorbeen App";

            shareIntent.putExtra( Intent.EXTRA_SUBJECT , shareSub);
            shareIntent.putExtra( Intent.EXTRA_TEXT , sharebody );

            startActivity( Intent.createChooser( shareIntent , "Share using" ) );
        }

        return super.onOptionsItemSelected( item );
    }

    private void setCategoryDialogue() {
        categoryDialogue = new Dialog( this );
        categoryDialogue.setContentView( R.layout.add_category_dialog );
        categoryDialogue.getWindow().setBackgroundDrawable( getDrawable( R.drawable.rounded_corner_categories ) );
        categoryDialogue.getWindow().setLayout( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        categoryDialogue.setCancelable( true );

        addImage = categoryDialogue.findViewById( R.id.image_holder );
        enter_category_name = categoryDialogue.findViewById( R.id.category_name_enter );
        Button enter_add_buttton = categoryDialogue.findViewById( R.id.add_category );
        enter_addrress_name = categoryDialogue.findViewById( R.id.category_address_enter );
        enter_city_name = categoryDialogue.findViewById( R.id.category_city_enter );

        addImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission( CategoryActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions( CategoryActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 21 );

                    } else {

                        Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                        startActivityForResult( galleryIntent, 101 );

                    }

                } else {

                    Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                    startActivityForResult( galleryIntent, 101 );

                }

            }
        } );

        enter_add_buttton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enter_category_name.getText().toString().isEmpty()) {
                    enter_category_name.setError( "Required" );
                    enter_category_name.requestFocus();
                    return;
                } else if (enter_addrress_name.getText().toString().isEmpty()) {
                    enter_addrress_name.setError( "Required" );
                    enter_addrress_name.requestFocus();
                    return;
                } else if (enter_city_name.getText().toString().isEmpty()) {
                    enter_city_name.setError( "Required" );
                    enter_city_name.requestFocus();
                    return;
                }
                if (image != null) {
                    categoryDialogue.dismiss();
                    progressBarRes.setVisibility( View.VISIBLE );
                    uploadData();
                } else {
                    Toast.makeText( CategoryActivity.this, "Please Select your Image", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

    //crash dobara crash krwabkrwa
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {

                Uri imageUri = null;
                if (data != null) {
                    imageUri = data.getData();
                    CropImage.activity( imageUri )
                            .setAspectRatio( 1, 1 )
                            .setMinCropWindowSize( 500, 500 )
                            .start( this );
                } else {
                    Toast.makeText( this, "Something went wrong", Toast.LENGTH_SHORT ).show();
                }


            }

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult( data );
            if (resultCode == RESULT_OK) {
                image = result.getUri();
                addImage.setImageURI( image );

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

                Toast.makeText( this, error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
    }

    private void uploadData() {

        File thumb_filePath = new File( image.getPath() );

        Bitmap thumb_bitmap = null;
        try {
            thumb_bitmap = new Compressor( this )
                    .setMaxWidth( 200 )
                    .setMaxHeight( 200 )
                    .setQuality( 75 )
                    .compressToBitmap( thumb_filePath );
        } catch (IOException e) {
            e.printStackTrace();
        }

        //sun ek hi tarika hai thik krne ka crop method lga de wo real path de dega aacha haan call krle agr smjh nhi aaya toh

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumb_bitmap.compress( Bitmap.CompressFormat.JPEG, 100, baos );
        final byte[] thumb_byte = baos.toByteArray();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        //ho jaye toh btaio vaapis run kr
        final StorageReference imageReference = storageReference.child( "MenuAdmins" ).child( "Restaurants" )
                .child( uid ).child( "Names" ).child( image.getLastPathSegment() );

        UploadTask uploadTask = imageReference.putBytes( thumb_byte );
        uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnCompleteListener( new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {
                            progressBarRes.setVisibility( View.INVISIBLE );
                            downloadUrl = task.getResult().toString();
                            uploadCategoryName();
                        } else {
                            progressBarRes.setVisibility( View.INVISIBLE );
                            Toast.makeText( CategoryActivity.this, "Something went wrong", Toast.LENGTH_SHORT ).show();
                        }

                    }
                } );

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( CategoryActivity.this, "Failed", Toast.LENGTH_SHORT ).show();
                progressBarRes.setVisibility( View.INVISIBLE );
            }
        } );
    }

    //upload done
    private void uploadCategoryName() {

        Map<String, Object> map = new HashMap<>();
        map.put( "name", enter_category_name.getText().toString() );
        map.put( "city", enter_city_name.getText().toString() );
        map.put( "address", enter_addrress_name.getText().toString() );
        map.put( "url", downloadUrl );

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        database.getReference().child( "MenuAdmins" ).child( "Restaurants" )
                .child( uid ).child( "Names" ).push().setValue( map ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    list.add( new Category_model( enter_category_name.getText(), toString(), enter_city_name.getText().toString(),
                            enter_addrress_name.getText().toString(), downloadUrl, "name" + (list.size() + 1) ) );


                    // adapter_category.notifyDataSetChanged();
                    // recyclerView.setAdapter( adapter_category );
                    adapter_category.notifyDataSetChanged();
                    progressBarRes.setVisibility( View.INVISIBLE );
                    recreate();
                    Toast.makeText( CategoryActivity.this, "Done", Toast.LENGTH_SHORT ).show();

                } else {
                    Toast.makeText( CategoryActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

    }

}
