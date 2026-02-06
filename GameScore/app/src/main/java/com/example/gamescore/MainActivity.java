package com.example.gamescore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    int scoreTeam1 = 0;
    int scoreTeam2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewScore1 = findViewById(R.id.scoreTeam1);
        TextView textViewScore2 = findViewById(R.id.scoreTeam2);

        if (savedInstanceState != null){
            scoreTeam1 = savedInstanceState.getInt("scoreTeam1");
            scoreTeam2 = savedInstanceState.getInt("scoreTeam2");
        }

        textViewScore1.setText(String.valueOf(scoreTeam1));
        textViewScore2.setText(String.valueOf(scoreTeam2));




        textViewScore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreTeam1++;
                textViewScore1.setText(String.valueOf(scoreTeam1));
            }
        });

        textViewScore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreTeam2++;
                textViewScore2.setText(String.valueOf(scoreTeam2));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("scoreTeam1",scoreTeam1);
        outState.putInt("scoreTeam2",scoreTeam2);
    }
}