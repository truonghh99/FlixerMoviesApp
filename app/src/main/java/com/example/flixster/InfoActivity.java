package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityInfoBinding;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class InfoActivity extends AppCompatActivity {

    TextView title;
    RatingBar ratingBar;
    TextView popularity;
    TextView overview;
    Movie movie;
    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInfoBinding binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        title = binding.tvTitleInfo;
        ratingBar = binding.ratingBar;
        popularity = binding.tvPopularity;
        overview = binding.tvOverviewInfo;
        ivPoster = binding.ivPosterInfo;

        getSupportActionBar().setTitle("Movie details");

        // Get selected movie from Main Activity
        int position = getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION);
        movie = MainActivity.getMovie(position);

        title.setText(movie.getTitle());
        ratingBar.setRating((float) movie.getRating() / 10 * 5);
        popularity.setText("Popularity: " + Double.toString(movie.getPopularity()));
        overview.setText(movie.getOverview());

        String imageUrl;
        int placeholder;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageUrl = movie.getPosterPath();
            placeholder = R.drawable.flicks_movie_placeholder;
        } else {
            imageUrl = movie.getBackdropPath();
            placeholder = R.drawable.flicks_backdrop_placeholder;
        }

        Glide.with(this)
                .load(imageUrl)
                .placeholder(placeholder)
                .fitCenter()
                .into(ivPoster);
    }
}