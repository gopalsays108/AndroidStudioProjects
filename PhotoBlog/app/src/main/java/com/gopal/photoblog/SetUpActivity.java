package com.gopal.photoblog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class SetUpActivity extends AppCompatActivity {

    private CircleImageView setUpImage;
    private EditText setUpName;
    private Button setUpBtn;
    private Uri imageUri = null;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String currentUid;
    private String downloadUrl;
    private String userName;
    private ProgressBar progressBar;
    private Boolean isChanged = false;
    private static final int MANIFEST_READ_WRITE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_set_up );

        firebaseAuth = FirebaseAuth.getInstance();
        currentUid = firebaseAuth.getUid();

        Toolbar toolbar = findViewById( R.id.setUpToolbar );
        setSupportActionBar( toolbar );
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle( "Account Setup" );

        setUpImage = findViewById( R.id.setUpImage );
        setUpName = findViewById( R.id.setupUpName );
        setUpBtn = findViewById( R.id.setUpBtn );
        progressBar = findViewById( R.id.progressBarSetUp );

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        progressBar.setVisibility( View.VISIBLE );
        firebaseFirestore.collection( "Users" ).document( currentUid ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                if (task.isSuccessful()) {
                    if (task.getResult() != null && task.getResult().exists()) {

                        String name = task.getResult().getString( "name" );
                        String userImage = task.getResult().getString( "image" );

                        imageUri = Uri.parse( userImage );
                        userName = name;

                        setUpName.setText( name );
                        Glide.with( getApplicationContext() ).load( userImage )
                                .placeholder( R.drawable.download )
                                .into( setUpImage );
                        progressBar.setVisibility( View.INVISIBLE );
                    }
                } else {
                    if (task.getException() != null) {
                        Exception e = task.getException();
                        progressBar.setVisibility( View.INVISIBLE );
                        Toast.makeText( SetUpActivity.this, e.toString(), Toast.LENGTH_SHORT ).show();
                    }
                }
                progressBar.setVisibility( View.INVISIBLE );
            }
        } );

        setUpImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        } );

        setUpBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = setUpName.getText().toString().trim();

                if (userName.isEmpty()) {
                    Toast.makeText( SetUpActivity.this, "Enter Name", Toast.LENGTH_SHORT ).show();
                } else if (imageUri == null) {
                    Toast.makeText( SetUpActivity.this, "Select Image", Toast.LENGTH_SHORT ).show();
                } else {
                    progressBar.setVisibility( View.VISIBLE );
                    setUpBtn.setEnabled( false );
                    startUploading();
                }
            }
        } );
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions( SetUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MANIFEST_READ_WRITE_PERMISSION_CODE );

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
                .setAspectRatio( 1, 1 )
                .setGuidelines( CropImageView.Guidelines.ON )
                .start( SetUpActivity.this );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult( data );
            if (resultCode == RESULT_OK) {
                if (result != null)
                    imageUri = result.getUri();
                setUpImage.setImageURI( imageUri );
                isChanged = true;
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

    private void startUploading() {

        progressBar.setVisibility( View.VISIBLE );
        if (isChanged) {
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
                        progressBar.setVisibility( View.INVISIBLE );
                        setUpBtn.setEnabled( true );
                        Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    if (thumb_bitmap != null)
                        thumb_bitmap.compress( Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream );
                    byte[] final_Image = byteArrayOutputStream.toByteArray();

                    if (imageUri.getPathSegments() != null) {
                        if (imageUri.getLastPathSegment() != null) {
                            final StorageReference storageReference1 = storageReference.child( "Blog" ).child( "users" ).child( currentUid )
                                    .child( setUpName.getText().toString() + ".jpg" );

                            UploadTask uploadTask = storageReference1.putBytes( final_Image );
                            uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    storageReference1.getDownloadUrl().addOnCompleteListener( new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()) {
                                                storeFireStore( task );
                                            } else {
                                                if (task.getException() != null) {
                                                    progressBar.setVisibility( View.INVISIBLE );
                                                    setUpBtn.setEnabled( true );
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
                                    setUpBtn.setEnabled( true );
                                    Toast.makeText( getApplicationContext(), e.toString(), Toast.LENGTH_SHORT ).show();
                                }
                            } );
                        }

                    } else {
                        progressBar.setVisibility( View.INVISIBLE );
                        setUpBtn.setEnabled( true );
                        Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
                    }

                }


            } else {
                progressBar.setVisibility( View.INVISIBLE );
                setUpBtn.setEnabled( true );
                Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
            }
        } else {
            storeFireStore( null );
        }
    }

    private void storeFireStore(@NonNull Task<Uri> task) {

        if (task != null) {
            if (task.getResult() != null)
                downloadUrl = task.getResult().toString();
        } else {
            downloadUrl = imageUri.toString();
        }
        Map<String, Object> userMap = new HashMap<>();
        userMap.put( "name", setUpName.getText().toString() );
        userMap.put( "image", downloadUrl );

        firebaseFirestore.collection( "Users" ).document( currentUid ).set( userMap )
                .addOnCompleteListener( new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            progressBar.setVisibility( View.INVISIBLE );
                            setUpBtn.setEnabled( true );
                            Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                            startActivity( intent );
                            finish();
                        } else {
                            if (task.getException() != null) {
                                progressBar.setVisibility( View.INVISIBLE );
                                setUpBtn.setEnabled( true );
                                Exception error = task.getException();
                                Toast.makeText( SetUpActivity.this, error.toString(), Toast.LENGTH_SHORT ).show();
                            }
                        }
                    }
                } );

    }
}