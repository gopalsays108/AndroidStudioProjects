package com.gopal.blog.register;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gopal.blog.MainActivity;
import com.gopal.blog.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class SetUpActivity extends AppCompatActivity {

    private ImageButton setupImageButton;
    private EditText nameField;
    private Uri imageUri;
    private String downloadUrl;
    private DatabaseReference databaseReference;
    private Button setUpButton;
    private FirebaseAuth firebaseAuth;
    private String uid;
    private static final int GALLERY_INTENT_CODE = 102;
    private Dialog loadingDialogue;
    private TextView loadingMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_set_up );

        setupImageButton = findViewById( R.id.setUpImageButton );
        nameField = findViewById( R.id.setUpNameField );
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        setUpButton = findViewById( R.id.setUpSubmitButton );

        String name = getIntent().getStringExtra( "name" );
        Uri uri = getIntent().getData();
       // String myUri = getIntent().getStringExtra( "imageUri" );

        imageUri = uri;
        nameField.setText( name );

        if (uri != null)
        Glide.with( getApplicationContext() ).load( uri ).into( setupImageButton );

        setUpLoadingDialogue();
    }

    private void setUpLoadingDialogue() {

        loadingDialogue = new Dialog( this );
        loadingDialogue.setContentView( R.layout.loading_dialogue );
        loadingDialogue.getWindow().setBackgroundDrawable( getDrawable( R.drawable.rounded_corner_categories ) );
        loadingDialogue.getWindow().setLayout( (LinearLayout.LayoutParams.WRAP_CONTENT), LinearLayout.LayoutParams.WRAP_CONTENT );
        loadingDialogue.setCanceledOnTouchOutside( false );

        loadingMessage = loadingDialogue.findViewById( R.id.loading_title );

        setupImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions( SetUpActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 21 );

                    } else {

                        // this is used to select the photo from galley
                        Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                        startActivityForResult( galleryIntent, GALLERY_INTENT_CODE );

                    }
                } else {
                    // this is used to select the photo from galley
                    Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                    startActivityForResult( galleryIntent, GALLERY_INTENT_CODE );

                }

            }
        } );

        setUpButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUploadingPhoto();
            }
        } );
    }


    private void startUploadingPhoto() {

        final String name = nameField.getText().toString().trim();

        if (name.isEmpty()) {
            nameField.setError( "Enter name" );
            nameField.requestFocus();
        } else if (imageUri == null) {
            Toast.makeText( getApplicationContext(), "Select Image", Toast.LENGTH_SHORT ).show();
        } else {

            loadingMessage.setText( R.string.uploadingUserData );
            loadingDialogue.show();

            File bitmap_file = new File( imageUri.getPath() );

            Bitmap thumb_bitmap = null;

            try {
                thumb_bitmap = new Compressor( this )
                        .setMaxWidth( 200 )
                        .setMaxHeight( 200 )
                        .setQuality( 75 )
                        .compressToBitmap( bitmap_file );

            } catch (IOException e) {
                e.printStackTrace();//nikal acticity yehi hai dishhh vaala
                loadingDialogue.dismiss();
            }


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (thumb_bitmap != null)
                thumb_bitmap.compress( Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream );
            byte[] final_Image = byteArrayOutputStream.toByteArray();

            final StorageReference storageReference1 = FirebaseStorage.getInstance().getReference();

            if (imageUri.getLastPathSegment() != null) {

                final StorageReference imagereference1 = storageReference1.child( "Blog" ).child( "users" )
                        .child( imageUri.getLastPathSegment() );

                UploadTask uploadTask = imagereference1.putBytes( final_Image );
                uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        loadingDialogue.show();
                        imagereference1.getDownloadUrl().addOnCompleteListener( new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult() != null)
                                        downloadUrl = task.getResult().toString();
                                    if (firebaseAuth.getCurrentUser() != null)
                                        uid = firebaseAuth.getCurrentUser().getUid();

                                    DatabaseReference newPost = databaseReference.child( "Users" ).child( uid );
                                    newPost.child( "name" ).setValue( name );
                                    newPost.child( "url" ).setValue( downloadUrl );

                                    loadingDialogue.dismiss();

                                    Intent mainIntent = new Intent( getApplicationContext(), MainActivity.class );
                                   // mainIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                    startActivity( mainIntent );
                                    finish();

                                } else {

                                    loadingDialogue.dismiss();
                                    if (task.getException() != null) {
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
                        // submitBtn.setEnabled( true );
                        //progressDialog.dismiss();
                        loadingDialogue.dismiss();
                        Toast.makeText( getApplicationContext(), e.toString(), Toast.LENGTH_SHORT ).show();
                    }
                } );

            } else {
//            submitBtn.setEnabled( true );
                loadingDialogue.dismiss();
                Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == GALLERY_INTENT_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                CropImage.activity( imageUri ).setAspectRatio( 1, 1 )
                        .setMinCropWindowSize( 500, 500 )
                        .start( this );
            } else {
                Toast.makeText( this, "Something went wrong", Toast.LENGTH_SHORT ).show();

            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult( data );
            if (resultCode == RESULT_OK) {
                if (result != null)
                    imageUri = result.getUri();
                setupImageButton.setImageURI( imageUri );
            } else {
                if (result != null) {
                    String error = result.getError().getLocalizedMessage();
                    Toast.makeText( this, error, Toast.LENGTH_SHORT ).show();
                }
            }
        }
    }

}
