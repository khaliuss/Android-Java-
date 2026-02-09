package com.example.movies.retrofit;

import com.example.movies.pojo.Movie;
import com.example.movies.pojo.MovieList;
import com.example.movies.pojo.Poster;
import com.example.movies.pojo.Rating;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=5RBED7A-V444WWE-QKBP08F-T8BA14K&limit=40&notNullFields=poster.url&sortField=rating.kp&sortType=-1")
    Single<MovieList> loadMovies(@Query("page") int page);

}
