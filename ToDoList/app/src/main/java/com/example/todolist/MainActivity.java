package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addTaskFButton;
    private NoteDataBase noteDataBase;

    private Handler handler = new Handler(Looper.getMainLooper());

    private RecyclerView recyclerViewNotes;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClicked(Note note) {
            }
        });

        recyclerViewNotes.setAdapter(notesAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNotes().get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        noteDataBase.notesDao().remove(note.getId());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showNotes();
                            }
                        });
                    }
                }).start();
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);


        addTaskFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFAB();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void showNotes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Note> notes = noteDataBase.notesDao().getNotes();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notesAdapter.setNotes(notes);
                    }
                });
            }
        }).start();
    }

    private void onClickFAB() {
        Intent intent = NewTaskActivity.newIntent(this);
        startActivity(intent);
    }

    private void init() {
        addTaskFButton = findViewById(R.id.addTaskFButton);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        notesAdapter = new NotesAdapter();
        noteDataBase = NoteDataBase.getInstance(getApplication());
    }
}