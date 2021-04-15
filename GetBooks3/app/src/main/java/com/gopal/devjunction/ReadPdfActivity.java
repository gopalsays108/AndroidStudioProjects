package com.gopal.devjunction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gopal.devjunction.getbooks.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReadPdfActivity extends AppCompatActivity {

    private PDFView pdfView;
    private String pdfUrl;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String currentUserId;
    private Uri pdfUri;
    private ProgressBar progressBar;
    private String pdfName;
    private String bookKey;
    private String uploadId;
    private int lastPage;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_read_pdf );

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            currentUserId = firebaseUser.getUid();

            pdfView = findViewById( R.id.pdfViewRead );
            progressBar = findViewById( R.id.progressBarActivityRead );

            pdfUrl = getIntent().getStringExtra( "pdfUrl" );
            bookKey = getIntent().getStringExtra( "bookKey" );
            uploadId = getIntent().getStringExtra( "uploadId" ); // userId
            int defaultValue = getIntent().getIntExtra( "defaultValue", 0 );

            defaultValue = defaultValue - 1;

            Calendar calendar = Calendar.getInstance();
            final String date = DateFormat.getDateInstance().format( calendar.getTime() );

            final Context mContext = this;
            progressBar.setVisibility( View.VISIBLE );
            Toast.makeText( mContext, "Please wait loading might take time", Toast.LENGTH_LONG ).show();

            SharedPreferences settings = getSharedPreferences( "settings", 0 );
            final boolean isChecked = settings.getBoolean( "checkbox", false );

            final int finalDefaultValue = defaultValue;
            AsyncTask.execute( new Runnable() {
                @Override
                public void run() {
                    try {
                        final InputStream input = new URL( pdfUrl ).openStream();

                        runOnUiThread( new Runnable() {
                            @Override
                            public void run() {

                                pdfView.fromStream( input )
                                        .defaultPage( finalDefaultValue )
                                        .enableSwipe( true )
                                        .swipeHorizontal( true )
                                        .pageSnap( true )
                                        .autoSpacing( true )
                                        .nightMode( isChecked )
                                        .pageFling( true )
                                        .enableAnnotationRendering( true )
                                        .scrollHandle( new DefaultScrollHandle( mContext ) )
                                        .load();
                                progressBar.setVisibility( View.INVISIBLE );
                            }
                        } );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } );

            databaseReference = FirebaseDatabase.getInstance().getReference( "RecentRead" ).child( currentUserId )
                    .child( bookKey );

            databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        databaseReference.child( "lastDate" ).setValue( date );
                    } else {
                        Map<String, Object> dateMap = new HashMap<>();
                        dateMap.put( "date", date );
                        dateMap.put( "lastDate", date );

                        databaseReference.setValue( dateMap );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        lastPage = pdfView.getCurrentPage();
        lastPage += 1;
        databaseReference.child( "lastPage" ).setValue( lastPage );

    }
}
