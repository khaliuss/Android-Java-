package com.example.movies.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movies.pojo.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class FavoriteDataBase extends RoomDatabase {

    private static FavoriteDataBase favoriteDataBase;
    private static final String DATABASE_NAME = "favorites.db";

    public static FavoriteDataBase getDataBase(Application application) {
        favoriteDataBase = Room.databaseBuilder(
                application
                , FavoriteDataBase.class
                , DATABASE_NAME
        ).build();
        return favoriteDataBase;
    }


    public abstract FavoriteMovieDao favoriteMovieDao();


}
