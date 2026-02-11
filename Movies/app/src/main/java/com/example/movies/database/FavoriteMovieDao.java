package com.example.movies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movies.pojo.Movie;
import com.example.movies.pojo.Trailer;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavoriteMovieDao {


    @Query("SELECT * FROM favorite_movie")
    LiveData<List<Movie>> getFavoriteMovies();

    @Insert
    Completable addToFavorite(Movie movie);

    @Query("DELETE FROM favorite_movie WHERE id=:id")
    Completable removeFromFavorite(int id);

    @Query("SELECT * FROM favorite_movie WHERE id=:id")
    LiveData<Movie> getFavoriteMovie(int id);

}
