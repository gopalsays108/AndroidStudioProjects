package com.example.jyoti;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        VideoView view =  findViewById(R.id.VideoView);
        view.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.jyoti3) );
        view.start();

    }
}
