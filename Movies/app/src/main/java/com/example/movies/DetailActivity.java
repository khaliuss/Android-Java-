package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.movies.pojo.Movie;
import com.example.movies.recuclerView.MovieAdapter;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity {

    private static final String MOVIE = "movie";

    private ImageView posterImg;
    private ImageView favoriteBt;
    private TextView movieName;
    private TextView movieYear;
    private TextView movieNDesc;

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



    }

    private void init(){
        posterImg = findViewById(R.id.detailImg);
        favoriteBt = findViewById(R.id.detailIFavoriteImgButton);
        movieName = findViewById(R.id.detailIMovieName);
        movieYear = findViewById(R.id.detailIMovieYear);
        movieNDesc = findViewById(R.id.detailIMovieDescription);

    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra(MOVIE,movie);
        return intent;
    }
}