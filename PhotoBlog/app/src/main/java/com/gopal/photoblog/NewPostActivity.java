package com.gopal.photoblog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import id.zelory.compressor.Compressor;

public class NewPostActivity extends AppCompatActivity {

    private ImageView postImage;
    private EditText postDec;
    private Button postBtn;
    private Uri imageUri = null;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private String currentUserId;
    private String downloadUrl;
    private ProgressBar progressBar;
    private static final int MANIFEST_READ_WRITE_PERMISSION_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_post );

        Toolbar toolbar = findViewById( R.id.newPostToolbar );
        setSupportActionBar( toolbar );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( "Add new post" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                currentUserId = firebaseUser.getUid();

                postImage = findViewById( R.id.newPostImage );
                postDec = findViewById( R.id.newPostDesc );
                postBtn = findViewById( R.id.postBlogBtn );
                storageReference = FirebaseStorage.getInstance().getReference();
                firebaseFirestore = FirebaseFirestore.getInstance();
                progressBar = findViewById( R.id.progressBarNewPost );


                postImage.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkPermission();
                    }
                } );

                postBtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkData();
                    }
                } );
            }
        }
    }

    private void checkData() {
        String postDescription = postDec.getText().toString().trim();

        if (postDescription.isEmpty()) {
            postDec.setError( "Enter Desc" );
            postBtn.requestFocus();
        } else if (imageUri == null) {
            Toast.makeText( this, "Choose Image", Toast.LENGTH_SHORT ).show();
        } else {
            progressBar.setVisibility( View.VISIBLE );
            startPosting();
        }
    }

    private void startPosting() {

        if (imageUri != null) {
            if (imageUri.getPath() != null) {
                File thumb_image = new File( imageUri.getPath() );

                Bitmap thumb_bitmap = null;

                try {
                    thumb_bitmap = new Compressor( this )
                            .setMaxWidth( 200 )
                            .setMaxHeight( 200 )
                            .setQuality( 75 )
                            .compressToBitmap( thumb_image );
                } catch (Exception e) {
//                    progressBar.setVisibility( View.INVISIBLE );
//                    setUpBtn.setEnabled( true );
                    Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (thumb_bitmap != null)
                    thumb_bitmap.compress( Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream );
                byte[] final_Image = byteArrayOutputStream.toByteArray();

                if (imageUri.getPathSegments() != null) {
                    if (imageUri.getLastPathSegment() != null) {
                        //new storage reference for file path
                        final StorageReference storageReference1 = storageReference
                                .child( "Post" ).child( "users" ).child( currentUserId )
                                .child( "post_image" ).child( imageUri.getLastPathSegment() );

                        UploadTask uploadTask = storageReference1.putBytes( final_Image );
                        uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                storageReference1.getDownloadUrl().addOnCompleteListener
                                        ( new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful() && task.getResult() != null) {
                                            downloadUrl = task.getResult().toString();

                                            Date c = Calendar.getInstance().getTime();
                                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                                            String formattedDate = df.format(c);

                                            Map<String, Object> postMap = new HashMap<>();
                                            postMap.put( "imageUrl", downloadUrl );
                                            postMap.put( "desc", postDec.getText().toString() );
                                            postMap.put( "userId", currentUserId );
                                            postMap.put( "time", formattedDate );
                                            postMap.put( "timestamp" , FieldValue.serverTimestamp() );

                                            firebaseFirestore.collection( "Posts" ).document( currentUserId ).collection( "Post" )
                                                    .document().set( postMap )
                                                    .addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()) {
                                                                Toast.makeText( NewPostActivity.this, "Posted", Toast.LENGTH_SHORT ).show();
                                                                progressBar.setVisibility( View.INVISIBLE );
                                                                postBtn.setEnabled( true );
                                                                finish();
                                                            } else {
                                                                progressBar.setVisibility( View.INVISIBLE );
                                                                postBtn.setEnabled( true );
                                                                Toast.makeText( NewPostActivity.this, "Try Again", Toast.LENGTH_SHORT ).show();
                                                            }
                                                        }
                                                    } );


                                        } else {
                                            if (task.getException() != null) {
                                                progressBar.setVisibility( View.INVISIBLE );
                                                postBtn.setEnabled( true );
                                                String error = task.getException().getLocalizedMessage();
                                                Toast.makeText( getApplicationContext(), error, Toast.LENGTH_SHORT ).show();
                                            }
                                        }
                                    }
                                } );
                            }
                        } ).addOnFailureListener( new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility( View.INVISIBLE );
                                postBtn.setEnabled( true );
                                Toast.makeText( getApplicationContext(), e.toString(), Toast.LENGTH_SHORT ).show();
                            }
                        } );
                    }

                } else {
                    progressBar.setVisibility( View.INVISIBLE );
                    postBtn.setEnabled( true );
                    Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
                }

            }


        } else {
            progressBar.setVisibility( View.INVISIBLE );
            postBtn.setEnabled( true );
            Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions( NewPostActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MANIFEST_READ_WRITE_PERMISSION_CODE );

            } else {
                if (ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText( this, "Permission Denied", Toast.LENGTH_SHORT ).show();
                } else {
                    startGalleryIntent();
                }
            }
        } else {
            //---------- No runtime permission required below marshmallow-----------
            startGalleryIntent();
        }
    }

    private void startGalleryIntent() {
        CropImage.activity()
                .setAspectRatio( 2, 1 )
                .setGuidelines( CropImageView.Guidelines.ON )
                .start( NewPostActivity.this );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult( data );
            if (resultCode == RESULT_OK) {
                if (result != null)
                    imageUri = result.getUri();
                postImage.setImageURI( imageUri );
                //   startUploading();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                if (result != null) {
                    Exception exception = result.getError();
                    Toast.makeText( this, exception.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            }
        } else {
            Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
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