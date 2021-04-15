package com.example.courtcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // displayForTeamA(0);
    }
    int score_a = 0;         // global variable

    public void three(View three){
      score_a = score_a + 3;
     displayForTeamA(score_a);

    }

    public void two(View two){
         score_a = score_a + 2;
        displayForTeamA(score_a);
    }

    public void free(View Free){
        score_a = score_a + 1;
       displayForTeamA(score_a);
    }

    private void displayForTeamA(int score_a) {                                     //score_a k jagah sirf score bhi kaam krega
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score_a));
    }
/*  Coding for team B
*/
    int score_b = 0;                         // global variablee for team b

    public void three_b(View three_b) {     // score increse by 3 team b

        score_b = score_b + 3;
        displayForTeamB(score_b);

    }

    public void two_b(View two_b){         // score increse by 2 for team b
        score_b = score_b + 2;
        displayForTeamB(score_b);
    }

    public void free_b(View free_b){      // score incrse by 1 for team b

        score_b = score_b +1;
        displayForTeamB(score_b);
    }

    private void displayForTeamB(int score_b){   // display method for team b to display score for team b

        TextView scoreForB = (TextView) findViewById(R.id.team_b_score);
        scoreForB.setText(String.valueOf(score_b));

    }

    public void reset(View reset){                   //resets score of a and b to zero

        score_b = score_a = 0;
        displayForTeamA(score_a);
        displayForTeamB(score_b);
    }


}

