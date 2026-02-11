package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.pojo.Movie;
import com.example.movies.recuclerView.MovieAdapter;
import com.example.movies.viewModels.FavoriteViewModel;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView favoriteRecycler;
    private MovieAdapter movieAdapter;
    private FavoriteViewModel favoriteViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);
        favoriteRecycler = findViewById(R.id.favoriteRecycler);
        movieAdapter = new MovieAdapter();

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        favoriteRecycler.setAdapter(movieAdapter);
        favoriteRecycler.setLayoutManager(new GridLayoutManager(this,2));

        movieAdapter.setOnClickItem(new MovieAdapter.OnClickItem() {
            @Override
            public void onClick(Movie movie) {
                Intent intent = DetailActivity.newIntent(FavoriteActivity.this,movie);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        favoriteViewModel.getFavoriteList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d("FavoriteActivity",movies.toString());
                movieAdapter.setMovies(movies);
            }
        });
    }

    public static Intent newIntent(Context context) {
        Intent intent =  new Intent(context,FavoriteActivity.class);
        return intent;
    }

}
