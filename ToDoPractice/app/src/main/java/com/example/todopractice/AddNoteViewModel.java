package com.example.todopractice;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AddNoteViewModel extends AndroidViewModel {

    private NoteDataBase noteDataBase;
    private MutableLiveData<Boolean> isFinished = new MutableLiveData<>();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        noteDataBase = NoteDataBase.getInstance(application);
    }

    public LiveData<Boolean> getIsFinished() {
        return isFinished;
    }

    public void add(Note note){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDataBase.notesDao().add(note);
                isFinished.postValue(true);
            }
        }).start();
    }
}
