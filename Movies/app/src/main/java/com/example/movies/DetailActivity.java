package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.pojo.Movie;
import com.example.movies.pojo.Trailer;
import com.example.movies.recuclerView.MovieAdapter;
import com.example.movies.recuclerView.TrailerAdapter;
import com.example.movies.viewModels.DetailViewModel;

import java.io.Serializable;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String MOVIE = "movie";
    private DetailViewModel detailViewModel;

    private ImageView posterImg;
    private ImageView favoriteBt;
    private TextView movieName;
    private TextView movieYear;
    private TextView movieNDesc;
    private RecyclerView trailerRecycler;
    private TrailerAdapter trailerAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        Movie movie = (Movie) getIntent().getSerializableExtra(MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(posterImg);

        movieName.setText(movie.getName());
        movieYear.setText(movie.getYear());
        movieNDesc.setText(movie.getDescription());

        if (movie.isFavorite()) {
            favoriteBt.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_on));
        } else {
            favoriteBt.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_off));
        }

        favoriteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movie.isFavorite()) {
                    favoriteBt.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_on));
                    detailViewModel.addMovieToFavorite(movie);
                } else {
                    detailViewModel.removeMovieFavorite(movie.getId());
                    favoriteBt.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_off));
                }
            }
        });

        trailerRecycler.setAdapter(trailerAdapter);

        detailViewModel.loadTrailers(movie.getId());

        detailViewModel.trailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                if (trailers != null) {
                    trailerAdapter.setTrailers(trailers);
                }
            }
        });

        detailViewModel.getIsTrailersLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isTrailerLoading) {
                if (isTrailerLoading) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        detailViewModel.getIsTrailerExist().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isTrailerExist) {
                if (!isTrailerExist) {
                    Toast.makeText(DetailActivity.this, "Trailer not found", Toast.LENGTH_LONG).show();
                }
            }
        });

        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });


    }

    private void init() {
        posterImg = findViewById(R.id.detailImg);
        favoriteBt = findViewById(R.id.detailIFavoriteImgButton);
        movieName = findViewById(R.id.detailIMovieName);
        movieYear = findViewById(R.id.detailIMovieYear);
        movieNDesc = findViewById(R.id.detailIMovieDescription);
        progressBar = findViewById(R.id.trailerProgressBar);
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        trailerRecycler = findViewById(R.id.trailersRecycler);
        trailerAdapter = new TrailerAdapter();
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(MOVIE, movie);
        return intent;
    }
}