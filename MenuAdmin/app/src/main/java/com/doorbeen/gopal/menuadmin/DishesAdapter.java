package com.doorbeen.gopal.menuadmin;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {

    private List<DishesModel> dishesModels;
    private DeleteListners deleteListners;
    public DishesAdapter(List<DishesModel> dishesModels , DeleteListners deleteListners){
        this.dishesModels = dishesModels;
        this.deleteListners = deleteListners;
    }

    public DishesAdapter(List<DishesModel> listDish) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from( parent.getContext()).inflate( R.layout.dishes_category  , parent , false);
        return new ViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setData( dishesModels.get(position).getUrl(),dishesModels.get(position).getDish() ,
                dishesModels.get( position ).getType() , dishesModels.get( position ).getPrice() ,
                dishesModels.get( position ).getKey() , position);

    }

    @Override
    public int getItemCount() {
        return dishesModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView imageView;
        private TextView dish;
        private TextView type;
        private TextView price;
        private ImageButton delete;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            dish = itemView.findViewById( R.id.title_dishes );
            imageView = itemView.findViewById( R.id.imageView_dishes );
            type = itemView.findViewById( R.id.type );
            price = itemView.findViewById( R.id.price );
            delete = itemView.findViewById( R.id.delete_dish );

        }

        private void setData(final String url , final String dish , final String type , final String price , final String key,
                             final int position ){
            Glide.with(itemView.getContext() ).load( url ).into( imageView );
            this.dish.setText( dish );
            this.type.setText( type );
            this.price.setText( price );

            delete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListners.onDeltes( key , position);
                }
            } );

            imageView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( itemView.getContext() , DishChangeImageActivity.class );
                    intent.putExtra( "key", key );
                    intent.putExtra( "name" , dish );
                    intent.putExtra( "url" , url );
                    intent.putExtra( "type" , type );
                    intent.putExtra( "price" , price );
                    intent.putExtra( "keyRes", dishesModels.get( position ).getKeyRes() );
                    itemView.getContext().startActivity( intent );
                }
            } );

        }
    }

    public interface DeleteListners{
        public void onDeltes(String key , int position);
    }


}
