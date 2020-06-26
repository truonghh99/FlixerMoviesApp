package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.flixster.comparators.DateComparator;
import com.example.flixster.comparators.RatingComparator;
import com.example.flixster.databinding.ActivityInfoBinding;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;
import com.example.flixster.storage.LibraryStorage;

import java.util.Collections;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class InfoActivity extends AppCompatActivity {

    public static final String KEY_ID = "MOVIE_ID";
    TextView title;
    RatingBar ratingBar;
    TextView popularity;
    TextView overview;
    Movie movie;
    Drawable playIcon;
    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInfoBinding binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Bind view from layout
        title = binding.tvTitleInfo;
        ratingBar = binding.ratingBar;
        popularity = binding.tvPopularity;
        overview = binding.tvOverviewInfo;
        ivPoster = binding.ivPosterInfo;
        playIcon = getResources().getDrawable(R.drawable.ic_play);

        // Get selected movie from Main Activity
        int position = getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION);
        movie = MainActivity.getMovie(position);

        // Render information of selected movie
        title.setText(movie.getTitle());
        ratingBar.setRating((float) movie.getRating() / 10 * 5);
        popularity.setText("Popularity: " + Double.toString(movie.getPopularity()));
        overview.setText(movie.getOverview());

        // Select suitable image
        String imageUrl;
        int placeholder;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageUrl = movie.getPosterPath();
            placeholder = R.drawable.flicks_movie_placeholder;
        } else {
            imageUrl = movie.getBackdropPath();
            placeholder = R.drawable.flicks_backdrop_placeholder;
        }

        // Render image
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .placeholder(placeholder)
                .fitCenter()
                .into(ivPoster);

        // Start trailer activity when poster is clicked
        ivPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, MovieTrailerActivity.class);
                intent.putExtra(KEY_ID, movie.getId());
                startActivity(intent);
            }
        });

    }

    // Implement details on toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_menu, menu);

        MenuItem addItem = menu.findItem(R.id.btAdd);
        MenuItem libraryItem = menu.findItem(R.id.btLibrary);

        ImageView btAdd = (ImageView) addItem.getActionView();
        ImageView btLibrary = (ImageView) libraryItem.getActionView();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LibraryStorage.addNewMovie(movie)) {
                    Toast.makeText(getApplicationContext(), "Movie added to your library!", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(getApplicationContext(), "Movie is already in your library", Toast.LENGTH_SHORT);
                }
            }
        });

        btLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });

        return true;
    }
}