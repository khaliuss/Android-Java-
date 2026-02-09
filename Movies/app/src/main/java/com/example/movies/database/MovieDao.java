package com.example.movies.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.movies.pojo.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorite_movie")
    List<Movie> getMovies();

}
