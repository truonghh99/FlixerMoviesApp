package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.databinding.ActivityLibraryBinding;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;
import com.example.flixster.storage.LibraryStorage;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    public static final String KEY_ITEM_POSITION = "item_position";

    MovieAdapter movieAdapter;
    List<Movie> movies = LibraryStorage.movies;
    RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLibraryBinding binding = ActivityLibraryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rvMovies = binding.rvMoviesLibrary;

        // Remove clicked movies from library
        MovieAdapter.OnClickListener onClickListener= new MovieAdapter.OnClickListener() {
            @Override
            public void onClickListener(int position) {
                LibraryStorage.removeMovie(movies.get(position));

            }
        };

        // Create the adapter & set up recycler view
        movieAdapter = new MovieAdapter(this, movies, onClickListener);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addNewMovie(Movie movie) {
        movies.add(movie);
    }
}