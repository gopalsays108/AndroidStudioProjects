package com.gopal.devjunction.hd4kwallpaper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

public class FullScreenImageActivity extends AppCompatActivity {

    private String originalSize;
    private String photoUrl;
    private String photographerUrl;
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static boolean toSetWallpaper = false;
    @SuppressLint("StaticFieldLeak")
    static ImageView fullscreenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_full_screen_image );

        FullScreenImageActivity.context = getApplicationContext();

        originalSize = getIntent().getStringExtra( "originalImageSize" );
        photoUrl = getIntent().getStringExtra( "photoUrl" );
        photographerUrl = getIntent().getStringExtra( "photographerUrl" );
        String photographerNames = getIntent().getStringExtra( "photographerNames" );
        String landScapeUrl = getIntent().getStringExtra( "landScapeUrl" );

        Toolbar toolbar = findViewById( R.id.fullscreenImageToolbar );
        setSupportActionBar( toolbar );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( "Photo by " + photographerNames );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }

        fullscreenImage = findViewById( R.id.fullscreenImage );

        Glide.with( FullScreenImageActivity.this ).load( originalSize ).
                thumbnail( Glide.with( this ).load( landScapeUrl ) ).into( fullscreenImage );

        View progressBarView = findViewById( R.id.loading_indicator );
        progressBarView.setVisibility( View.INVISIBLE );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.fullscreen_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.photographer_url_menu) {
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( photographerUrl ) );
            startActivity( intent );
        } else if (item.getItemId() == R.id.image_url_menu) {
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( photoUrl ) );
            startActivity( intent );
        } else if (item.getItemId() == R.id.download_photo_menu) {
            toSetWallpaper = false;
            askPermission();
        } else if (item.getItemId() == R.id.setAs_wallapaper_menu) {
            setWallpapers();
        }

        return super.onOptionsItemSelected( item );
    }

    private void askPermission() {

        if (ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( FullScreenImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101 );

        } else {
            if (ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText( this, "Permission Denied", Toast.LENGTH_SHORT ).show();
            } else {
                Toast.makeText( context, "Downloading...", Toast.LENGTH_SHORT ).show();
                DownloadAsyncTask asyncTask = new DownloadAsyncTask();
                asyncTask.execute( originalSize );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText( context, "Downloading...", Toast.LENGTH_SHORT ).show();
                DownloadAsyncTask asyncTask = new DownloadAsyncTask();
                asyncTask.execute( originalSize );
            } else {
                // Explain to the user that the feature is unavailable because
                // the features requires a permission that the user has denied.
                // At the same time, respect the user's decision. Don't link to
                // system settings in an effort to convince the user to change
                // their decision.
                Toast.makeText( context, "Permission Denied", Toast.LENGTH_SHORT ).show();
            }
        }
    }

    private static void setWallpapers() {
        Bitmap bitmap1 = ((BitmapDrawable) FullScreenImageActivity.fullscreenImage.getDrawable()).getBitmap();
        WallpaperManager wallpaperManager = WallpaperManager.getInstance( context );
        try {
            wallpaperManager.setBitmap( bitmap1 );
            Toast.makeText( context, "Wallpaper Set", Toast.LENGTH_SHORT ).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e( "Tag", "setWallpapers: ", e );
        }
    }

    private static void SaveImage(Bitmap finalBitmap) {

        final int ASCII_START_CODE = 65;  // known as least value
        final int ASCII_END_CODE = 122; // known oa  upper bound (exclusive)
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            stringBuilder.append( (char) ThreadLocalRandom.current().nextInt( ASCII_START_CODE, ASCII_END_CODE ) );
        }

        String gop = Environment.getExternalStorageState().toString();
        Log.i( "Tag" ,"Storage Location1 : " + gop  );

        String root = Environment.getExternalStorageDirectory().toString();
        Log.i( "Tag" ,"Storage Location2 : " + root  );
        File myDir = new File( root );
        String fname = "Image-" + stringBuilder + ".jpg";
        File file = new File( myDir, fname );
        if (file.exists())
            Log.i( "LOAD Gopal", root + fname );
        try {
            FileOutputStream out = new FileOutputStream( file );
            finalBitmap.compress( Bitmap.CompressFormat.JPEG, 100, out );
            out.flush();
            out.close();
            //Snackbar.make( context ,"Snackbar" , Snackbar.LENGTH_SHORT  ).show();
            Toast.makeText( context, "Downloaded", Toast.LENGTH_SHORT ).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class DownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = createUrl( strings[0] );

            if (url != null)
                try {
                    return makeHttpConnection( url );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            FullScreenImageActivity.SaveImage( bitmap );
        }


        private Bitmap makeHttpConnection(URL url) throws IOException {

            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod( "GET" );
                httpURLConnection.connect();

                inputStream = httpURLConnection.getInputStream();
                return readFromInput( inputStream );

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }

        private Bitmap readFromInput(InputStream inputStream) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream( inputStream );
            return BitmapFactory.decodeStream( bufferedInputStream );
        }

        private URL createUrl(String string) {
            try {
                return new URL( string );
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}

/*
   //Text of the Document
    String textToWrite = "bla bla bla";

    //Checking the availability state of the External Storage.
    String state = Environment.getExternalStorageState();
    if (!Environment.MEDIA_MOUNTED.equals(state)) {

        //If it isn't mounted - we can't write into it.
        return;
    }

    //Create a new file that points to the root directory, with the given name:
    File file = new File(getExternalFilesDir(null), filenameExternal);

    //This point and below is responsible for the write operation
    FileOutputStream outputStream = null;
    try {
        file.createNewFile();
        //second argument of FileOutputStream constructor indicates whether
        //to append or create new file if one exists
        outputStream = new FileOutputStream(file, true);

        outputStream.write(textToWrite.getBytes());
        outputStream.flush();
        outputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
 */