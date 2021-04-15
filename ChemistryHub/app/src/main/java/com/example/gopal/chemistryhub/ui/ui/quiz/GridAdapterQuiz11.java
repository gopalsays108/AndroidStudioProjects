package com.example.gopal.chemistryhub.ui.ui.quiz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gopal.chemistryhub.R;

public class GridAdapterQuiz11 extends BaseAdapter {

    private int sets = 0;

    public GridAdapterQuiz11(int sets) {
        this.sets = sets;
    }

    @Override
    public int getCount() {

         return sets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        View view;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_quiz_11 ,parent, false);
        }else{
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionIntent = new Intent(parent.getContext() , QuestionQuizActivity11.class);
                parent.getContext().startActivity(questionIntent);
            }
        });

        ((TextView)view.findViewById(R.id.set_item_quiz_text_11)).setText(String.valueOf(position+1));
        return view;
    }
}
