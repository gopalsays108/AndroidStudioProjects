package com.gopal.gettogether;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static com.gopal.gettogether.GetTogether.makeOnline;

public class SettingActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    String uid;
    private CircleImageView circleImageView;
    private CircleImageView bottomSheetCircleImage;
    private TextView username;
    private TextView statusUser;
    private Button changeStatusBtn;
    private String downloadUrl;
    private Button changeImageBtn;
    private Button galleryBottomBtn;
    private Button cameraBottomBtn;
    private Button submitBottomBtn;
    private Uri choosedImageUri;
    private Uri pickedImageUri = null;
    private ProgressBar progressBar;
    private BottomSheetDialog bottomSheetDialog;
    private ProgressBar progressBarBottomSheet;
    private static final int GALLERY_INTENT_CODE = 102;
    private static final int PERMISSION_CODE = 201;
    private static final int CAMERA_INTENT_CODE = 301;
    private String status;
    private String imageUri;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_setting );

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null)
            uid = firebaseUser.getUid();
        //  FirebaseDatabase.getInstance().setPersistenceEnabled( true );

        Toolbar toolbar = findViewById( R.id.settingToolbar );
        setSupportActionBar( toolbar );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( "Settings" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }
        //------Android Field---------
        circleImageView = findViewById( R.id.settingImage );
        username = findViewById( R.id.settingDisplayName );
        statusUser = findViewById( R.id.settingStatus );

        changeImageBtn = findViewById( R.id.settingImageBtn );
        changeStatusBtn = findViewById( R.id.settingStatusBtn );

        progressBar = findViewById( R.id.progressBarSettingActivity );


        databaseReference = FirebaseDatabase.getInstance().getReference( "Users" ).child( uid );
        databaseReference.keepSynced( true );
        progressBar.setVisibility( View.VISIBLE );
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    progressBar.setVisibility( View.VISIBLE );

                    Log.i( "DATA", snapshot.toString() );

                    name = (String) snapshot.child( "name" ).getValue();
                    imageUri = (String) snapshot.child( "image" ).getValue();
                    status = (String) snapshot.child( "status" ).getValue();

                    if (imageUri.equals( "default" ) || imageUri.equals( "" ) || imageUri.length() < 10) {
                        circleImageView.setImageResource( R.drawable.download );
                    } else {
                        Glide.with( getApplicationContext() ).load( imageUri ).placeholder( R.drawable.download ).
                                diskCacheStrategy( DiskCacheStrategy.ALL ).into( circleImageView );
                    }
                    username.setText( name );
                    statusUser.setText( status );

                    username.setVisibility( View.VISIBLE );
                    circleImageView.setVisibility( View.VISIBLE );
                    statusUser.setVisibility( View.VISIBLE );
                    progressBar.setVisibility( View.INVISIBLE );

                } else {
                    Toast.makeText( SettingActivity.this, "Create new Account", Toast.LENGTH_SHORT ).show();
                    progressBar.setVisibility( View.INVISIBLE );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressBar.setVisibility( View.INVISIBLE );
                Toast.makeText( SettingActivity.this, error.toString(), Toast.LENGTH_SHORT ).show();
            }
        } );

        statusUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeStatus = new Intent( getApplicationContext(), StatusActivity.class );
                changeStatus.putExtra( "status", status );
                startActivity( changeStatus );
            }
        } );

        changeStatusBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeStatus = new Intent( getApplicationContext(), StatusActivity.class );
                changeStatus.putExtra( "status", status );
                startActivity( changeStatus );
            }
        } );

        changeImageBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialogue();
            }
        } );

        circleImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialogue();
            }
        } );

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser != null) {
            makeOnline();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected( item );
    }

    //---------- Bottom dialogue work------------
    private void showImageDialogue() {

        bottomSheetDialog = new BottomSheetDialog(
                SettingActivity.this, R.style.BottomSheetDialogTheme
        );

        final View bottomSheetView = LayoutInflater.from( getApplicationContext() ).inflate(
                R.layout.layout_bottom_sheet, (LinearLayout) findViewById( R.id.bottomSheetContainer )
        );

        //---- TODO : Android fields in bottom sheet
        bottomSheetCircleImage = bottomSheetView.findViewById( R.id.bottomSheetImage );
        galleryBottomBtn = bottomSheetView.findViewById( R.id.galleyBottomSheet );
        cameraBottomBtn = bottomSheetView.findViewById( R.id.cameraBottomSheet );
        submitBottomBtn = bottomSheetView.findViewById( R.id.submitBottomSheetBtn );
        progressBarBottomSheet = bottomSheetView.findViewById( R.id.progressBarBottomSheet );

        if (imageUri.equals( "default" ) || imageUri.equals( "" ) || imageUri.length() < 10) {
            bottomSheetCircleImage.setImageResource( R.drawable.download );
        } else {
            Glide.with( getApplicationContext() ).load( imageUri ).into( bottomSheetCircleImage );
        }

        bottomSheetCircleImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionGallery();
            }
        } );

        galleryBottomBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionGallery();
            }
        } );

        cameraBottomBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
            }
        } );

        bottomSheetDialog.setContentView( bottomSheetView );
        bottomSheetDialog.show();

    }

    private void checkCameraPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_DENIED) {

                //permission not enabled request it
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions( permission, PERMISSION_CODE );

            } else {
//                Intent cameraIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
//                //cameraIntent.putExtra( MediaStore.EXTRA_OUTPUT , cameraImageUri );
//                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(cameraIntent, CAMERA_INTENT_CODE);
//                }
                CropImage.activity().setGuidelines( CropImageView.Guidelines.ON )
                        .setCropShape( CropImageView.CropShape.OVAL )
                        .setAspectRatio( 1, 1 )
                        .start( this );
            }

        } else {

            CropImage.activity().setGuidelines( CropImageView.Guidelines.ON )
                    .setCropShape( CropImageView.CropShape.OVAL )
                    .setAspectRatio( 1, 1 )
                    .start( this );
        }
    }

    private void checkPermissionGallery() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission( SettingActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions( SettingActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 21 );

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


    //------ To retrieve Image after intent-----------
    //-----this methods is called when user presses allow or deny from permission request popup
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == GALLERY_INTENT_CODE && resultCode == RESULT_OK) {

            if (data != null) {
                choosedImageUri = data.getData();
                CropImage.activity( choosedImageUri )
                        .setCropShape( CropImageView.CropShape.OVAL )
                        .setAspectRatio( 1, 1 )
                        .setGuidelines( CropImageView.Guidelines.ON )
                        .start( this );
            } else {
                Toast.makeText( this, "Something went wrong", Toast.LENGTH_SHORT ).show();
            }
        } else if (requestCode == CAMERA_INTENT_CODE && resultCode == RESULT_OK) {

            if (data != null) {
                choosedImageUri = data.getData();
                CropImage.activity( choosedImageUri )
                        .setCropShape( CropImageView.CropShape.OVAL )
                        .setGuidelines( CropImageView.Guidelines.ON )
                        .setAspectRatio( 1, 1 )
                        .start( this );
            } else {
                Toast.makeText( this, "Something went wrong", Toast.LENGTH_SHORT ).show();
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult( data );
            if (resultCode == RESULT_OK) {
                if (result != null)
                    choosedImageUri = result.getUri();
                circleImageView.setImageURI( choosedImageUri );
                bottomSheetCircleImage.setImageURI( choosedImageUri );
                pickedImageUri = choosedImageUri;
                checkUriOfImage();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                if (result != null) {
                    Exception error = result.getError();
                    Toast.makeText( this, error.getMessage(), Toast.LENGTH_LONG ).show();
                }
            }
        }
    }

    private void checkUriOfImage() {

        if (pickedImageUri == null) {
            Toast.makeText( getApplicationContext(), "Try Again", Toast.LENGTH_SHORT ).show();
        } else {

            galleryBottomBtn.setVisibility( View.INVISIBLE );
            cameraBottomBtn.setVisibility( View.INVISIBLE );

            submitBottomBtn.setVisibility( View.VISIBLE );

            submitBottomBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    submitBottomBtn.setVisibility( View.INVISIBLE );
                    progressBarBottomSheet.setVisibility( View.VISIBLE );
                    startUploadingImage();
                }
            } );
