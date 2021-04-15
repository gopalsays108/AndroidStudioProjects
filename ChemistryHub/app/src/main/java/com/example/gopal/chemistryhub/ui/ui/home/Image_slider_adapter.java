package com.example.gopal.chemistryhub.ui.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.gopal.chemistryhub.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class Image_slider_adapter extends SliderViewAdapter<SliderViewHolder> {

    HomeFragment context;
    int setTotalCount;
    String imageLink;

    //List<Image_slider_model> image_slider_models_list;

    public Image_slider_adapter(HomeFragment context , int setTotalCount) {  //List<Image_slider_model> image_slider_models_list
        this.context = context;
        this.setTotalCount = setTotalCount;
        //this.image_slider_models_list = image_slider_models_list;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SliderViewHolder viewHolder, final int position) {
      //  viewHolder.sliderImageView.setImageResource(image_slider_models_list.get(position).getImage());  for custom image
        FirebaseDatabase.getInstance().getReference("ImagesLinks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                switch (position){
                    case 0 :
                        imageLink = dataSnapshot.child("1").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageLink).into(viewHolder.sliderImageView);
                        break;

                    case 1 :
                        imageLink = dataSnapshot.child("2").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageLink).into(viewHolder.sliderImageView);
                        break;
                    case 2 :
                        imageLink = dataSnapshot.child("3").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageLink).into(viewHolder.sliderImageView);
                        break;
                    case 3 :
                        imageLink = dataSnapshot.child("4").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageLink).into(viewHolder.sliderImageView);
                        break;
                    case 4 :
                        imageLink = dataSnapshot.child("5").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageLink).into(viewHolder.sliderImageView);
                        break;
                    case 5 :
                        imageLink = dataSnapshot.child("6").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageLink).into(viewHolder.sliderImageView);
                        break;
                    case 6 :
                        imageLink = dataSnapshot.child("7").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageLink).into(viewHolder.sliderImageView);
                        break;
                    case 7 :
                        imageLink = dataSnapshot.child("8").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageLink).into(viewHolder.sliderImageView);
                        break;
                    case 8 :
                        imageLink = dataSnapshot.child("9").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageLink).into(viewHolder.sliderImageView);
                        break;
                    case 9 :
                        imageLink = dataSnapshot.child("10").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageLink).into(viewHolder.sliderImageView);
                        break;
                    case 10 :
                        imageLink = dataSnapshot.child("11").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageLink).into(viewHolder.sliderImageView);
                        break;

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

    @Override
    public int getCount() {
        //return image_slider_models_list.size();
        return setTotalCount;
    }
}

class SliderViewHolder extends SliderViewAdapter.ViewHolder {
    ImageView sliderImageView;
    View itemView;
    public SliderViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        sliderImageView = itemView.findViewById(R.id.slider_image_view);
    }
}