package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.database.FavoriteDataBase;
import com.example.movies.pojo.Movie;
import com.example.movies.pojo.Review;
import com.example.movies.pojo.Trailer;
import com.example.movies.recuclerView.ReviewAdapter;
import com.example.movies.recuclerView.TrailerAdapter;
import com.example.movies.viewModels.DetailViewModel;

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

    private RecyclerView reviewRecycler;
    private ReviewAdapter reviewAdapter;
    private ProgressBar progressBar;
    private boolean isFavorite;

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

        Drawable starOn = ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_on);
        Drawable starOff = ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_off);

        detailViewModel.getFavoriteMovie(movie).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie fMovie) {
                if (fMovie != null) {
                    favoriteBt.setImageDrawable(starOn);
                    isFavorite = true;
                } else {
                    favoriteBt.setImageDrawable(starOff);
                    isFavorite = false;
                }
            }
        });



        favoriteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    detailViewModel.removeMovieFavorite(movie);
                    favoriteBt.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_off));
                } else {
                    favoriteBt.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_on));
                    detailViewModel.addMovieToFavorite(movie);
                }
            }
        });

        trailersFunctionality(movie);

        reviewFunctionality(movie);




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
        reviewRecycler = findViewById(R.id.reviewRecycler);
        reviewAdapter = new ReviewAdapter();
    }


    private void trailersFunctionality(Movie movie){
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


    private void reviewFunctionality(Movie movie) {

        reviewRecycler.setAdapter(reviewAdapter);

        detailViewModel.loadReview(movie.getId());

        detailViewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviews(reviews);
            }
        });

    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(MOVIE, movie);
        return intent;
    }
}