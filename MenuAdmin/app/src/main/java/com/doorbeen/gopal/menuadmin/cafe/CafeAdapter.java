package com.doorbeen.gopal.menuadmin.cafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doorbeen.gopal.menuadmin.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.viewHolder> {

    private List<CafeModel> cafeModelList;

    public CafeAdapter(List<CafeModel> cafeModelList) {
        this.cafeModelList = cafeModelList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from( parent.getContext() ).inflate( R.layout.main_category_item , parent , false);
        return new viewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.setData(cafeModelList.get( position ).getUrl() , cafeModelList.get( position ).getName() ,
                cafeModelList.get( position ).getAddress() , cafeModelList.get( position ).getCity());
    }


    @Override
    public int getItemCount() {
        return cafeModelList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        private CircleImageView circleImageView;
        private TextView title;
        private TextView address;
        private TextView city;
      //  private ImageButton delete;

        public viewHolder(@NonNull View itemView) {
            super( itemView );
            circleImageView = itemView.findViewById(R.id.imageView_cafe);
            title = itemView.findViewById(R.id.title_categories_cafe);
            address = itemView.findViewById( R.id.address_cafe );
            city = itemView.findViewById( R.id.city_cafe );
          //  delete = itemView.findViewById( R.id.delete_cafe );

        }

        private void setData(String url , String title , String address , String  city){
            Glide.with( itemView.getContext() ).load( url ).into( circleImageView );
            this.title.setText( title );
            this.address.setText(address );
            this.city.setText( city );

    }

    }


}
