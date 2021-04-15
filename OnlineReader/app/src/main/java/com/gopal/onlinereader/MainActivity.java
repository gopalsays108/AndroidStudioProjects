package com.gopal.onlinereader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText editPdfName;
    private Button btnUpload;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        editPdfName = findViewById( R.id.pdfName );
        btnUpload = findViewById( R.id.btnUpload );

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference( "Uploads" );

        btnUpload.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPdfFile();

            }
        } );

    }

    private void selectPdfFile() {

        Intent intent = new Intent();
        intent.setType( "application/pdf" );
        intent.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( Intent.createChooser( intent, "Select Pdf File" ), 1 );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            uploadPdfFile( data.getData() );
        }
    }

    private void uploadPdfFile(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog( this );
        progressDialog.setTitle( "UPloading..." );

        progressDialog.show();



        StorageReference reference = storageReference.child( "PDF" + System.currentTimeMillis() + ".pdf" );
        reference.putFile( data ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) {
                    Uri uri = uriTask.getResult();  // link yaha p store ho haayyega

                    UploadPdfHelper uploadPdfHelper = new UploadPdfHelper( editPdfName.getText().toString(), uri.toString() );
                    databaseReference.child( databaseReference.push().getKey() ).setValue( uploadPdfHelper );
                    progressDialog.dismiss();

                }
            }
        } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                progressDialog.setMessage( "Uploaded : " + (int) progress + "%" );

            }
        } );
    }
    private void countPages(File pdfFile) throws IOException {
        try {
            ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
            PdfRenderer pdfRenderer = null;
            pdfRenderer = new PdfRenderer(parcelFileDescriptor);
            int totalpages = pdfRenderer.getPageCount();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}