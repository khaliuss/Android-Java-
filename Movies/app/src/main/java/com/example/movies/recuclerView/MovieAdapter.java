package com.example.movies.recuclerView;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.MainActivity;
import com.example.movies.R;
import com.example.movies.pojo.Movie;
import com.example.movies.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private List<Movie> movies = new ArrayList<>();

    private OnReachEndListener onReachEndListener;
    private OnClickItem onClickItem;

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_recycler_item,
                parent,
                false
        );
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClick(movie);
            }
        });

        if (position >= movies.size()-10 && onReachEndListener != null){
                onReachEndListener.onReachEnd();
        }
        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster().getUrl())
                .into(holder.imgPoster);

        double rating = movie.getRating().getKp();
        int background;
        if (rating>7){
            background = R.drawable.circl_green;
        }else if (rating>5){
            background = R.drawable.circl_orange;
        }else {
            background = R.drawable.circl_red;
        }
        Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(),background);
        holder.tvRating.setBackground(drawable);
        holder.tvRating.setText(String.format(Locale.US,"%.1f",movie.getRating().getKp()));
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface OnReachEndListener {
        void onReachEnd();
    }

    public interface OnClickItem {
        void onClick(Movie movie);
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPoster;
        private final TextView tvRating;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgViewPoster);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }

}
