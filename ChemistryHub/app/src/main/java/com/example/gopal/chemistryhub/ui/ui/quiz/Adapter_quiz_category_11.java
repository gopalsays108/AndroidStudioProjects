package com.example.gopal.chemistryhub.ui.ui.quiz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gopal.chemistryhub.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_quiz_category_11 extends RecyclerView.Adapter<Adapter_quiz_category_11.ViewHolder> {

    private List<CategoryModel> categoryModelList;

    public Adapter_quiz_category_11(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_11_grade , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(categoryModelList.get(position).getUrl() , categoryModelList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.categry_item_image_11);
            textView = itemView.findViewById(R.id.catergory_title11);

        }

        private void setData (String url , final String title){
                        Glide.with(itemView.getContext()).load(url).into(imageView);
            this.textView.setText(title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext() ,Quiz_set_Activity_11.class);
                    intent.putExtra("title" , title);
                    itemView.getContext().startActivity(intent);

                }
            });
        }
    }
}
