package com.example.movies.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.movies.retrofit.ApiFactory;

public class DetailViewModel extends AndroidViewModel {
    public DetailViewModel(@NonNull Application application) {
        super(application);
    }


    void loadMovie(){

    }
}
