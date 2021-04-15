package com.gopal.devjunction.hd4kwallpaper;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StaggeredWallpaperAdapter staggeredWallpaperAdapter;
    public ArrayList<WallpaperModel> wallpaperModel;
    int pageNumber = 1;

    private String JSON_API_LINK = "https://api.pexels.com/v1/curated?page=" + pageNumber + "&per_page=80   ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        recyclerView = findViewById( R.id.wallpaperRecyclerView );

        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL );
        staggeredGridLayoutManager.setGapStrategy( StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS );
        staggeredGridLayoutManager.setOrientation( RecyclerView.VERTICAL );

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( staggeredGridLayoutManager );

        wallpaperModel = new ArrayList<>();

        recyclerView.setItemAnimator( new DefaultItemAnimator() );

        staggeredWallpaperAdapter = new StaggeredWallpaperAdapter( this, wallpaperModel );

        recyclerView.setAdapter( staggeredWallpaperAdapter );

        recyclerView.addOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled( recyclerView, dx, dy );

                boolean isReachedAtBottom = !recyclerView.canScrollVertically( 1 ); // 1 for reached at bottom or not

                if (isReachedAtBottom) {
                    pageNumber++;

                    String s1 = "https://api.pexels.com/v1/curated?page=";
                    String s2 = String.valueOf( pageNumber );
                    String s3 = "&per_page=80";
                    String finalString = s1+s2+s3;

                    WallpaperAsyncTask task = new WallpaperAsyncTask();
                    task.execute( finalString );
                }
            }
        } );

        WallpaperAsyncTask task = new WallpaperAsyncTask();
        Log.i( "COUNT", JSON_API_LINK );
        task.execute( JSON_API_LINK );
    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the list of earthquakes in the response.
     * <p>
     * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
     * an output type. Our task will take a String URL, and return an Earthquake. We won't do
     * progress updates, so the second generic is just Void.
     * <p>
     * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
     * The doInBackground() method runs on a background thread, so it can run long-running code
     * (like network activity), without interfering with the responsiveness of the app.
     * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
     * UI thread, so it can use the produced data to update the UI.
     */
    @SuppressLint("StaticFieldLeak")
    public class WallpaperAsyncTask extends AsyncTask<String, Void, ArrayList<WallpaperModel>> {

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link WallpaperModel}s as the result.
         */
        @Override
        protected ArrayList<WallpaperModel> doInBackground(String... urls) {

            //Do not perform if there is no Url
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            ArrayList<WallpaperModel> wallpaperModels = WallpaperJson.extractWallpaperJson( urls[0] );
            return wallpaperModels;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of earthquake data from a previous
         * query to USGS. Then we update the adapter with the new list of earthquakes,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(ArrayList<WallpaperModel> wallpaperModels) {
            if (wallpaperModels != null && !wallpaperModels.isEmpty()) {
                wallpaperModel.addAll( wallpaperModels );
                staggeredWallpaperAdapter.notifyDataSetChanged();
            }
        }
    }
}