package com.gopal.bookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PdfViewActivity extends AppCompatActivity {

    PDFView pdfView;
    int position = -1;
    int no = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pdf_view );

        pdfView = findViewById( R.id.pdfView );

        position = getIntent().getIntExtra( "position", -1 );

        displayPdf();

//       int couunt = pdfView.fromFile( MainActivity.filesList.get( position ) )
//        Toast.makeText( this,String.valueOf( couunt ) , Toast.LENGTH_SHORT ).show();
//       Log.i( "COUNTT total" , String.valueOf( couunt ) );
    }

    private void displayPdf() {

        pdfView.fromFile( MainActivity.filesList.get( position ) )
                .enableSwipe( true )
                .enableAnnotationRendering( true )
                .scrollHandle( new DefaultScrollHandle( this ) )
                .load();
        //.defaultPage( no )// to start from specific page
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        no = pdfView.getCurrentPage();
//        pdfView.jumpTo( 5 ,true );
//        Log.i( "NUMBER" , String.valueOf( no ) );
//
//    }
}