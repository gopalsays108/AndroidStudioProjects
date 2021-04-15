package com.devJunction.covid19tracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountriesActivity extends AppCompatActivity {

    EditText editSearch;
    private ListView listView;
    private SimpleArcLoader simpleArcLoader;
    public static List<CountryModel> countryModelList;
    public static List<CountryModel> countryModelListFiltered;
    CountryAdapter countryAdapter;
    CountryModel countryModel;    //object declared

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_affected_contries2 );

        editSearch = findViewById( R.id.edit_query );
        listView = findViewById( R.id.listView );
        simpleArcLoader = findViewById( R.id.loader );

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( "Affected Countries" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }

        countryModelList = new ArrayList<>();

        fetchData();

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity( new Intent( getApplicationContext(), CountryDetailActivity.class ).putExtra( "position", position ) );
            }
        } );

        editSearch.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countryAdapter.getFilter().filter( s );
                countryAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );
    }

    private void fetchData() {
        String Url = "https://corona.lmao.ninja/v2/countries/";   // we used / at last why?

        simpleArcLoader.start();

        StringRequest stringRequest = new StringRequest( Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray( response );

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject( i ); //i positon jo object ya data hoga store krega vo

                        String countryName = jsonObject.getString( "country" );
                        String cases = jsonObject.getString( "cases" );
                        String todayCases = jsonObject.getString( "todayCases" );
                        String deaths = jsonObject.getString( "deaths" );
                        String todayDeaths = jsonObject.getString( "todayDeaths" );
                        String active = jsonObject.getString( "active" );
                        String critical = jsonObject.getString( "critical" );
                        String recovered = jsonObject.getString( "recovered" );

                        JSONObject jsonObject1 = jsonObject.getJSONObject( "countryInfo" );  // ek object ke andar se object le rha hai screen shot hoga laptop mien
                        String flagUrl = jsonObject1.getString( "flag" );

                        countryModel = new CountryModel( flagUrl, countryName, cases, todayCases,
                                deaths, todayDeaths, recovered, active, critical );

                        countryModelList.add( countryModel );
                    }

                    countryAdapter = new CountryAdapter( AffectedCountriesActivity.this, countryModelList );
                    listView.setAdapter( countryAdapter );
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility( View.GONE );
                    countryAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility( View.GONE );
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility( View.GONE );
                Toast.makeText( AffectedCountriesActivity.this, error.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected( item );
    }
}