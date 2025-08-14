package com.example.calculator_1;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    String firstnumber= "", secondnumber= "", operator= "";
    boolean isnegativenumber = false;

    TextView solutionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // display solution on screen
        solutionTv = findViewById(R.id.solutionTv);

        // buttons declaration
        Button button25 = findViewById(R.id.button25);
        Button button26 = findViewById(R.id.button26);
        Button button27 = findViewById(R.id.button27);
        Button button28 = findViewById(R.id.button28);
        Button button29 = findViewById(R.id.button29);
        Button button30 = findViewById(R.id.button30);
        Button button31 = findViewById(R.id.button31);
        Button button32 = findViewById(R.id.button32);
        Button button33 = findViewById(R.id.button33);
        Button button34 = findViewById(R.id.button34);
        Button button35 = findViewById(R.id.button35);
        Button button36 = findViewById(R.id.button36);
        Button button37 = findViewById(R.id.button37);
        Button button38 = findViewById(R.id.button38);
        Button button39 = findViewById(R.id.button39);
        Button button40 = findViewById(R.id.button40);
        Button button41 = findViewById(R.id.button41);
        Button button42 = findViewById(R.id.button42);
        Button button43 = findViewById(R.id.button43);
        Button button44 = findViewById(R.id.button44);



        //  clear button
        button25.setOnClickListener(v -> clearALL());
        // calculate (equal button)
        button44.setOnClickListener(v -> calculate());
        //  clear one number
        button26.setOnClickListener(v -> clearOneNumber());
        // negative number
        button27.setOnClickListener(v -> negativeNumber());

        //  numeric buttons
        button37.setOnClickListener(v -> appeandNumber("1"));
        button39.setOnClickListener(v -> appeandNumber("2"));
        button38.setOnClickListener(v -> appeandNumber("3"));
        button33.setOnClickListener(v -> appeandNumber("4"));
        button34.setOnClickListener(v -> appeandNumber("5"));
        button35.setOnClickListener(v -> appeandNumber("6"));
        button29.setOnClickListener(v -> appeandNumber("7"));
        button30.setOnClickListener(v -> appeandNumber("8"));
        button31.setOnClickListener(v -> appeandNumber("9"));
        button42.setOnClickListener(v -> appeandNumber("0"));
        button43.setOnClickListener(v -> appeandNumber("."));


        //  operator buttons
        button36.setOnClickListener(v -> setOperator("-"));
        button32.setOnClickListener(v -> setOperator("*"));
        button28.setOnClickListener(v -> setOperator("/"));
        button40.setOnClickListener(v -> setOperator("+"));
        button41.setOnClickListener(v -> setOperator("%"));

    }
    // for numeric buttons
    private void appeandNumber(String numStr) {
        if (solutionTv == null) return;
        if (operator.isEmpty()) {
            if (numStr.equals(".") && firstnumber.contains(".")) return;
            if (firstnumber.equals("0") && !numStr.equals(".")) firstnumber = "";
            if (firstnumber.equals("-0") && !numStr.equals(".")) firstnumber = "-";
            firstnumber += numStr;
            solutionTv.setText(firstnumber);
        } else {
            if (numStr.equals(".") && secondnumber.contains(".")) {
                String content = secondnumber;
                if (content.startsWith("(-") && content.endsWith(")")) {
                    content = content.substring(2, content.length() -1);
                } else if (content.startsWith("(-")) {
                    content = content.substring(2);
                }
                if(content.contains(".")) return;
            }
            if (secondnumber.equals("(-")) {
                if (numStr.equals(".")) {
                    secondnumber += "0" + numStr;

                } else {
                    secondnumber += numStr;
                }
            } else if (secondnumber.endsWith(")")) {
                secondnumber = secondnumber.substring(0, secondnumber.length() - 1) + numStr + ")";
            } else {
                if (secondnumber.equals("0") && !numStr.equals(".")) secondnumber = "";
                secondnumber += numStr;
            } if(isnegativenumber==true){

                secondnumber = secondnumber.substring(0, secondnumber.length()) +  ")";
                isnegativenumber = false;
                return;
            }
            solutionTv.setText(firstnumber + " " + operator + " " + secondnumber);

        }
    }
    // for setting operator
    private void setOperator(String op) {
        if (!firstnumber.isEmpty()) {

            if (!secondnumber.isEmpty() && !operator.isEmpty()) {
                calculate();
            }
            operator = op;
            if (solutionTv != null) {
                solutionTv.setText(firstnumber + " " + operator);
            }
        }
    }

   // calculation logic ( trigger on pressed equal button)
    private void calculate() {
        if (!firstnumber.isEmpty() && !operator.isEmpty()) {
            if (secondnumber.isEmpty() || secondnumber.equals("(-") || secondnumber.equals("(-)")) {
                return;
            }
            double result = 0;
            boolean error = false;
            double num1 = 0;
            double num2 = 0;
            try {
                num1 = Double.parseDouble(firstnumber);
                String secondNumStr = secondnumber;
                if (secondNumStr.startsWith("(-") && secondNumStr.endsWith(")")) {
                    secondNumStr = "-" + secondNumStr.substring(2, secondNumStr.length() - 1);
                }
                if (secondNumStr.equals("-")) {
                    solutionTv.setText("Error: Invalid input");
                    return;
                }
                num2 = Double.parseDouble(secondNumStr);

            } catch (NumberFormatException e) {
                if (solutionTv != null) {
                    solutionTv.setText("Error: Invalid number");
                }
                return;
            }

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        if (solutionTv != null) {
                            solutionTv.setText("Error: Div by zero");
                        }
                        error = true;
                        return;
                    }
                    break;
                    case "%":
                    result = num1 % num2;
                    break;
                default:
                    if (solutionTv != null) {
                        solutionTv.setText("Error: Invalid op");
                    }
                    error = true;
                    return;
            }

            if (!error) {
                String resultString;
                if (result == (long) result) {
                    resultString = String.format("%d", (long) result);
                } else {
                    resultString = String.format("%s", result).replaceAll("(\\.\\d*?)0+$", "$1").replaceAll("\\.$", "");
                }
                if (solutionTv != null) {
                    solutionTv.setText(resultString);
                }
                firstnumber = resultString;
                secondnumber = "";
                operator = "";
            }
        } else {
            if (solutionTv != null) {
                solutionTv.setText("Error: Incomplete");
            }
        }
    }

    // for clear button
    private void clearALL(){
        firstnumber = "";
        secondnumber = "";
        operator = "";
        solutionTv.setText("");
    }

    // for clear one number
    private void clearOneNumber(){
        if (operator.isEmpty()) {
            firstnumber = firstnumber.substring(0, firstnumber.length() - 1);
            solutionTv.setText(firstnumber);
        }
    }


    // for negative sign
    private void negativeNumber() {
        if (solutionTv == null) return;
        if (operator.isEmpty()) {
            if (!firstnumber.isEmpty()) {
                if (firstnumber.startsWith("-")) {
                    firstnumber = firstnumber.substring(1);
                } else {
                    firstnumber = "-" + firstnumber;
                }
                solutionTv.setText(firstnumber);
            } else {
                firstnumber = "-";
                solutionTv.setText(firstnumber);
            }
        } else {
            if (!secondnumber.isEmpty()) {
                if (secondnumber.startsWith("(-") && secondnumber.endsWith(")")) {
                    secondnumber = secondnumber.substring(2, secondnumber.length() - 1);
                } else if (secondnumber.startsWith("-")) {
                    secondnumber = secondnumber.substring(1);
                }
                else {
                    secondnumber = "(-" + secondnumber + ")";
                }
            } else {
                secondnumber = "(-";
            }
            solutionTv.setText(firstnumber + " " + operator + " " + secondnumber);
            if( firstnumber.startsWith("(-") ||  secondnumber.startsWith("(-")){
                isnegativenumber = true;
            }else{
                isnegativenumber = false;
            }
        }
    }
}
