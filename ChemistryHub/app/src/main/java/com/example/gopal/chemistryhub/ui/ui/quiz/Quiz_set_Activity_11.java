package com.example.gopal.chemistryhub.ui.ui.quiz;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gopal.chemistryhub.R;

public class Quiz_set_Activity_11 extends AppCompatActivity {

    GridView  gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_set__11);

        Toolbar toolbar = findViewById(R.id.quiz_tool_bar_11);
        gridView = findViewById(R.id.quiz_grid_view_11);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        GridAdapterQuiz11 gridAdapterQuiz11 = new GridAdapterQuiz11(20);
        gridView.setAdapter(gridAdapterQuiz11);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
