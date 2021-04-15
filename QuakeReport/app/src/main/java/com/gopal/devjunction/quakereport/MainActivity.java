package com.gopal.devjunction.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/*
        Implementing the LoaderCallbacks in our activity is a little more complex. First we need to
        say that EarthquakeActivity implements the LoaderCallbacks interface, along with a generic
         parameter specifying what the loader will return (in this case an Earthquake).
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthquakeModel>> {

    public static String LOG_TAG = MainActivity.class.getName();


    /**
     * Adapter for the list of earthquakes
     */
    private EarthquakeAdapter mAdapter;

    /**
     * Sample JSON response for a USGS query
     */
    public static final String SAMPLE_JSON_RESPONSE = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=100";

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i( LOG_TAG, "Test  : oncreate is called : " );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // ArrayList<EarthquakeModel> earthquakes = QueryUtils.extractEarthquake();
        //Fake Earth quake (Static Value)
//        earthquakes.add(new EarthquakeModel("7.2", "San Francisco", "Feb 2, 2016"));
//        earthquakes.add(new EarthquakeModel("6.1", "London", "July 20, 2015"));
//        earthquakes.add(new EarthquakeModel("3.9", "Tokyo", "Nov 10, 2014"));
//        earthquakes.add(new EarthquakeModel("5.4", "Mexico City", "May 3, 2014"));
//        earthquakes.add(new EarthquakeModel("2.8", "Moscow", "Jan 31, 2013"));
//        earthquakes.add(new EarthquakeModel("4.9", "Rio de Janeiro", "Aug 19, 2012"));
//        earthquakes.add(new EarthquakeModel("1.6", "Paris", "Oct 30, 2011"));
//
//        earthquakes.add("London");
//        earthquakes.add("Tokyo");
//        earthquakes.add("Mexico City");
//        earthquakes.add("Moscow");
//        earthquakes.add("Rio de Janeiro");
//        earthquakes.add("Paris");

        //Find reference to the{@link ListView} in the layout

        ListView earthquakeListView = findViewById( R.id.list );

        mEmptyStateTextView = findViewById( R.id.empty_view );
        earthquakeListView.setEmptyView( mEmptyStateTextView );

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter( this, new ArrayList<EarthquakeModel>() );

//         Create a new { @link ArrayAdapter } of earthquakes
//        ArrayAdapter<String> adapter  = new ArrayAdapter<>(
//                this, android.R.layout.simple_list_item_1, earthquakes
//        );

        // Custom Adapter that take the list of earthquake as input
        // Create a new adapter that takes the list of earthquakes as input
        //final EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter( this , earthquakes );

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter( mAdapter );

        earthquakeListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Find the current earthquake that was clicked on
                EarthquakeModel currentEarthquake = mAdapter.getItem( position );

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                assert currentEarthquake != null;
                Uri earthquakeUri = Uri.parse( currentEarthquake.getUrl() );

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent( Intent.ACTION_VIEW, earthquakeUri );

                // Send the intent to launch a new activity
                startActivity( websiteIntent );
            }
        } );
        // Get a reference to the LoaderManager, in order to interact with loaders.
        //  LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        Log.i( LOG_TAG, "Calling initLoader : " );

        // loaderManager.initLoader( EARTHQUAKE_LOADER_ID, null, MainActivity.this );

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService( Context.CONNECTIVITY_SERVICE );

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader( EARTHQUAKE_LOADER_ID, null, this );
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById( R.id.loading_indicator );
            loadingIndicator.setVisibility( View.GONE );

            // Update empty state with no connection error message
            mEmptyStateTextView.setText( R.string.no_internet );
        }

        // Start the AsyncTask to fetch the earthquake data
