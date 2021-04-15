package com.gopal.devjunction.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeModel>>   {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = MainActivity.LOG_TAG;

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public EarthquakeLoader(@NonNull Context context, String url) {
        super( context );
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i( LOG_TAG, "onStartLoading is called : " );
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Nullable
    @Override
    public List<EarthquakeModel> loadInBackground() {
        Log.i( LOG_TAG, "loadInBackground is called : " );
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<EarthquakeModel> earthquakes = QueryUtils.extractEarthquake( mUrl );
        return earthquakes;
    }
}
