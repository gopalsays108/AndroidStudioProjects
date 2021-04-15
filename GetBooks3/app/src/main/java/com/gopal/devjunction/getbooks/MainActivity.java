package com.gopal.devjunction.getbooks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gopal.devjunction.AllpdfActivity;
import com.gopal.devjunction.TermsAcceptActivity;
import com.gopal.devjunction.allbooks.AllBooksActivity;
import com.gopal.devjunction.register.LoginActivity;
import com.gopal.devjunction.requestbook.RequestBookActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import id.zelory.compressor.Compressor;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseCurrentUser;
    private Dialog addNameDialog;
    private ViewPager viewPager;
    private DatabaseReference databaseReference;
    private Uri chooseImageUri = null;
    private String currentUserId;
    private ImageView userImage;
    private String downloadUrl;
    private EditText userName;
    private Button updateBtn;
    private StorageReference storageReference1;
    private ImageView userProfileImage;
    private TextView userHeaderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseCurrentUser = firebaseAuth.getCurrentUser();
        if (firebaseCurrentUser != null) {

            currentUserId = firebaseCurrentUser.getUid();

            Toolbar toolbar = findViewById( R.id.mainToolbar );
            setSupportActionBar( toolbar );

            DrawerLayout drawerLayout = findViewById( R.id.drawerLayout );
            NavigationView navigationView = findViewById( R.id.navView );
            databaseReference = FirebaseDatabase.getInstance().getReference( "Users" ).child( currentUserId );
            storageReference1 = FirebaseStorage.getInstance().getReference();

            setNameDialog();

            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle( this,
                    drawerLayout, toolbar, R.string.open_nav_drawer, R.string.close_nav_drawer );

            drawerLayout.addDrawerListener( actionBarDrawerToggle );
            actionBarDrawerToggle.syncState();

            navigationView.setNavigationItemSelectedListener( this );

            //tabs
            viewPager = findViewById( R.id.mainPager );

            SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter( getSupportFragmentManager(), 0 );
            viewPager.setAdapter( sectionPagerAdapter );

            //----- To get to specific page
            viewPager.setCurrentItem( 0 ); // first page

            View headerView = navigationView.getHeaderView( 0 );
            userHeaderName = headerView.findViewById( R.id.userHeaderName );
            userProfileImage = headerView.findViewById( R.id.userProfileImage );

            TabLayout tabLayout = findViewById( R.id.mainTab );
            tabLayout.setupWithViewPager( viewPager );

            checkRequest();

        }

    }

    private String restoredPrefData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences( "privacyAccept", MODE_PRIVATE );
        return sharedPreferences.getString( "isAccpeted", "no" );
    }

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            if (viewPager.getCurrentItem() == 1 && viewPager != null) {
                viewPager.setCurrentItem( 0 );
            } else {
                if (viewPager != null)
                    if (viewPager.getCurrentItem() == 2 && viewPager != null) {
                        viewPager.setCurrentItem( 0 );
                    } else {
                        finish();
                    }
            }
        }
        mBackPressed = System.currentTimeMillis();
    }

    private void checkRequest() {
        //todo : deleting request older than  30 days

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert( 30, TimeUnit.DAYS );
        Query oldItems = rootRef.child( "Request" ).child( currentUserId ).orderByChild( "timestamp" ).endAt( cutoff );
        oldItems.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    itemSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        } );
    }

    // click event is handled herer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.navMenuBooks) {
            startActivity( new Intent( getApplicationContext(), AllBooksActivity.class ) );
        } else if (item.getItemId() == R.id.uploadBooksNav) {
            Toast.makeText( this, "Wait", Toast.LENGTH_SHORT ).show();
            startActivity( new Intent( getApplicationContext(), AllpdfActivity.class ) );
        } else if (item.getItemId() == R.id.logOut) {
            firebaseAuth.signOut();
            loginIntent();
        } else if (item.getItemId() == R.id.rateApp) {
            startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.gopal.devjunction.getbooks" ) ) );
        } else if (item.getItemId() == R.id.likedBookActivity) {
            startActivity( new Intent( getApplicationContext(), LikedBookActivity.class ) );
        } else if (item.getItemId() == R.id.requestBook) {
            startActivity( new Intent( getApplicationContext(), RequestBookActivity.class ) );
        } else if (item.getItemId() == R.id.shareApp) {
            Intent shareIntent = new Intent( Intent.ACTION_SEND );
            shareIntent.setType( "text/plain" );
            String sharebody = "Download this amazing GetBooks Ebook Reader App. \n" +
                    "App that lets you read your favorite books on the go. Choose from a massive " +
                    "collection of popular books that you can download in a jiffy." +
                    " Add your own book and continue Reading :- https://play.google.com/store/apps/details?id=com.gopal.devjunction.getbooks&hl=en";
            String shareSub = "GetBooks | Ebook Reader";

            shareIntent.putExtra( Intent.EXTRA_SUBJECT, shareSub );
            shareIntent.putExtra( Intent.EXTRA_TEXT, sharebody );

            startActivity( Intent.createChooser( shareIntent, "Share using" ) );
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

//        if (restoredPrefData().equals( "no" )) {
//            startActivity( new Intent( getApplicationContext(), TermsAcceptActivity.class ) );
//            finish();
//        }

        if (firebaseCurrentUser == null) {
            loginIntent();
        } else {

                    if (restoredPrefData().equals( "no" )) {
            startActivity( new Intent( getApplicationContext(), TermsAcceptActivity.class ) );
            finish();
        }

            databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = (String) snapshot.child( "name" ).getValue();
                        String image = (String) snapshot.child( "image" ).getValue();
                        if (name == null || image == null) {
                            addNameDialog.show();
                        } else {
                            userHeaderName.setText( name );
                            Glide.with( getApplicationContext() ).load( image )
                                    .placeholder( R.drawable.face ).into( userProfileImage );
                        }
                    } else {
                        addNameDialog.show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        }
    }

    private void setNameDialog() {

        addNameDialog = new Dialog( this );
        addNameDialog.setContentView( R.layout.enter_name_dialogue );
        if (addNameDialog.getWindow() != null) {
            addNameDialog.getWindow().setBackgroundDrawable( getDrawable( R.drawable.rounded_corner ) );
            addNameDialog.getWindow().setLayout( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
            addNameDialog.setCancelable( false );

            userImage = addNameDialog.findViewById( R.id.userImage );
            userName = addNameDialog.findViewById( R.id.userNameUpdated );
            updateBtn = addNameDialog.findViewById( R.id.updateUserData );

            userImage.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkPermissions();
                }
            } );

            updateBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = userName.getText().toString();
                    if (name.isEmpty()) {
                        userName.setError( "Enter Name" );
                        userName.requestFocus();
                    } else {
                        updateBtn.setEnabled( false );
                        updateBtn.setText( R.string.updating );
                        startSendingImage();
                    }
                }
            } );
        }
    }


    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_DENIED) {

                //permission not enabled request it
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions( permission, 20 );

            } else {

                // this is used to select the photo from galley
                CropImage.activity().setGuidelines( CropImageView.Guidelines.ON )
                        .setCropShape( CropImageView.CropShape.RECTANGLE )
                        .setAspectRatio( 1, 1 )
                        .start( this );

            }

        } else {

            CropImage.activity().setGuidelines( CropImageView.Guidelines.ON )
                    .setCropShape( CropImageView.CropShape.RECTANGLE )
                    .setAspectRatio( 1, 1 )
                    .start( this );

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult( data );
            if (resultCode == RESULT_OK) {
                if (result != null)

                    chooseImageUri = result.getUri();
                userImage.setImageURI( chooseImageUri );

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                if (result != null) {
                    Exception error = result.getError();
                    Toast.makeText( this, error.getMessage(), Toast.LENGTH_LONG ).show();
                }
            }
        }
    }

    private void startSendingImage() {

        if (chooseImageUri != null) {

            if (chooseImageUri.getPath() != null) {

                File file_thumb_image_path = new File( chooseImageUri.getPath() );
                Bitmap thumb_bitmap = null;


                try {
                    thumb_bitmap = new Compressor( this )
                            .setMaxWidth( 200 )
                            .setMaxHeight( 200 )
                            .setQuality( 35 )
                            .compressToBitmap( file_thumb_image_path );

                } catch (IOException e) {
                    e.printStackTrace(); //nikal acticity yehi hai dishhh vaala
                    Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (thumb_bitmap != null)
                    thumb_bitmap.compress( Bitmap.CompressFormat.JPEG, 35, byteArrayOutputStream );
                final byte[] final_Image = byteArrayOutputStream.toByteArray();

                final String name = userName.getText().toString();
                if (chooseImageUri.getLastPathSegment() != null) {

                    final StorageReference imagereference1 = storageReference1.child( "EBook" )
                            .child( currentUserId )
                            .child( "user_img" )
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

                                        Map<String, Object> messageMap = new HashMap<>();
                                        messageMap.put( "name", name );
                                        messageMap.put( "image", downloadUrl );

                                        databaseReference.setValue( messageMap ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    updateBtn.setEnabled( true );
                                                    updateBtn.setText( R.string.update );
                                                    addNameDialog.dismiss();

                                                } else {
                                                    updateBtn.setEnabled( true );
                                                    updateBtn.setText( R.string.update );
                                                    Toast.makeText( MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT ).show();
                                                }
                                            }
                                        } );
                                    } else {
                                        Toast.makeText( MainActivity.this, "Error", Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            } );
                        }
                    } );

                } else {
                    Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
                }
            } else {
                Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
            }
        } else {
            Map<String, String> userMap = new HashMap<>();
            userMap.put( "name", userName.getText().toString() );
            userMap.put( "image", "default" );

            databaseReference.setValue( userMap ).addOnCompleteListener( new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        updateBtn.setEnabled( true );
                        updateBtn.setText( R.string.update );
                        addNameDialog.dismiss();
                    } else {
                        updateBtn.setEnabled( true );
                        updateBtn.setText( R.string.update );
                        Toast.makeText( MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT ).show();
                    }

                }
            } );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.right_menu, menu );

        SharedPreferences settings = getSharedPreferences( "settings", 0 );
        boolean isChecked = settings.getBoolean( "checkbox", false );
        MenuItem item = menu.findItem( R.id.app_bar_switch );
        item.setChecked( isChecked );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_account) {
            addNameDialog.show();
        } else if (item.getItemId() == R.id.about_us) {
            startActivity( new Intent( getApplicationContext(), AboutUsActivity.class ) );
        } else if (item.getItemId() == R.id.app_bar_switch) {
            item.setChecked( !item.isChecked() );
            SharedPreferences settings = getSharedPreferences( "settings", 0 );
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean( "checkbox", item.isChecked() );
            editor.apply();

            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    private void loginIntent() {

        Intent loginIntent = new Intent( getApplicationContext(), LoginActivity.class );
        startActivity( loginIntent );
        finish();

    }

}