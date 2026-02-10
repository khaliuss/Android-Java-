package com.example.movies.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movies.pojo.Movie;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface FavoriteMovieDao {

    @Insert
    Completable addToFavorite(Movie movie);

    @Query("DELETE FROM favorite_movie WHERE id=:id")
    Completable removeFromFavorite(int id);

}
