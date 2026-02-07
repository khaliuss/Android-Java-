package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private FloatingActionButton addTaskFButton;
    private static final String NOTE = "note";
    private static final String NOTES = "notes";
    private static final String PRIORITY = "priority";
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main","OnCreate");
        init();

        addTaskFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTask();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(NOTES,database.getNotes());
    }

    private void showNotes() {
        if (!database.getNotes().isEmpty()){
            for (Note note : Database.getInstance().getNotes()){
                View view =  getLayoutInflater().inflate(R.layout.text_view,linearLayout,false);
                TextView textView = view.findViewById(R.id.note_text_view);
                textView.setText(note.getText());
                textView.setBackgroundColor(getColor(note.getPriority()));
                linearLayout.addView(view);
            }
        }
    }

    public static Intent newIntent(Context context,Note note) {
        return new Intent(context,MainActivity.class);
    }

    private void addNewTask() {
        Intent intent  = NewTaskActivity.newIntent(this);
        startActivity(intent);
    }

    private void init(){
        addTaskFButton  = findViewById(R.id.addTaskFButton);
        linearLayout  = findViewById(R.id.linearLayoutNotes);
        database = Database.getInstance();
    }
}