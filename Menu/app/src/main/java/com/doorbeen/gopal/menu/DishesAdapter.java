package com.doorbeen.gopal.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {

    private List<DishesModel> dishesModels;

    public DishesAdapter(List<DishesModel> dishesModels) {
        this.dishesModels = dishesModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.dishes_category, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setData( dishesModels.get( position ).getUrl(), dishesModels.get( position ).getDish(),
                dishesModels.get( position ).getType(), dishesModels.get( position ).getPrice() );

    }

    @Override
    public int getItemCount() {
        return dishesModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imageView;
        private TextView dish;
        private TextView type;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            dish = itemView.findViewById( R.id.title_dishes );
            imageView = itemView.findViewById( R.id.imageView_dishes );
            type = itemView.findViewById( R.id.type );
            price = itemView.findViewById( R.id.price );

        }

        private void setData(String url, final String dish, String type, String price) {
            Glide.with( itemView.getContext() ).load( url ).into( imageView );
            this.dish.setText( dish );
            this.type.setText( type );
            this.price.setText( price );
        }
    }
}
