package com.example.movies.recuclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.pojo.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {

    private List<Trailer> trailers = new ArrayList<>();

    private OnTrailerClickListener onTrailerClickListener;

    @NonNull
    @Override
    public TrailerAdapter.TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trailer_item,
                parent,
                false
        );
        return new TrailerHolder(view);
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.TrailerHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.trailerNameTv.setText(trailer.getTrailerName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTrailerClickListener != null){
                    onTrailerClickListener.onClick(trailer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public static class TrailerHolder extends RecyclerView.ViewHolder {

        TextView trailerNameTv;

        public TrailerHolder(@NonNull View itemView) {
            super(itemView);
            trailerNameTv = itemView.findViewById(R.id.trailerName);
        }
    }

    public interface OnTrailerClickListener {
        void onClick(Trailer trailer);
    }

}
