package com.example.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class NewTaskViewModel extends AndroidViewModel {

    private NoteDataBase noteDataBase;
    private MutableLiveData<Boolean> isFinish  = new MutableLiveData<>();

    public NewTaskViewModel(@NonNull Application application) {
        super(application);
        noteDataBase = NoteDataBase.getInstance(application);
    }

    public LiveData<Boolean> getIsFinish() {
        return isFinish;
    }

    void add(Note note){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDataBase.notesDao().add(note);
                isFinish.postValue(true);
            }
        }).start();
    }


}
