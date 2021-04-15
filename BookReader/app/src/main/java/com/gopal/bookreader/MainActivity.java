package com.gopal.bookreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView pdf;
    public static ArrayList<File> filesList = new ArrayList<>();
    PdfAdapter objAdapter;
    private final int REQUEST_PERMISSION = 1;
    boolean aBooleanPermission;
    File dir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        pdf = findViewById( R.id.listViewPdf );

        dir = new File( Environment.getExternalStorageDirectory().toString() );// despricated hai;

        permissionFn();

        pdf.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent( getApplicationContext(), PdfViewActivity.class );
                intent.putExtra( "position", position );
                startActivity( intent );

//                int count = pdf.getCount(); return total pdf
//                Log.i( "COUNT" , String.valueOf( count ) );
            }
        } );
    }

    private void permissionFn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions( MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION );

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
            Toast.makeText( this, "Allow the Permssion", Toast.LENGTH_SHORT ).show();
        }
    }

    private ArrayList<File> getFile(File dir) {

        File listFile[] = dir.listFiles();

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
        return filesList;
    }
}