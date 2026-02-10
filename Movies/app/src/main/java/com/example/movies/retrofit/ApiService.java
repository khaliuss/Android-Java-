package com.example.movies.retrofit;

import com.example.movies.pojo.MovieList;
import com.example.movies.pojo.TrailerResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=5RBED7A-V444WWE-QKBP08F-T8BA14K&limit=40&notNullFields=poster.url&sortField=rating.kp&sortType=-1")
    Single<MovieList> loadMovies(@Query("page") int page);

    @GET("movie/{id}?token=5RBED7A-V444WWE-QKBP08F-T8BA14K")
    Single<TrailerResponse> loadTrailers(@Path("id") int movieId);


}
