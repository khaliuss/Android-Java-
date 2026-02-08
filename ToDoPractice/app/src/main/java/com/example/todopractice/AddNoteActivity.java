package com.example.todopractice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class AddNoteActivity extends AppCompatActivity {

    private EditText noteEd;
    private RadioGroup radioGroup;
    private RadioButton highRadioButton;
    private RadioButton mediumRadioButton;
    private AddNoteViewModel addNoteViewModel;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        init();

        addNoteViewModel.getIsFinished().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isFinished) {
                if (isFinished){
                    finish();
                }
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteText = noteEd.getText().toString().trim();
                Note note = new Note(noteText,getPriority());
                addNoteViewModel.add(note);
            }
        });


    }

    private int getPriority(){
        if (radioGroup.getCheckedRadioButtonId() == highRadioButton.getId()){
            return android.R.color.holo_red_light;
        } else if (radioGroup.getCheckedRadioButtonId() == mediumRadioButton.getId()) {
            return android.R.color.holo_orange_light;
        }else {
            return android.R.color.holo_green_light;
        }
    }

    private void init(){
        noteEd = findViewById(R.id.taskNoteEd);
        radioGroup = findViewById(R.id.radioGroup);
        highRadioButton = findViewById(R.id.highRButton);
        mediumRadioButton = findViewById(R.id.mediumRButton);
        saveButton = findViewById(R.id.saveButton);
        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }

}