package com.gopal.devjunction.udemylearningpart1;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean isFade = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        final ImageView fadingImageView = findViewById( R.id.fadingImage );
        final ImageView ttnImage = findViewById( R.id.ttn );

        fadingImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fadingImageView.animate().translationXBy( -4000 ).setDuration( 2000 ); //- mtlb left

               // fadingImageView.animate().scaleX( 0.5f ).scaleY( 0.5f ).setDuration( 2000 );

                fadingImageView.setX( -1000 );

                fadingImageView.animate().rotation( 360 * 5 ).translationXBy( 1000 ).alpha( 1 ).setDuration( 2000 );

                //fadingImageView.animate().rotation( 360 * 6 ).setDuration( 2000 ); // - or + se clockwise or anticlockwise rotate krta hai

                //fadingImageView.animate().translationYBy( 1000 ).setDuration( 2000 ); //down
            }
        } );


//        fadingImageView.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText( getApplicationContext(), "Clicked", Toast.LENGTH_LONG ).show();
//                Log.i( "TAG", "Cliked" );
//                if (isFade) {
//                    isFade = false;
//                    fadingImageView.animate().alpha( 0 ).setDuration( 1000 );//.start()
//                    ttnImage.animate().alpha( 1 ).setDuration( 1000 );
//                } else {
//                    fadingImageView.animate().alpha( 1 ).setDuration( 1000 );//.start()
//                    ttnImage.animate().alpha( 0 ).setDuration( 1000 );
//                    isFade = true;
//                }
//            }
//        } );
    }

//    public void face(View view){
//        Toast.makeText(getApplicationContext(), "Clicked" , Toast.LENGTH_LONG).show();
//        Log.i("TAG", "Cliked");
//    }
}