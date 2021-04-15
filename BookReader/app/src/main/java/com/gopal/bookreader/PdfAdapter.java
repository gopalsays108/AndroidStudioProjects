package com.gopal.bookreader;

import android.content.Context;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class PdfAdapter extends ArrayAdapter<File> {

    Context context;
    ViewHolder viewHolder; // object of class viewholder is declared here
    ArrayList<File> arrayListPdf;

    public PdfAdapter(Context context, ArrayList<File> arrayListPdf) {
        super( context, R.layout.adapter_pdf, arrayListPdf );
        this.context = context;
        this.arrayListPdf = arrayListPdf;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (arrayListPdf.size() > 0) {
            return arrayListPdf.size();
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from( getContext() ).inflate( R.layout.adapter_pdf, parent, false );
            viewHolder = new ViewHolder();
            viewHolder.fileName = convertView.findViewById( R.id.pdfName );
            convertView.setTag( viewHolder );

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.fileName.setText( arrayListPdf.get( position ).getName() );

        try {
            countPages( arrayListPdf.get( 28 ).getAbsoluteFile() );
            Log.i( "COUNT+>" , arrayListPdf.get( 28 ).getAbsolutePath() );
        } catch (IOException e) {
            String error = e.getLocalizedMessage();
            Log.i( "ERROR", error );

        }


        //Log.i( "goPAL" , String.valueOf( arrayListPdf.get( position ). ) );
        return convertView;
    }

    public static class ViewHolder {
        TextView fileName;
    }

    // page count only open document
    private void countPages(File pdfFile) throws IOException {
        try {
            ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open( pdfFile, ParcelFileDescriptor.MODE_READ_ONLY );
            PdfRenderer pdfRenderer = null;
            pdfRenderer = new PdfRenderer( parcelFileDescriptor );
            int totalpages = pdfRenderer.getPageCount();

            Log.i( "COUNTT", String.valueOf( totalpages ) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
