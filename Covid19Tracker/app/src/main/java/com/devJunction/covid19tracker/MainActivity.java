package com.devJunction.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView tvCases, tvActive, tvRecovered, tvCritical, tvTodayCases;
    private TextView tvTodayDeath, tvAffectedCountries, tvTotalDeath;
    private SimpleArcLoader simpleArcLoader;
    private ScrollView scrollView;
    private PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        tvCases = findViewById( R.id.tvCases );
        tvActive = findViewById( R.id.tvActive );
        tvRecovered = findViewById( R.id.tvRecovered );
        tvCritical = findViewById( R.id.tvCritical );
        tvTodayCases = findViewById( R.id.tvTodayCases );
        tvTodayDeath = findViewById( R.id.tvTodayDeath );
        tvAffectedCountries = findViewById( R.id.tvAffectedCountries );
        tvTotalDeath = findViewById( R.id.tvTotalDeath );

        simpleArcLoader = findViewById( R.id.loader );
        scrollView = findViewById( R.id.scrollStats );
        pieChart = findViewById( R.id.pieChart );

        fetchData();
    }

    private void fetchData()  {
        String Url = "https://corona.lmao.ninja/v2/all/";   // we used / at last why?

        simpleArcLoader.start();

        StringRequest stringRequest = new StringRequest( Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject( response );

                    tvCases.setText( jsonObject.getString( "cases" ) );
                    tvActive.setText( jsonObject.getString( "active" ) );
                    tvRecovered.setText( jsonObject.getString( "recovered" ) );
                    tvCritical.setText( jsonObject.getString( "critical" ) );
                    tvTodayCases.setText( jsonObject.getString( "todayCases" ) );
                    tvTodayDeath.setText( jsonObject.getString( "todayDeaths" ) );
                    tvAffectedCountries.setText( jsonObject.getString( "affectedCountries" ) );
                    tvTotalDeath.setText( jsonObject.getString( "deaths" ) );

                    pieChart.addPieSlice( new PieModel( "Cases", Integer.parseInt( tvCases.getText().toString() ), Color.parseColor( "#FFA726" ) ) );
                    pieChart.addPieSlice( new PieModel( "Recovered", Integer.parseInt( tvRecovered.getText().toString() ), Color.parseColor( "#66bb6a" ) ) );
                    pieChart.addPieSlice( new PieModel( "Death", Integer.parseInt( tvTotalDeath.getText().toString() ), Color.parseColor( "#EF5350" ) ) );
                    pieChart.addPieSlice( new PieModel( "Active", Integer.parseInt( tvActive.getText().toString() ), Color.parseColor( "#29b6f6" ) ) );

                    pieChart.startAnimation();

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility( View.GONE );
                    scrollView.setVisibility( View.VISIBLE );


                } catch (JSONException e) {
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility( View.GONE );
                    scrollView.setVisibility( View.VISIBLE );
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility( View.GONE );
                scrollView.setVisibility( View.VISIBLE );
                Toast.makeText( MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }

    public void goTrackCountries(View view) {
        startActivity( new Intent( getApplicationContext(), AffectedCountriesActivity.class )  );
    }
}