package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewTaskActivity extends AppCompatActivity {

    private Button saveButton;

    private EditText editText;
    private RadioGroup radioGroup;
    private RadioButton medium;
    private RadioButton high;
    private NoteDataBase noteDataBase;

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        init();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        String text = editText.getText().toString();
        int priority;

        if (radioGroup.getCheckedRadioButtonId() == high.getId()) {
            priority = R.color.red;
        } else if (radioGroup.getCheckedRadioButtonId() == medium.getId()) {
            priority = R.color.orange;
        } else {
            priority = R.color.green;
        }

        Note note = new Note(text, priority);

        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDataBase.notesDao().add(note);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
            }
        }).start();
    }

    public void init() {
        saveButton = findViewById(R.id.saveButton);
        radioGroup = findViewById(R.id.radioGroup);
        medium = findViewById(R.id.mediumRButton);
        high = findViewById(R.id.highRButton);
        editText = findViewById(R.id.taskNote);
        noteDataBase = NoteDataBase.getInstance(getApplication());
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, NewTaskActivity.class);
        return intent;
    }
}