//        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
//        task.execute(SAMPLE_JSON_RESPONSE);
    }

    @NonNull
    @Override
    public android.content.Loader<List<EarthquakeModel>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i( LOG_TAG, "onCreateLoader Called : " );
        // Create a new loader for the given URL
        return new EarthquakeLoader( this, SAMPLE_JSON_RESPONSE );
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<EarthquakeModel>> loader, List<EarthquakeModel> data) {
        Log.i( LOG_TAG, "onLoadFinished Called : " );

        View loadingIndicator = findViewById( R.id.loading_indicator );
        loadingIndicator.setVisibility( View.GONE );

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText( R.string.no_earthquakes );

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll( data );
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<EarthquakeModel>> loader) {
        Log.i( LOG_TAG, " onLoaderReset Called : " );
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

//    /**
//     * {@link AsyncTask} to perform the network request on a background thread, and then
//     * update the UI with the list of earthquakes in the response.
//     *
//     * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
//     * an output type. Our task will take a String URL, and return an Earthquake. We won't do
//     * progress updates, so the second generic is just Void.
//     *
//     * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
//     * The doInBackground() method runs on a background thread, so it can run long-running code
//     * (like network activity), without interfering with the responsiveness of the app.
//     * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
//     * UI thread, so it can use the produced data to update the UI.
//     */
//    public  class EarthquakeAsyncTask extends AsyncTask<String , Void , List<EarthquakeModel>> {
//
//        /**
//         * This method runs on a background thread and performs the network request.
//         * We should not update the UI from a background thread, so we return a list of
//         * {@link EarthquakeModel}s as the result.
//         * @return
//         */
//        @Override
//        protected List<EarthquakeModel> doInBackground(String... urls) {
//            // Don't perform the request if there are no URLs, or the first URL is null
//            if (urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//            List<EarthquakeModel> result = QueryUtils.extractEarthquake(urls[0]);
//            return result;
//        }
//
//
//        /**
//         * This method runs on the main UI thread after the background work has been
//         * completed. This method receives as input, the return value from the doInBackground()
//         * method. First we clear out the adapter, to get rid of earthquake data from a previous
//         * query to USGS. Then we update the adapter with the new list of earthquakes,
//         * which will trigger the ListView to re-populate its list items.
//         */
//        @Override
//        protected void onPostExecute(List<EarthquakeModel> earthquakeModels) {
//            // Clear the adapter of previous earthquake data
//            mAdapter.clear();
//
//            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//            // data set. This will trigger the ListView to update.
//            if (earthquakeModels != null && !earthquakeModels.isEmpty()) {
//                mAdapter.addAll(earthquakeModels);
//            }
//        }
//    }


////
////    class EarthQuakeAsyncTask extends AsyncTask<URL, Void ,EarthquakeModel > {
////
////        @Override
////        protected EarthquakeModel doInBackground(URL... strings) {
////            URL url = createUrl(QueryUtils.SAMPLE_JSON_RESPONSE);
////            String jsonResponse = null;
////
////            // Perform HTTP request to the URL and receive a JSON response back
////            try {
////                 jsonResponse = makeHttpRequest(url);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////
////            QueryUtils.extractEarthquake( jsonResponse );
////            return  null;
////        }
////    }
////
////    private String makeHttpRequest(URL url) throws IOException {
////        String jsonResponse = "";
////
////        if (url == null){
////            return jsonResponse;
////        }
////        HttpURLConnection urlConnection = null;
////        InputStream inputStream = null;
////
////        try {
////            urlConnection = (HttpURLConnection) url.openConnection();
////            urlConnection.setRequestMethod( "GET" );
////            urlConnection.setReadTimeout( 10000 );
////            urlConnection.setConnectTimeout( 15000 );
////
////            //The line of code where We establish connection
////            urlConnection.connect();
////
////            //If the Request was Successfull (Response Code 200 )
////            // then read the input stream and parse the response
////            if (urlConnection.getResponseCode() == 200){
////                //These line od codes is about receiving the response and making sense of it in out app
////                inputStream = urlConnection.getInputStream();
////                jsonResponse = readFromStream(inputStream);
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////            Log.e( LOG_TAG , "Problem retrieving the earthquake JSON result : " , e );
////        }finally {
////            if (urlConnection != null){
////                urlConnection.disconnect();
////            }
////            if (inputStream != null){
////                inputStream.close();
////            }
////        }
////        return jsonResponse;
////    }
////
////    /**
////     * Convert the {@link InputStream} into a String which contains the
////     * whole JSON response from the server.
////     */
////    private String readFromStream(InputStream inputStream) throws IOException {
////        StringBuilder stringBuilder = new StringBuilder(  );
////        if (inputStream != null){
////            //StandardCharsets.UTF_8 it specifies how to translate  the inputStream's raw data into readable character one byte at a time.
////            //that isit knows how to decode each byte in human readable character that is a, b,c
////            //UTF-8 is the Unicode character encoding used for every piece of text we see
////            InputStreamReader inputStreamReader = new InputStreamReader( inputStream , StandardCharsets.UTF_8 );
////
////            //Wrapped inputStreamReader over BufferReaer to read it faster and save a large chunk of data around it
////            BufferedReader bufferedReader = new BufferedReader( inputStreamReader );
////            String line = bufferedReader.readLine();
////            while(line != null){
////                stringBuilder.append( line );
////                line = bufferedReader.readLine();
////            }
////        }
////        return stringBuilder.toString();
////    }
////
////    private URL createUrl(String sampleJsonResponse) {
////        URL url;
////        try {
////            url = new URL(sampleJsonResponse);
////        } catch (MalformedURLException e) {
////            Log.e( LOG_TAG, "Error with creating URL", e );
////            e.printStackTrace();
////            return null;
////        }
////        return url;
////    }
}

