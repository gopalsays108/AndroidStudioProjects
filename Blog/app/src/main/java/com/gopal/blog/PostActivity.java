package com.gopal.blog;

import android.app.Dialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

import id.zelory.compressor.Compressor;

public class PostActivity extends AppCompatActivity {

    private ImageButton selectImage;
    private EditText enterTitle;
    private EditText enterDescription;
    private Button submitBtn;
    Uri imageUri = null;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceUsers;
    private Dialog loadingDilogue;
    private TextView textView;
    private String downloadUrl;
    private String uid;
    private String currentDate;
    private String currentTime;

    //private ProgressDialog progressDialog; // it is depreciated ,means not recommended
    private static final int GALLERY_INTENT_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_post );

        // <----- TO GET CURRENT DATE & TIME ---------->
        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format( calendar.getTime() );
        currentTime =DateFormat.getTimeInstance().format( calendar.getTime() );

        // all reference to variable
        selectImage = findViewById( R.id.selectImage );
        enterTitle = findViewById( R.id.enterTitle );
        enterDescription = findViewById( R.id.enterDescription );
        submitBtn = findViewById( R.id.submitBtn );
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
            uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child( "Blog" );
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( uid );
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        setLoadingDilogue();
    }

    private void setLoadingDilogue() {

        loadingDilogue = new Dialog( this );
        loadingDilogue.setContentView( R.layout.loading_dialogue );
        Objects.requireNonNull( loadingDilogue.getWindow() ).setBackgroundDrawable( getDrawable( R.drawable.rounded_corner_categories ) );
        loadingDilogue.getWindow().setLayout( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        loadingDilogue.setCanceledOnTouchOutside( false );

        textView = loadingDilogue.findViewById( R.id.loading_title );


        selectImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission( PostActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions( PostActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 21 );

                    } else {

                        // this is used to select the photo from galley
                        Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                        startActivityForResult( galleryIntent, GALLERY_INTENT_CODE );

                    }

                } else {

                    Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                    startActivityForResult( galleryIntent, GALLERY_INTENT_CODE );

                }
            }
        } );

        submitBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        } );
    }

    //<---to retirive image from user selected (defined on selectImage intent in intent) , retrieving now----->
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == GALLERY_INTENT_CODE && resultCode == RESULT_OK) {

            if (data != null) {
                imageUri = data.getData();
                CropImage.activity( imageUri )
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
                selectImage.setImageURI( imageUri );
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                if (result != null) {
                    Exception error = result.getError();
                    Toast.makeText( this, error.getMessage(), Toast.LENGTH_LONG ).show();
                }
            }
        }
    }

    private void startPosting() {

        //String randomId = UUID.randomUUID().toString();
        final String titleVal = enterTitle.getText().toString().trim();
        final String descriptionVal = enterDescription.getText().toString().trim();

        if (titleVal.isEmpty()) {
            Toast.makeText( getApplicationContext(), "Enter title", Toast.LENGTH_SHORT ).show();
        } else if (descriptionVal.isEmpty()) {
            Toast.makeText( getApplicationContext(), "Enter Description", Toast.LENGTH_SHORT ).show();
        } else if (imageUri == null) {
            Toast.makeText( getApplicationContext(), "Select Image", Toast.LENGTH_SHORT ).show();
        } else {

            textView.setText( R.string.uploadingBlog );
            loadingDilogue.show();
            submitBtn.setEnabled( false );

            if (imageUri.getPath() != null) {
                File thumb_filePath = new File( imageUri.getPath() );

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
                            imagereference1.getDownloadUrl().addOnCompleteListener( new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult() != null)
                                            downloadUrl = task.getResult().toString();

                                        databaseReferenceUsers.addValueEventListener( new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                String userName = (String) snapshot.child( "name" ).getValue();
                                                String imageUrl = (String) snapshot.child( "url" ).getValue();

                                                DatabaseReference newPost = databaseReference.push();

                                                newPost.child( "title" ).setValue( titleVal );
                                                newPost.child( "desc" ).setValue( descriptionVal );
                                                newPost.child( "url" ).setValue( downloadUrl );
                                                newPost.child( "uid" ).setValue( uid );
                                                newPost.child( "date" ).setValue( currentDate );
                                                newPost.child( "time" ).setValue( currentTime );
                                                newPost.child( "userName" ).setValue( userName );
                                                newPost.child( "imageUrl" ).setValue( imageUrl ).addOnCompleteListener(
                                                        new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if (task.isSuccessful()) {
                                                                    loadingDilogue.dismiss();
                                                                    Toast.makeText( PostActivity.this, "Posted", Toast.LENGTH_SHORT ).show();
                                                                    finish();

                                                                } else {
                                                                    Toast.makeText( PostActivity.this, "Try Again", Toast.LENGTH_SHORT ).show();
                                                                }
                                                            }
                                                        }
                                                );

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        } );

                                        loadingDilogue.dismiss();
//                                        Toast.makeText( PostActivity.this, "Posted", Toast.LENGTH_SHORT ).show();
//                                        finish();

                                    } else {
                                        submitBtn.setEnabled( true );
                                        loadingDilogue.dismiss();
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
                            submitBtn.setEnabled( true );
                            //progressDialog.dismiss();
                            loadingDilogue.dismiss();
                            Toast.makeText( getApplicationContext(), e.toString(), Toast.LENGTH_SHORT ).show();
                        }
                    } );
                }

            } else {
                submitBtn.setEnabled( true );
                loadingDilogue.dismiss();
                Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
            }
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