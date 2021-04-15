package com.doorbeen.gopal.menuadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

class photo_adapter extends RecyclerView.Adapter<photo_adapter.Viewholder> {


    private List<PhotoModel>photoModelList ;
    private DeleteListners  deleteListners;

    public photo_adapter(List<PhotoModel> photoModelList , DeleteListners deleteListners) {
        this.deleteListners = deleteListners;
        this.photoModelList = photoModelList;
    }



    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.photo_category  , parent , false);
        return new Viewholder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.setData( photoModelList.get( position ).getUrl() , photoModelList.get( position ).getKey() ,
                position);

    }

    @Override
    public int getItemCount() {
        return photoModelList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private ImageButton delete;

        public Viewholder(@NonNull View itemView) {
            super( itemView );
            delete = itemView.findViewById( R.id.delete_photo );
            imageView = itemView.findViewById( R.id.menu_image );
        }

        private void setData(String url , final String key , final int position){
            Glide.with( itemView.getContext() ).load( url ).into( imageView );


            delete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListners.onDeletes( key , position);
                }
            } );


        }

    }
    public interface DeleteListners{
        public void onDeletes(String key , int position);
    }


}
