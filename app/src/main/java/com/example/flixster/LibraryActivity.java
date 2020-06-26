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

    MovieAdapter movieAdapter;
    List<Movie> movies;
    RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Library");

        ActivityLibraryBinding binding = ActivityLibraryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movies = LibraryStorage.movies;
        rvMovies = binding.rvMoviesLibrary;

        // Remove clicked movies from library
        MovieAdapter.OnClickListener onClickListener= new MovieAdapter.OnClickListener() {
            @Override
            public void onClickListener(int position) {
                LibraryStorage.removeMovie(movies.get(position));
                movieAdapter.notifyDataSetChanged();
            }
        };

        // Create the adapter & set up recycler view
        movieAdapter = new MovieAdapter(this, movies, onClickListener);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
    }
}