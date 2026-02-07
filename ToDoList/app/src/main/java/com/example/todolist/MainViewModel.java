package com.example.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private NoteDataBase noteDataBase;


    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDataBase = NoteDataBase.getInstance(application);
    }

    public void remove(Note note){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDataBase.notesDao().remove(note.getId());
            }
        }).start();
    }

    public LiveData<List<Note>> getNotes(){
        return noteDataBase.notesDao().getNotes();
    }
}
