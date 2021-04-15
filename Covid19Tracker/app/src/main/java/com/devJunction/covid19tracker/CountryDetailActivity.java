package com.devJunction.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CountryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_country_detail );

        Intent intent = getIntent();
        int postionCountry = intent.getIntExtra( "position", 0 );

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( AffectedCountriesActivity.countryModelList.get( postionCountry ).getCountry() );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );

            TextView tvCases = findViewById( R.id.tvCases );
            TextView tvCountry = findViewById( R.id.tvCountry );
            TextView tvRecovered = findViewById( R.id.tvRecovered );
            TextView tvCritical = findViewById( R.id.tvCritical );
            TextView tvActive = findViewById( R.id.tvActive );
            TextView tvTodayDeath = findViewById( R.id.tvTodayDeath );
            TextView tvTodayCases = findViewById( R.id.tvTodayCases );
            TextView tvTotalDeaths = findViewById( R.id.tvDeath );
            ImageView flag = findViewById( R.id.imageFlag );

            tvCountry.setText( AffectedCountriesActivity.countryModelList.get( postionCountry ).getCountry() );
            tvCases.setText( AffectedCountriesActivity.countryModelList.get( postionCountry ).getCases() );
            tvRecovered.setText( AffectedCountriesActivity.countryModelList.get( postionCountry ).getRecovered() );
            tvCritical.setText( AffectedCountriesActivity.countryModelList.get( postionCountry ).getCritical() );
            tvActive.setText( AffectedCountriesActivity.countryModelList.get( postionCountry ).getActive() );
            tvTodayDeath.setText( AffectedCountriesActivity.countryModelList.get( postionCountry ).getTodayDeaths() );
            tvTodayCases.setText( AffectedCountriesActivity.countryModelList.get( postionCountry ).getTodayCases() );
            tvTotalDeaths.setText( AffectedCountriesActivity.countryModelList.get( postionCountry ).getDeaths() );

            Glide.with( getApplicationContext() ).load( AffectedCountriesActivity.countryModelList.get( postionCountry ).getFlag() )
                    .into( flag );
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected( item );
    }
}