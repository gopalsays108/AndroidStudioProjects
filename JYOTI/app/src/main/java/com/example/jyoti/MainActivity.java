package com.example.jyoti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity  implements  SensorEventListener{
    TextView showValue ;
    int value = 0;

    private SensorManager sensorManager;
    private Sensor mLight;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showValue = findViewById(R.id.zero);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        Button Button = findViewById(R.id.Button);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Activity2.class);
                startActivity(intent);
                SensorManager sensorManager;
                sensorManager = (SensorManager) getSystemService( SENSOR_SERVICE );

                List<Sensor> list = sensorManager.getSensorList( Sensor.TYPE_ALL );
                Toast.makeText( MainActivity.this, "List : " + list +"\n", Toast.LENGTH_LONG).show();
                Log.i( "TAG : "  , String.valueOf( list + "\n") );
            }
        });


    }
    public void increment(View view) {
        value++ ;
        showValue.setText("$"+ value);
    }
    public void decrement(View view) {
        value--;
        showValue.setText("$"+ value);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float lux = event.values[0];
        // Do something with this sensor value.
        Toast.makeText( this, "Changed" + lux, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Toast.makeText( this, "GOpal", Toast.LENGTH_SHORT ).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}

