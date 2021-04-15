package com.doorbeen.gopal.menuadmin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class DishChangeImageActivity extends AppCompatActivity {

    private CircleImageView circleImageView;
    private TextView dishName;
    private Button changeDishBtn;
    private DatabaseReference databaseReference;
    String uid;
    private Uri image;
    String keyRes;
    String downloadUrl;
    String key;
    private ProgressBar progressBar;
    String dish, type, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dish_change_image );
        circleImageView = findViewById( R.id.changeDishIcon );
        changeDishBtn = findViewById( R.id.changeDishIconBtn );
        dishName = findViewById( R.id.changeDishName );
        progressBar = findViewById( R.id.progressbar_changeDish );
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();
        dish = getIntent().getStringExtra( "name" );
        type = getIntent().getStringExtra( "type" );
        price = getIntent().getStringExtra( "price" );

        Toolbar toolbar = findViewById( R.id.changeDishToolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( dish );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        dishName.setText( getIntent().getStringExtra( "name" ) );

        databaseReference = FirebaseDatabase.getInstance().getReference( "MenuAdmins" ).child( "Restaurants" ).child( uid );

        Glide.with( DishChangeImageActivity.this ).load( getIntent().getStringExtra( "url" ) ).into( circleImageView );

        keyRes = getIntent().getStringExtra( "keyRes" );
        key = getIntent().getStringExtra( "key" );

        // Toast.makeText( this, key, Toast.LENGTH_SHORT ).show();
        //Toast.makeText( this, key, Toast.LENGTH_SHORT ).show(); //reskeyhai  6m

        circleImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission( DishChangeImageActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions( DishChangeImageActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 21 );

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

        changeDishBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image != null) {
                    progressBar.setVisibility( View.VISIBLE );
                    changeDishBtn.setEnabled( false );
                    uploadDishPhoto();
                } else {
                    Toast.makeText( DishChangeImageActivity.this, "Change Image", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

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
                if (result != null)
                    image = result.getUri();
                circleImageView.setImageURI( image );
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText( this, error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
    }

    private void uploadDishPhoto() {

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
            e.printStackTrace();//nikal acticity yehi hai dishhh vaala
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumb_bitmap.compress( Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream );
        byte[] final_Image = byteArrayOutputStream.toByteArray();

        final StorageReference storageReference1 = FirebaseStorage.getInstance().getReference();

        final StorageReference imagereference1 = storageReference1.child( "MenuAdmins" ).child( "Restaurants" ).child( uid ).child( "Name" )
                .child( keyRes ).child( image.getLastPathSegment() );

        UploadTask uploadTask = imagereference1.putBytes( final_Image );
        uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagereference1.getDownloadUrl().addOnCompleteListener( new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadUrl = task.getResult().toString();
                            changeDishPhoto();
                        } else {
                            changeDishBtn.setEnabled( true );
                            progressBar.setVisibility( View.INVISIBLE );
                            Toast.makeText( DishChangeImageActivity.this, "Something went Wrong", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                changeDishBtn.setEnabled( true );
                progressBar.setVisibility( View.INVISIBLE );
                Toast.makeText( DishChangeImageActivity.this, "Something went Wrong", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    private void changeDishPhoto() {

        Map<String, Object> map = new HashMap<>();
        map.put( "dish", dish );
        map.put( "type", type );
        map.put( "price", price );
        map.put( "url2", downloadUrl );

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference().child( "MenuAdmins" ).child( "Restaurants" ).child( uid ).child( "Names" ).child( keyRes ).child( "Sets" ).
                child( key ).setValue( map ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    changeDishBtn.setEnabled( true );
                    progressBar.setVisibility( View.INVISIBLE );
                    Toast.makeText( DishChangeImageActivity.this, "Done", Toast.LENGTH_SHORT ).show();
                    Intent intent = new Intent( getApplicationContext(),Dishes.class );
                    intent.putExtra( "key",keyRes );
                    startActivity( intent );
                    finish();
                } else {
                    changeDishBtn.setEnabled( true );
                    progressBar.setVisibility( View.INVISIBLE );
                    Toast.makeText( DishChangeImageActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT ).show();
                }
                changeDishBtn.setEnabled( true );
                progressBar.setVisibility( View.INVISIBLE );
            }
        } );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected( item );
    }
}
