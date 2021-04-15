package com.gopal.devjunction.didyoufeelit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Displays the perceived strength of a single earthquake event based on responses from people who
 * felt the earthquake.
 */
public class MainActivity extends AppCompatActivity {

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create an {@link Async} to perform the HTTp request to the given URL
        // on the background thread. When the result is recieved on the main UI thread
        //then Update the Ui
        EarthquakeAsyncTask earthquakeAsyncTask = new EarthquakeAsyncTask();
        earthquakeAsyncTask.execute( USGS_REQUEST_URL );
    }

    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUi(Event earthquake) {
        TextView titleTextView = findViewById(R.id.title);
        titleTextView.setText(earthquake.title);

        TextView tsunamiTextView = findViewById(R.id.number_of_people);
        tsunamiTextView.setText(getString(R.string.num_people_felt_it, earthquake.numOfPeople));

        TextView magnitudeTextView = findViewById(R.id.perceived_magnitude);
        magnitudeTextView.setText(earthquake.perceivedStrength);
    }

    private class EarthquakeAsyncTask extends AsyncTask<String , Void , Event>{

        /**
         * This methods is invoked(or Called) on a background thread, so we can perform long running operation
         * like making network request.
         *
         * It is not okhay to Update the Ui from the Backgroud Thread , so we just return the link object as the result.
         * @param strings input the urn
         * @return {@link Event} object
         */
        @Override
        protected Event doInBackground(String... strings) {
            // Perform the HTTP request for earthquake data and process the response.
            Event earthquake = Utils.fetchEarthquakeData(strings[0]);
            return earthquake;
        }

        /**
         * This methods is invoked on the main UI thread after the background work has been completed
         *
         * Its is ok to modify the UI within this methods we will take {@link Event} object , which was ,
         * returned in doInBackground() methods and update the UI
         * @param event Event object
         */
        @Override
        protected void onPostExecute(Event event) {
            // Update the information displayed to the user.
            updateUi(event);
        }
    }
}