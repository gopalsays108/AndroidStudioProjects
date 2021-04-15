package com.example.scientificcalculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView calculation;
    TextView answer;
    String sCalculation = "";
    String sAnswer = "";
    String number_one = "", number_two = "";
    String currentOperator = "", prev_ans = "";
    Double numberOne = 0.0, Result = 0.0, temp = 0.0, numberTwo = 0.0;
    boolean dot_present = false, number_allow = true, root_present = false, invert_allow = true, power_present = false;
    boolean isFirstTime = true;
    boolean lastTime = true;

//    DecimalFormat  format , longFormat;

    DecimalFormat formatter = new DecimalFormat("#.####");
    DecimalFormat longFormat = new DecimalFormat("#0.#E0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculation = findViewById(R.id.calculation);

        //set movement to text view
        calculation.setMovementMethod(new ScrollingMovementMethod());

        //initilise answer
        answer = findViewById(R.id.answer);


        //we want answer till 4 decimal places
        //  format = new DecimalFormat("#.####");
        //  //if its  long
        //longFormat = new DecimalFormat("0.#Σ0");
    }

    public void onClickNumber(View v) {
        //finding which button is clicked
        if (number_allow) {
            Button bn = (Button) v;
            sCalculation += bn.getText();
            number_one += bn.getText();
            numberOne = Double.parseDouble(number_one);

            //checking root is present
            if (root_present) {
                numberOne = Math.sqrt(numberOne);
            }
            switch (currentOperator) {

                case "": //if it is null
                    if (power_present) {
                        temp = Result + Math.pow(numberTwo, numberOne);
                    } else {
                        temp = Result + numberOne;

                    }
                    break;

                case "+":
                    if (power_present) {
                        temp = Result + Math.pow(numberTwo, numberOne);
                    } else {
                        temp = Result + numberOne;

                    }
                    break;

                case "-":
                    if (power_present) {
                        temp = Result - Math.pow(numberTwo, numberOne);
                    } else {
                        temp = Result - numberOne;

                    }
                    break;

                case "x":
                    if (power_present) {
                        temp = Result * Math.pow(numberTwo, numberOne);
                    } else {
                        temp = Result * numberOne;

                    }
                    break;

                case "/":
                    try {
                        if (power_present) {
                            temp = Result / Math.pow(numberTwo, numberOne);
                        } else {
                            temp = Result / numberOne;

                        }
                        break;
                    } catch (Exception e) {
                        Toast.makeText(this, "DIVISION BY ZERO(0) NOT POSSIBLE", Toast.LENGTH_SHORT).show();
                    }

            }
            sAnswer = formatter.format(temp).toString();
            updateCalculation();
        }
    }

    public void onClickOperator(View viii) {
        Button ob = (Button) viii;
        //if answer is null means no cal needed

        if (!sAnswer.equals("")) {

            //we check last char is operatorr or not
            if (!currentOperator.equals("")) {

                char c = getCharFromLast(sCalculation, 2); // 2 is the char from the last bcz our last char  is " "

                if (c == '+' || c == '-' || c == 'x' || c == '/') { // battery dead

                    sCalculation = sCalculation.substring(0, sCalculation.length() - 3);
                }
            }
            sCalculation += "\n" + ob.getText() + " ";
            number_one = "";
            //numberOne=0.0;
            Result = temp;
            //temp = 0.0;
            // sAnswer = formatter.format(temp);
            currentOperator = ob.getText().toString();
            updateCalculation();

            //when operator click dot is not present n number_one
            dot_present = false;
            number_allow = true;
            root_present = false;
            invert_allow = true;
            power_present = false;
            number_two = "";
            numberTwo = 0.0;

        } else {
            Toast.makeText(getApplicationContext(), "PLEASE ENTER INPUT NUMBER", Toast.LENGTH_SHORT).show();
        }
    }

    private char getCharFromLast(String s, int i) {
        char c = s.charAt(s.length() - i);    //we can also writr return s.charAt(s.lenghth() - 2); to reduce redundancy
        return c;
    }

    public void onClickClear(View v) {
        sCalculation = "";
        sAnswer = "";
        currentOperator = "";
        number_one = "";
        number_two = "";
        numberTwo = 0.0;
        Result = 0.0;
        prev_ans = "";
        numberOne = 0.0;
        temp = 0.0;
        updateCalculation();
        dot_present = false;
        number_allow = true;
        root_present = false;
        invert_allow = true;
        power_present = false;


    }

    public void updateCalculation() {

        calculation.setText(sCalculation);
        answer.setText((sAnswer));
    }

    public void onDotClick(View view) {

        //created boolean dot_present variable to check whether dot is present or not

        if (!dot_present) {

            //check length of numberone
            if (number_one.length() == 0) {
                number_one += "0.";
                sCalculation += "0.";
                sAnswer += "0.";
                dot_present = true;
                updateCalculation();
            } else {
                number_one += ".";
                sAnswer += ".";
                sCalculation += ".";
                dot_present = true;
                updateCalculation();
            }
        }
    }

    public void onClickEqual(View view) {

        showResult();
    }

    public void showResult() {
        if (!sAnswer.equals("") && !sAnswer.equals(prev_ans)) {
            sCalculation += "\n-----------\n" + sAnswer + " ";
            number_one = "";
            numberOne = 0.0;
            numberTwo = 0.0;
            number_two = "";
            Result = temp;
            prev_ans = sAnswer;
            power_present = false;
            //temp = 0.0;
            updateCalculation();

            //we dont allow our answer to edit
            dot_present = true;
            number_allow = false;

        } else Toast.makeText(this, " INVALID CHOICE", Toast.LENGTH_SHORT).show();
    }

    public void onModuloClick(View view) {

        if (!sAnswer.equals("") && getCharFromLast(sCalculation, 1) != ' ') {
            sCalculation += "%";

            //value of temp will change according to operator

            switch (currentOperator) {

                case "":
                    temp = temp / 100;
                    break;

                case "+":
                    temp = Result + ((Result * numberOne) / 100);
                    break;

                case "-":
                    temp = Result - ((Result * numberOne) / 100);
                    break;

                case "x":
                    temp = Result * (numberOne / 100);
                    break;

                case "/":
                    try {
                        temp = Result / (numberOne / 100);
                    } catch (Exception e) {
                        sAnswer = e.getMessage();
                    }

                    break;

            }
            sAnswer = formatter.format(temp).toString();
            if (sAnswer.length() > 9) {

                sAnswer = longFormat.format(temp).toString();
            }
        }
        Result = temp;
        //updateCalculation();
        // now we show the result
        showResult();

    }

    public void onPMclick(View view) {

        if (invert_allow) {
            if (!sAnswer.equals("") && getCharFromLast(sCalculation, 1) != ' ') {

                numberOne = numberOne * (-1);
                number_one = formatter.format(numberOne).toString();
                switch (currentOperator) {

                    case "":
                        temp = numberOne;
                        sCalculation += number_one;
                        break;

                    case "+":
                        temp = Result + numberOne;
                        //adding sign to starting of string
                        removeUntilChar(sCalculation, ' ');
                        sCalculation += number_one;
                        break;

                    case "-":
                        temp = Result - numberOne;
                        //adding sign to starting of string
                        removeUntilChar(sCalculation, ' ');
                        sCalculation += number_one;
                        break;

                    case "x":
                        temp = Result * numberOne;
                        //adding sign to starting of string
                        removeUntilChar(sCalculation, ' ');
                        sCalculation += number_one;
                        break;

                    case "/":
                        try {
                            temp = Result / numberOne;
                            //adding sign to starting of string
                            removeUntilChar(sCalculation, ' ');
                            sCalculation += number_one;
                        } catch (Exception e) {
                            sAnswer = e.getMessage();
                        }
                        break;
                }
                sAnswer = formatter.format(temp).toString();
                updateCalculation();
            }
        }
    }

    public void removeUntilChar(String str, char chr) {

        char c = getCharFromLast(str, 1);
        if (c != chr) {
            //remove last char
            str = removeChar(str, 1);
            sCalculation = str;
            updateCalculation();
            removeUntilChar(str, chr);
        }
    }

    public String removeChar(String str, int i) {

        char c = str.charAt(str.length() - 1);
        // checks if dot is removed or not
        if (c == '.' && !dot_present) {
            dot_present = false;
        }
        if (c == ' ') {
            return str.substring(0, str.length() - (i - 1));
        }

        return str.substring(0, str.length() - 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isFirstTime)
            Toast.makeText(getApplicationContext(), "WELCOME", Toast.LENGTH_LONG).show();
        isFirstTime = false;
    }

    //shared preference
    @Override
    protected void onStop() {
        super.onStop();
        if (lastTime)
            Toast.makeText(getApplicationContext(), "Thank You", Toast.LENGTH_LONG).show();
        lastTime = false;
    }

    public void onRootClick(View view) {

        Button root = (Button) view;
        if (sAnswer.equals("") && Result == 0 && !root_present) {
            sCalculation = root.getText().toString();
            root_present = true;
            invert_allow = false;
            updateCalculation();
        } else if (getCharFromLast(sCalculation, 1) == ' ' && !currentOperator.equals("") && !root_present) {
            sCalculation += root.getText().toString();
            root_present = true;
            invert_allow = false;
            updateCalculation();
        }
    }

    public void onPowerClick(View view) {
        Button power = (Button) view;

        if (!sCalculation.equals("") && !root_present && !power_present) {

            if (getCharFromLast(sCalculation, 1) != ' ') {
                sCalculation += power.getText().toString();

                //we need
                number_two = number_one;
                numberTwo = numberOne;
                number_one = "";
                power_present = true;
                updateCalculation();
            }
        }
    }


    public void onClickSquare(View view) {
        if (!sCalculation.equals("") && !power_present && getCharFromLast(sCalculation, 1) != ' ') {

            numberOne = numberOne * numberOne;
            number_one = formatter.format(numberOne).toString();
            if (currentOperator.equals("")) {
                if(number_one.length()> 9){
                    number_one = longFormat.format(temp);
                }
                sCalculation = number_one;
                temp = numberOne;
            } else {
                switch (currentOperator) {
                    case "+":
                        temp = Result + numberOne;
                        break;
                    case "-":
                        temp = Result - numberOne;
                        break;
                    case "x":
                        temp = Result * numberOne;
                        break;
                    case "/":
                        try {
                            temp = Result / numberOne;
                            break;
                        } catch (Exception e) {
                            sAnswer = e.getMessage();
                        }
                        break;
                }
                removeUntilChar(sCalculation, ' ');
                sCalculation += number_one;
            }
            sAnswer = formatter.format(temp);
            if (sAnswer.length() > 9) {
                sAnswer = longFormat.format(temp);
            }
            updateCalculation();
        }
    }

}
