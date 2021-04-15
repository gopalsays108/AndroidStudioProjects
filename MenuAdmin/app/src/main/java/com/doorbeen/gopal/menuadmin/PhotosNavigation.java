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

public class PhotosNavigation extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<PhotoModel> photoModelList;
    private DatabaseReference db;
    String uid;
    private Uri image;
    private Dialog imageDialogue;
    private photo_adapter photo_adapter;
    private String downloadUrl;
    private CircleImageView addImage;
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


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        setImageDialogue();

        photoModelList = new ArrayList<>();
        photo_adapter = new photo_adapter( photoModelList, new photo_adapter.DeleteListners() {
            @Override
            public void onDeletes(final String key, final int position) {
                new AlertDialog.Builder( PhotosNavigation.this ).setTitle( "Delete?" ).setMessage( "Are you sure you want to delete this item?" ).
                        setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db = FirebaseDatabase.getInstance().getReference();
                                db.child( "MenuAdmins" ).child( "Restaurants" ).child( uid ).child( "Names" ).child( getIntent().getStringExtra( "key" ) ).child( "Photo" ).
                                        child( key ).removeValue().addOnCompleteListener( new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // Deleting
                                        DatabaseReference deleteReff = FirebaseDatabase.getInstance().getReference( "Trial" );
                                        deleteReff.child( uid ).child( "Names" ).child( getIntent().getStringExtra( "key" ) ).child( "Photo" )
                                                .child( key ).removeValue().addOnCompleteListener( new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    photoModelList.remove( position );
                                                    photo_adapter.notifyDataSetChanged();

                                                } else {

                                                    Toast.makeText( PhotosNavigation.this, "Something Went Wrong", Toast.LENGTH_SHORT ).show();

                                                }

                                            }
                                        } );
                                    }
                                } );
                            }
                        } ).setNegativeButton( "No", null ).setIcon( android.R.drawable.ic_dialog_alert ).
                        show();
            }
        } );
        recyclerView.setAdapter( photo_adapter );


        db = FirebaseDatabase.getInstance().getReference( "MenuAdmins" ).child( "Restaurants" ).child( uid );
        progressBar.setVisibility( View.VISIBLE );
        db.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot dataSnapshot2 : dataSnapshot.child( "Names" ).child( getIntent().getStringExtra( "key" ) )
                            .child( "Photo" ).getChildren()) {

                        String url = (String) dataSnapshot2.child( ("url") ).getValue();

                        photoModelList.add( new PhotoModel( url, dataSnapshot2.getKey() ) );

                        progressBar.setVisibility( View.INVISIBLE );
                        photo_adapter.notifyDataSetChanged();
                    }
                    progressBar.setVisibility( View.INVISIBLE );

                } else {
                    progressBar.setVisibility( View.INVISIBLE );
                    Toast.makeText( PhotosNavigation.this, "Something Went Wrong /n Restart the App", Toast.LENGTH_SHORT ).show();
                }
                progressBar.setVisibility( View.INVISIBLE );


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility( View.INVISIBLE );
                Toast.makeText( PhotosNavigation.this, "Something Went Wrong /n Restart the App", Toast.LENGTH_SHORT ).show();

            }
        } );


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.add) {
            imageDialogue.show();
        } else if (item.getItemId() == R.id.logOut) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent( PhotosNavigation.this , MainActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity( intent );
        }else if (item.getItemId() == R.id.aboutUs){
            startActivity( new Intent( PhotosNavigation.this , AboutUsActivity.class ) );
        }else if (item.getItemId() == R.id.shareApp){
            Intent shareIntent= new Intent( Intent.ACTION_SEND );
            shareIntent.setType( "text/plain" );
            String sharebody = "Download this amazing online menu app. \n" +
                    "App that shows all nearby restaurants" +
                    " menu :- https://play.google.com/store/apps/details?id=com.doorbeen.gopal.menuadmin&hl=en";
            String shareSub = "Doorbeen App";

            shareIntent.putExtra( Intent.EXTRA_SUBJECT , shareSub);
            shareIntent.putExtra( Intent.EXTRA_TEXT , sharebody );

            startActivity( Intent.createChooser( shareIntent , "Share using" ) );
        }
        return super.onOptionsItemSelected( item );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.dialog_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    private void setImageDialogue() {

        imageDialogue = new Dialog( this );
        imageDialogue.setContentView( R.layout.add_photo_dialogue );
        imageDialogue.getWindow().setBackgroundDrawable( getDrawable( R.drawable.rounded_corner_categories ) );
        imageDialogue.getWindow().setLayout( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        imageDialogue.setCancelable( true );

        addImage = imageDialogue.findViewById( R.id.image_holder_image );
        Button addButtonMenu = imageDialogue.findViewById( R.id.add_menu_image );

        addImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission( PhotosNavigation.this, android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions( PhotosNavigation.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 21 );

                    } else {

                        Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                        startActivityForResult( galleryIntent, 103 );

                    }

                } else {

                    Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                    startActivityForResult( galleryIntent, 103 );

                }
            }
        } );

        addButtonMenu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image == null) {
                    Toast.makeText( PhotosNavigation.this, "Please Choose Image", Toast.LENGTH_SHORT ).show();
                } else if (image != null) {
                    Toast.makeText( PhotosNavigation.this, "Please Wait", Toast.LENGTH_SHORT ).show();
                    imageDialogue.dismiss();
                    progressBar.setVisibility( View.VISIBLE );
                    uploadPhoto();
                }
            }
        } );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == 103) {
            if (resultCode == RESULT_OK) {

                Uri imageUri = null;
                if (data != null) {
                    imageUri = data.getData();
                    CropImage.activity( imageUri )
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
                if (result != null)
                    image = result.getUri();
                addImage.setImageURI( image );
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText( this, error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
    }

    private void uploadPhoto() {
        File thumb_filePath = new File( image.getPath() );
        //   File actualPhoto = new File( image.getLastPathSegment() );
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

        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        thumb_bitmap.compress( Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream2 );
        byte[] final_menu = byteArrayOutputStream2.toByteArray();

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        final StorageReference storageReference1 = storageReference.child( "MenuAdmins" ).child( "Restaurants" ).child( uid )
                .child( "Names" ).child( getIntent().getStringExtra( "key" ) ).child( "Photo" )
                .child( image.getLastPathSegment() );

        UploadTask uploadTask = storageReference1.putBytes( final_menu );

        uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                storageReference1.getDownloadUrl().addOnCompleteListener( new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadUrl = task.getResult().toString();
                            uploadImageName();

                        } else {
                            Toast.makeText( PhotosNavigation.this, "Something Went Wrong", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( PhotosNavigation.this, "Something Went Wrong /n Try again", Toast.LENGTH_SHORT ).show();
            }
        } );


    }

    //upload
    private void uploadImageName() {


        Map<String, Object> map = new HashMap<>();

        map.put( "url", downloadUrl );

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference( "MenuAdmins" ).child( "Restaurants" ).child( uid ).child( "Names" ).child( getIntent().getStringExtra( "key" ) )
                .child( "Photo" ).push().setValue( map ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    photoModelList.add( new PhotoModel( downloadUrl ) );
                    photo_adapter.notifyDataSetChanged();
                    recreate();
                    progressBar.setVisibility( View.INVISIBLE );


                    Toast.makeText( PhotosNavigation.this, "Done", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( PhotosNavigation.this, "Failed To Add /nPlease try again", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }
}