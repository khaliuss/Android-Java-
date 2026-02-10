package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import com.bumptech.glide.Glide;
import com.example.movies.pojo.Movie;
import com.example.movies.pojo.Trailer;
import com.example.movies.recuclerView.MovieAdapter;
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

        if (movie.isFavorite()){
            favoriteBt.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_on));
        }else{
            favoriteBt.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_off));
        }

        favoriteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movie.isFavorite()){
                    favoriteBt.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_on));
                    detailViewModel.addMovieToFavorite(movie);
                }else {
                    detailViewModel.removeMovieFavorite(movie.getId());
                    favoriteBt.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_off));
                }
            }
        });

        detailViewModel.loadTrailers(movie.getId());
        detailViewModel.trailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                Log.d("DetailAct",trailers.toString());
            }
        });



    }

    private void init(){
        posterImg = findViewById(R.id.detailImg);
        favoriteBt = findViewById(R.id.detailIFavoriteImgButton);
        movieName = findViewById(R.id.detailIMovieName);
        movieYear = findViewById(R.id.detailIMovieYear);
        movieNDesc = findViewById(R.id.detailIMovieDescription);
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra(MOVIE,movie);
        return intent;
    }
}