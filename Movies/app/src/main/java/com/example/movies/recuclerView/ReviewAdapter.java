package com.example.movies.recuclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.pojo.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.review_item,
                parent,
                false
        );
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Review review = reviews.get(position);

        holder.reviewAuthTv.setText(review.getAuthor());

        String rvType = review.getType();
        int backgroundColor;
        if (rvType.equals("Позитивный")){
            backgroundColor = ContextCompat.getColor(holder.itemView.getContext(),android.R.color.holo_green_light);
        }else if(rvType.equals("Негативный")){
            backgroundColor = ContextCompat.getColor(holder.itemView.getContext(),android.R.color.holo_red_light);
        } else{
            backgroundColor = ContextCompat.getColor(holder.itemView.getContext(),android.R.color.holo_orange_light);
        }

        holder.reviewCardView.setCardBackgroundColor(backgroundColor);

        holder.reviewTextTv.setText(review.getReview());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder {

        TextView reviewAuthTv;
        TextView reviewTextTv;
        CardView reviewCardView;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            reviewAuthTv = itemView.findViewById(R.id.reviewAuth);
            reviewTextTv = itemView.findViewById(R.id.reviewText);
            reviewCardView = itemView.findViewById(R.id.reviewCardView);
        }
    }
}
