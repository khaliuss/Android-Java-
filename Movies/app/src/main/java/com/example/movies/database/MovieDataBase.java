package com.example.movies.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movies.pojo.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDataBase extends RoomDatabase {

    private MovieDataBase movieDataBase;
    private String MOVIE_DB = "movie.db";

    public MovieDataBase getMovieDataBase(Application application) {
        if (movieDataBase == null) {
            movieDataBase = Room.databaseBuilder(
                    application,
                    MovieDataBase.class,
                    MOVIE_DB
            ).build();
        }
        return movieDataBase;
    }
}
