package com.example.movies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.pojo.Movie;
import com.example.movies.pojo.MovieList;
import com.example.movies.recuclerView.MovieAdapter;
import com.example.movies.viewModels.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ProgressBar progressBar;
    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        movieRecyclerView.setAdapter(movieAdapter);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });

        mainViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading){
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                mainViewModel.loadMovies();
            }
        });

        movieAdapter.setOnClickItem(new MovieAdapter.OnClickItem() {
            @Override
            public void onClick(Movie movie) {
                Intent intent = DetailActivity.newIntent(MainActivity.this,movie);
                startActivity(intent);
            }
        });

    }

    private void init(){
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        movieRecyclerView = findViewById(R.id.movieRecycler);
        progressBar = findViewById(R.id.progressBar);
        movieAdapter = new MovieAdapter();
    }
}