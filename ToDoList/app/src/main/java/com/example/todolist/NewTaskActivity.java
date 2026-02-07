package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private Database database;

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

        int id=database.getNotes().size();
        String text = editText.getText().toString();
        int priority;

        if (radioGroup.getCheckedRadioButtonId() == high.getId()){
            priority = R.color.red;
        }else if (radioGroup.getCheckedRadioButtonId() == medium.getId()){
            priority = R.color.orange;
        }else {
            priority = R.color.green;
        }

        Note note = new Note(id+1,text,priority);
        database.add(note);
        Intent intent = MainActivity.newIntent(this,note);
        startActivity(intent);
    }

    public void init(){
        saveButton = findViewById(R.id.saveButton);
        radioGroup = findViewById(R.id.radioGroup);
        medium = findViewById(R.id.mediumRButton);
        high = findViewById(R.id.highRButton);
        editText = findViewById(R.id.taskNote);
        database = Database.getInstance();
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, NewTaskActivity.class);
        return intent;
    }
}