package com.gopal.devjunction.hd4kwallpaper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WallpaperJson {

    private static final String TAG = WallpaperJson.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link WallpaperJson} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    public WallpaperJson() {
    }

    /**
     * @param url String api link
     * @return a list of {@link WallpaperModel} object that has been built from parsing
     * json Response.
     */
    public static ArrayList<WallpaperModel> extractWallpaperJson(String url) {

        //creating Url
        URL url1 = createUrl( url );
        String jsonResponse = "";

        try {
            jsonResponse = makeHttpRequest( url1 );

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Creating An empty ArrayList
        ArrayList<WallpaperModel> wallpaperModels = new ArrayList<>();


        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.return null;
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        if (jsonResponse != null) {

            Log.i( TAG, "extractWallpaperJson: " + jsonResponse );
            try {
                JSONObject rootJson = new JSONObject( jsonResponse );

                JSONArray photosArray = rootJson.optJSONArray( "photos" );

                if (photosArray != null)
                    for (int i = 0; i < photosArray.length(); i++) {
                        JSONObject arrayObject = photosArray.optJSONObject( i );

                        String photoUrl = arrayObject.getString( "url" );
                        String photographerName = arrayObject.getString( "photographer" );
                        String photographer_url = arrayObject.getString( "photographer_url" );

                        JSONObject imageSrcObj = arrayObject.getJSONObject( "src" );
                        String originalImageLink = imageSrcObj.getString( "original" );
                        String smallImageLink = imageSrcObj.getString( "small" );
                        String landscapeImageLink = imageSrcObj.getString( "landscape" );

                    //    Bitmap[] streams = getInputStream( originalImageLink, smallImageLink, landscapeImageLink );

                       // Log.i( TAG, "=> " + Arrays.toString( streams ) );

                        wallpaperModels.add( new WallpaperModel( photographerName, photographer_url, originalImageLink,
                                smallImageLink, landscapeImageLink, photoUrl ) );

                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return wallpaperModels;
    }
//    private static Bitmap[] getInputStream(String originalImageLink, String smallImageLink, String landscapeImageLink) {
//
//        URL[] urls = getUrls( originalImageLink, smallImageLink, landscapeImageLink );
//        Bitmap[] jsonResponse;
//
//        jsonResponse = makeHttpRequests( urls );
//
//        return jsonResponse;
//    }
//
//    private static Bitmap[] makeHttpRequests(URL[] urls) {
//        Bitmap[] bitmap = new Bitmap[urls.length];
//
//        for (int i = 0; i < urls.length; i++) {
//            HttpURLConnection urlConnection = null;
//            InputStream inputStream = null;
//
//            try {
//                urlConnection = (HttpURLConnection) urls[i].openConnection();
//                urlConnection.setRequestMethod( "GET" );
//
//                inputStream = urlConnection.getInputStream();
//                bitmap[i] = readFromInput( inputStream );
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        Log.i( "BITMAP", "GOpal" + Arrays.toString( bitmap ) );
//        return bitmap;
//    }
//
//    private static Bitmap readFromInput(InputStream i) {
//
//        BufferedInputStream bufferedInputStream = new BufferedInputStream( i );
//
//        return BitmapFactory.decodeStream( bufferedInputStream );
//    }
//
//
//    private static URL[] getUrls(String originalImageLink, String smallImageLink, String landscapeImageLink) {
//
//        String[] urls = {originalImageLink, smallImageLink, landscapeImageLink};
//        URL[] url = new URL[urls.length];
//
//        for (int i = 0; i < urls.length; i++) {
//            try {
//                url[i] = new URL( urls[i] );
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }
//        return url;
//    }

    private static String makeHttpRequest(URL url1) throws IOException {
        String jsonResponse;

        if (url1 == null) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setRequestProperty( "Authorization", "563492ad6f917000010000016d17a81a71004423b8a336881322a76a" );
            urlConnection.setRequestMethod( "GET" );
            urlConnection.setReadTimeout( 10000 /* milliseconds */ );
            urlConnection.setConnectTimeout( 15000 /* milliseconds */ );

            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream( inputStream );
            } else {
                Log.i( TAG, "Code :" + urlConnection.getResponseCode() );
                jsonResponse = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            jsonResponse = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder jsonObject = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream, StandardCharsets.UTF_8 );
            BufferedReader bufferedReader = new BufferedReader( inputStreamReader );
            String line = bufferedReader.readLine();

            while (line != null) {
                jsonObject.append( line );
                line = bufferedReader.readLine();
            }
        }
        return jsonObject.toString();
    }

    /**
     * @param url String url
     * @return Url
     */
    private static URL createUrl(String url) {
        URL url1;
        try {
            url1 = new URL( url );
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e( TAG, "Error with creating URL", e );
            url1 = null;
        }
        return url1;
    }

}

