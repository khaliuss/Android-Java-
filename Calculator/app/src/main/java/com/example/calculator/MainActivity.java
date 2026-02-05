package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int s1;
    int s2;
    int res;
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textQuestion = findViewById(R.id.tVTask);
        EditText editTextAnswer = findViewById(R.id.edTxAnswer);
        Button buttonAnswer = findViewById(R.id.buttonAnswer);
        ImageButton updateButton = findViewById(R.id.updateTask);
        TextView incorrectAnswer = findViewById(R.id.tVIncorrect);
        TextView correctAnswer = findViewById(R.id.tVCorrect);
        s1 = 15;
        s2 = 10;
        res = s1+s2;
        text = getString(R.string.math_text,s1,s2,"?");
        textQuestion.setText(text);



        buttonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editText = editTextAnswer.getText().toString();
                int number = Integer.parseInt(editText);
                if (number == res){
                    correctAnswer.setVisibility(View.VISIBLE);
                    incorrectAnswer.setVisibility(View.GONE);
                    text = getString(R.string.math_text,s1,s2,Integer.toString(res));
                    textQuestion.setText(text);
                }else{
                    incorrectAnswer.setVisibility(View.VISIBLE);
                    correctAnswer.setVisibility(View.GONE);
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = new Random().nextInt(100);
                s2 = new Random().nextInt(100);
                res = s1+s2;
                text = getString(R.string.math_text,s1,s2,"?");
                textQuestion.setText(text);
            }
        });






    }
}