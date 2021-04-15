package com.gopal.devjunction.hd4kwallpaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class StaggeredWallpaperAdapter extends RecyclerView.Adapter<StaggeredWallpaperAdapter.WallpaperViewHolder> {

    Context context;
    ArrayList<WallpaperModel> wallpaperModels;

    public StaggeredWallpaperAdapter(Context context, ArrayList<WallpaperModel> wallpaperModels) {
        this.wallpaperModels = wallpaperModels;
        this.context = context;
    }

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.staggered_layout, parent, false );
        return new WallpaperViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {
        holder.setData( wallpaperModels.get( position ).getPhotographerName(), wallpaperModels.get( position ).getPhotoUrl(),
                wallpaperModels.get( position ).getOriginalImageSize(), wallpaperModels.get( position ).getSmallImageSize(),
                wallpaperModels.get( position ).getLandscapeImage(), wallpaperModels.get( position ).getPhotographer_url() );
    }

    @Override
    public int getItemCount() {
        return wallpaperModels.size();
    }

    public static class WallpaperViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView photoImg;

        public WallpaperViewHolder(@NonNull View itemView) {
            super( itemView );
            view = itemView;
            photoImg = view.findViewById( R.id.staggeredImageView );
        }

        /**
         * @param photographerNames photographerNames Name
         */
        private void setData(final String photographerNames, final String photoUrl, final String originalImageSize,
                             String smallerImageSize, final String landScapeUrl, final String photographerUrl) {

            Glide.with( view.getContext() ).load( landScapeUrl ).diskCacheStrategy( DiskCacheStrategy.DATA ).into( photoImg );

            photoImg.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent myIntent = new Intent( itemView.getContext(), FullScreenImageActivity.class );
                    myIntent.putExtra( "photographerNames", photographerNames );
                    myIntent.putExtra( "photoUrl", photoUrl );
                    myIntent.putExtra( "originalImageSize", originalImageSize );
                    myIntent.putExtra( "landScapeUrl", landScapeUrl );
                    myIntent.putExtra( "photographerUrl", photographerUrl );
                    itemView.getContext().startActivity( myIntent );

//                    ActivityOptions options =
//                            ActivityOptions.makeCustomAnimation(itemView.getContext() , R.anim.animation, R.anim.animation);
//                    itemView.getContext().startActivity(myIntent, options.toBundle());

                }
            } );
//            LoadImageAsycnTask asycnTask = new LoadImageAsycnTask();
//            asycnTask.execute( landScapeUrl );
        }
    }
//    static class LoadImageAsycnTask extends AsyncTask<String, Void, Bitmap> {
//
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            URL url = makeUrl( strings[0] );
//
//            Log.i( "LOGGOPAl", "HIP ===" + url );
//
//            Bitmap jsonResponse = null;
//
//            try {
//                jsonResponse = makeHttpRequests( url );
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Log.i( "BITMAPGOPALLL" ,  "gopal" +String.valueOf( jsonResponse ) );
//            return jsonResponse;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            ImageView imageView = views.findViewById( R.id.staggeredImageView );
//            imageView.setImageBitmap( bitmap );
//        }
//
//        private Bitmap makeHttpRequests(URL url) throws IOException {
//
//            Bitmap bitmap = null;
//
//            HttpURLConnection httpURLConnection = null;
//            InputStream inputStream = null;
//
//            try {
//                httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod( "GET" );
//                httpURLConnection.connect();
//
//                inputStream = httpURLConnection.getInputStream();
//                bitmap = readFromStream( inputStream );
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (httpURLConnection != null) {
//                    httpURLConnection.disconnect();
//                }
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//            }
//            return bitmap;
//        }
//
//        private Bitmap readFromStream(InputStream inputStream) {
//
//            BufferedInputStream bufferedInputStream = new BufferedInputStream( inputStream );
//            return BitmapFactory.decodeStream( bufferedInputStream );
//        }
//
//        private URL makeUrl(String strings) {
//
//            URL url = null;
//
//            try {
//                url = new URL( strings );
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            return url;
//        }
//    }
}