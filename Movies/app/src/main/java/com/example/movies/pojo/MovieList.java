package com.example.movies.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {

    @SerializedName("docs")
    private List<Movie> movies;

    public MovieList(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "MovieList{" +
                "movies=" + movies +
                '}';
    }
}