// ruk jaa galat project hai open kre de phone mat kro abhil
        }

    }

    private void startUploadingImage() {

        if (choosedImageUri != null) {

            if (choosedImageUri.getPath() != null) {
                File thumb_filePath = new File( choosedImageUri.getPath() );

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

                if (choosedImageUri.getLastPathSegment() != null) {

                    final StorageReference imagereference1 = storageReference1.child( "GetTogether" ).child( "users" ).child( uid )
                            .child( name + ".jpg" );

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

                                        databaseReference.child( "image" ).setValue( downloadUrl ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressBarBottomSheet.setVisibility( View.INVISIBLE );
                                                    bottomSheetDialog.dismiss();
                                                    galleryBottomBtn.setVisibility( View.VISIBLE );
                                                    cameraBottomBtn.setVisibility( View.VISIBLE );

                                                    submitBottomBtn.setVisibility( View.INVISIBLE );
                                                } else {
                                                    progressBarBottomSheet.setVisibility( View.INVISIBLE );
                                                    galleryBottomBtn.setVisibility( View.VISIBLE );
                                                    cameraBottomBtn.setVisibility( View.VISIBLE );

                                                    submitBottomBtn.setVisibility( View.INVISIBLE );
                                                    Toast.makeText( SettingActivity.this, "Failed", Toast.LENGTH_SHORT ).show();
                                                }
                                            }
                                        } );

                                    } else {
                                        if (task.getException() != null) {
                                            progressBarBottomSheet.setVisibility( View.INVISIBLE );
                                            galleryBottomBtn.setVisibility( View.VISIBLE );
                                            cameraBottomBtn.setVisibility( View.VISIBLE );

                                            submitBottomBtn.setVisibility( View.INVISIBLE );

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

                            progressBarBottomSheet.setVisibility( View.INVISIBLE );
                            galleryBottomBtn.setVisibility( View.VISIBLE );
                            cameraBottomBtn.setVisibility( View.VISIBLE );

                            submitBottomBtn.setVisibility( View.INVISIBLE );

                            Glide.with( getApplicationContext() ).load( imageUri ).diskCacheStrategy( DiskCacheStrategy.ALL ).into( bottomSheetCircleImage );
                            Glide.with( getApplicationContext() ).load( imageUri ).diskCacheStrategy( DiskCacheStrategy.ALL ).into( circleImageView );
                            Toast.makeText( getApplicationContext(), e.toString(), Toast.LENGTH_SHORT ).show();
                        }
                    } );
                } else {

                    progressBarBottomSheet.setVisibility( View.INVISIBLE );
                    galleryBottomBtn.setVisibility( View.VISIBLE );
                    cameraBottomBtn.setVisibility( View.VISIBLE );

                    submitBottomBtn.setVisibility( View.INVISIBLE );
                    Toast.makeText( this, "An Error Occurred \nTry Again", Toast.LENGTH_SHORT ).show();
                }
            } else {
                progressBarBottomSheet.setVisibility( View.INVISIBLE );
                galleryBottomBtn.setVisibility( View.VISIBLE );
                cameraBottomBtn.setVisibility( View.VISIBLE );

                submitBottomBtn.setVisibility( View.INVISIBLE );

                Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
            }

        } else {
            progressBarBottomSheet.setVisibility( View.INVISIBLE );
            galleryBottomBtn.setVisibility( View.VISIBLE );
            cameraBottomBtn.setVisibility( View.VISIBLE );

            submitBottomBtn.setVisibility( View.INVISIBLE );
            Toast.makeText( this, "Choose Again", Toast.LENGTH_SHORT ).show();
            Glide.with( getApplicationContext() ).load( imageUri ).diskCacheStrategy( DiskCacheStrategy.ALL ).into( bottomSheetCircleImage );
            Glide.with( getApplicationContext() ).load( imageUri ).diskCacheStrategy( DiskCacheStrategy.ALL ).into( circleImageView );

        }

    }
}
