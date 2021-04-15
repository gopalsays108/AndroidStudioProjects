package com.doorbeen.gopal.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.viewHolder> {

    private List<PhotoModel> photoModelList;

    public PhotoAdapter(List<PhotoModel> photoModelList) {
        this.photoModelList = photoModelList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.photo_category  , parent ,false);
        return new viewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.setData( photoModelList.get( position ).getUrl() );

    }

    @Override
    public int getItemCount() {
        return photoModelList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public viewHolder(@NonNull View itemView) {
            super( itemView );
            imageView = itemView.findViewById( R.id.menu_image );

        }

        private void setData(String url){
            Glide.with( itemView.getContext() ).load( url ).into( imageView );
        }
    }
}
