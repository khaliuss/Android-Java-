package com.example.movies.retrofit;

import com.example.movies.pojo.MovieList;
import com.example.movies.pojo.ReviewResponse;
import com.example.movies.pojo.TrailerResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=7NBRPP1-PST4ZKY-HA7X8DD-PYD9S0K&limit=40&notNullFields=poster.url&sortField=rating.kp&sortType=-1")
    Single<MovieList> loadMovies(@Query("page") int page);

    @GET("movie/{id}?token=7NBRPP1-PST4ZKY-HA7X8DD-PYD9S0K")
    Single<TrailerResponse> loadTrailers(@Path("id") int movieId);

    @GET("review?token=7NBRPP1-PST4ZKY-HA7X8DD-PYD9S0K")
    Single<ReviewResponse> loadReviews(@Query("movieId") int movieId);

//    Single<ReviewResponse> loadReviews(@Query("movieId") int movieId);


}
