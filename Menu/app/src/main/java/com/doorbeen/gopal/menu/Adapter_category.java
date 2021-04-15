package com.doorbeen.gopal.menu;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_category extends RecyclerView.Adapter<Adapter_category.ViewHolder> {

private List<Category_model> category_modelList;

    public Adapter_category(List<Category_model> category_modelList) {
        this.category_modelList = category_modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(  category_modelList.get(position).getUrl(),category_modelList.get(position).getName() ,
                category_modelList.get( position ).getAddress() , category_modelList.get( position ).getCity() ,
                category_modelList.get( position ).getKey());

    }

    @Override
    public int getItemCount() {
        return category_modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView circleImageView;
        private TextView title;
        private TextView address;
        private TextView city;

        public ViewHolder(@NonNull View itemView) {
         super(itemView);
        circleImageView = itemView.findViewById(R.id.imageView_categories);
        title = itemView.findViewById(R.id.title_categories);
        address = itemView.findViewById( R.id.address );
        city = itemView.findViewById( R.id.city );

        }

        private void setData(String url , final String title , String address , String city , final String key){
            Glide.with(itemView.getContext()).load(url).into(circleImageView);
            this.title.setText(title);
            this.address.setText( address );
            this.city.setText( city );


            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( itemView.getContext() , Dishes.class );
                    intent.putExtra( "title" , title );
                    intent.putExtra( "key" , key );
                   // intent.putExtra( "sets" , sets );
                    itemView.getContext().startActivity( intent );

                }
            } );

        }
    }
}
