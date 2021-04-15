package com.gopal.devjunction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gopal.devjunction.getbooks.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class AllpdfActivity extends AppCompatActivity {

    private ListView pdf;
    public static ArrayList<File> filesList = new ArrayList<>();
    PdfAdapter objAdapter;
    private final int REQUEST_PERMISSION = 1;
    boolean aBooleanPermission;
    File dir;
    private ImageView noSearchResult;
    private TextView noSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_allpdf );

        pdf = findViewById( R.id.listViewPdf );
        EditText fromStorage = findViewById( R.id.fromStorage );
        noSearch = findViewById( R.id.noResult );
        noSearchResult = findViewById( R.id.noSearchBooks );

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( "Choose the file" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }

        dir = new File( Environment.getExternalStorageDirectory().toString() );// despricated hai;

        permissionFn();
        
        fromStorage.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    search( s );
                }else{
                    getFile( dir );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

    }


    private void permissionFn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions( AllpdfActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION );

            } else {
                if (ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText( this, "Permission Denied", Toast.LENGTH_SHORT ).show();
                } else {
                    aBooleanPermission = true;
                    getFile( dir );
                    objAdapter = new PdfAdapter( getApplicationContext(), filesList );
                    pdf.setAdapter( objAdapter );
                }
            }
        } else {
            //---------- No runtime permission required below marshmallow-----------
            aBooleanPermission = true;
            getFile( dir );
            objAdapter = new PdfAdapter( getApplicationContext(), filesList );
            pdf.setAdapter( objAdapter );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                aBooleanPermission = true;
                getFile( dir );
                objAdapter = new PdfAdapter( getApplicationContext(), filesList );
                pdf.setAdapter( objAdapter );
            }
        } else {
            Toast.makeText( this, "Allow the Permission", Toast.LENGTH_SHORT ).show();
        }
    }


    private void search(CharSequence s) {
        ArrayList<File> arrayList = new ArrayList<>(  );

        for (File ignored : filesList){
                for (int i = 0 ; i < filesList.size() ; i++){
                    if (filesList.get( i ).getName().toLowerCase().contains( s )){
                            if (!arrayList.contains( filesList.get( i ) )){
                                arrayList.add( filesList.get( i ) );
                            }
                    }
                }
        }

        if (arrayList.isEmpty()){
            noSearchResult.setVisibility( View.VISIBLE );
            noSearch.setVisibility( View.VISIBLE );
        }else{
            noSearchResult.setVisibility( View.INVISIBLE );
            noSearch.setVisibility( View.INVISIBLE );
        }

        Collections.sort( arrayList );
        PdfAdapter pdfAdapter = new PdfAdapter( getApplicationContext() , arrayList );
        pdf.setAdapter( pdfAdapter );
    }

    private ArrayList<File> getFile(File dir) {

        File[] listFile = dir.listFiles();

        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getFile( listFile[i] );
                } else {
                    boolean booleanPdf = false;
                    if (listFile[i].getName().endsWith( ".pdf" )) {
                        for (int j = 0; j < filesList.size(); j++) {
                            if (filesList.get( j ).getName().equals( listFile[i].getName() )) {

                                booleanPdf = true;

                            } else {
                            }
                        }
                        if (booleanPdf) {
                            booleanPdf = false;
                        } else {
                            filesList.add( listFile[i] );
                        }
                    }
                }
            }
        }
        Collections.sort( filesList );
        return filesList;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected( item );

    }
}