package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.flixster.R;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;
    OnClickListener clickListener;

    public interface OnClickListener {
        void onClickListener(int position);
    }

    public MovieAdapter(Context context, List<Movie> movies, OnClickListener clickListener) {
        this.context = context;
        this.movies = movies;
        this.clickListener = clickListener;
    }

    // Inflate layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMovieBinding itemMovieBinding = ItemMovieBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemMovieBinding);
    }

    // Return the total count of item in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // Populating data into item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the View Holder
        holder.bind(movie);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.getRoot());
            this.tvTitle = itemMovieBinding.tvTitle;
            this.tvOverview = itemMovieBinding.tvOverview;
            this.ivPoster = itemMovieBinding.ivPoster;
        }

        // Bind each information of given movie to the template
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;
            int placeholder;
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 0; // crop margin, set to 0 for corners with no crop

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
                placeholder = R.drawable.flicks_backdrop_placeholder;
            } else {
                imageUrl = movie.getPosterPath();
                placeholder = R.drawable.flicks_movie_placeholder;
            }

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(placeholder)
                    .fitCenter()
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);

            // Notify the listener with clicked title
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClickListener(getAdapterPosition());
                }
            });

            // Notify the listener with clicked overview
            tvOverview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClickListener(getAdapterPosition());
                }
            });

            // Notify the listener with clicked overview
            ivPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClickListener(getAdapterPosition());
                }
            });
        }
    }
}
