package com.example.movies.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.database.FavoriteDataBase;
import com.example.movies.database.FavoriteMovieDao;
import com.example.movies.pojo.Movie;
import com.example.movies.retrofit.ApiFactory;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private FavoriteMovieDao favoriteDAO;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteDAO = FavoriteDataBase.getDataBase(application).favoriteMovieDao();
    }

    public LiveData<List<Movie>> getFavoriteList(){
        return favoriteDAO.getFavoriteMovies();
    }


}
