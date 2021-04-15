package com.example.gopal.chemistryhub.ui.ui.quiz;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.gopal.chemistryhub.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QuestionQuizActivity11 extends AppCompatActivity {

    private TextView question , numberIndicator;
    private FloatingActionButton bookmarks11;
    private LinearLayout linearLayoutOption;
    private Button share;
    private Button next;
    private int count = 0;
    private List<QuestionModel11> list;
    private int position = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_quiz11);

        Toolbar toolbar = findViewById(R.id.QuestionToolbar11);
        question = findViewById(R.id.quiz_question_11);
        bookmarks11 = findViewById(R.id.bookmark_btn_11);
        numberIndicator = findViewById(R.id.number_indicator_11);
        linearLayoutOption = findViewById(R.id.option_container_quiz_11);
        share = findViewById(R.id.share_btn_quiz_11);
        next = findViewById(R.id.next_question_btn_quiz_11);

        list = new ArrayList<>();

        list.add(new QuestionModel11("question 1" , "a" ,"b" , "c" , "d" , "a"));
        list.add(new QuestionModel11("question 2" , "a" ,"b" , "c" , "d" , "a"));
        list.add(new QuestionModel11("question 3" , "a" ,"b" , "c" , "d" , "b"));
        list.add(new QuestionModel11("question 4" , "a" ,"b" , "c" , "d" , "a"));
        list.add(new QuestionModel11("question 5" , "a" ,"b" , "c" , "d" , "b"));
        list.add(new QuestionModel11("question 6" , "a" ,"b" , "c" , "d" , "c"));


        for (int i = 0 ; i<4 ; i++){
                linearLayoutOption.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    checkAnswer((Button) v);
                    }
                });
        }



        playAnim(question , 0 ,list.get(position).getQuestion());
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(false);
                next.setAlpha(0.6f);
                enableOption(true);
                position++;
                if (position == list.size()){
                    /////score Activity
                    return;
                }
                count = 0;
                playAnim(question , 0 ,list.get(position).getQuestion());
            }
        });
    }

    private  void playAnim(final View view , final int value , final String data){

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                ((TextView)view).setText(data);
                if(value == 0 && count <4 ){

                    String option = "";

                    if(count == 0 ){
                        option = list.get(position).getOptionA();

                    }else if(count == 1){
                        option = list.get(position).getOptionB();
                    }else if(count == 2){
                        option = list.get(position).getOptionC();

                    }else if(count == 3 ){
                        option = list.get(position).getOptionD();
                    }
                    playAnim(linearLayoutOption.getChildAt(count) , 0 , option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                //data change

                if (value == 0 ) {
                    try {
                        ((TextView)view).setText(data);
                        numberIndicator.setText(position + 1 + "/" + list.size());
                    }catch (Exception e){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view, 1 , data);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {




            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void checkAnswer( Button selectedAnswer){
        enableOption(false);
        next.setEnabled(true);
        next.setAlpha(1);

        if(selectedAnswer.getText().toString().equals(list.get(position).getCorrectAnswer())){
                //correct
                selectedAnswer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                score++;
        }else{
            //incorrect
            selectedAnswer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            //to show right answer
            Button correctOption =(Button)  linearLayoutOption.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
    }
    private void enableOption(boolean enable ){

        for (int i = 0 ; i< 4 ;i++){
            linearLayoutOption.getChildAt(i).setEnabled(enable);
            if(enable){
                linearLayoutOption.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
            }
        }


    }
}
