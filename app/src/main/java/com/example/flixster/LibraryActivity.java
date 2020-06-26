package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.comparators.DateComparator;
import com.example.flixster.comparators.RatingComparator;
import com.example.flixster.databinding.ActivityLibraryBinding;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;
import com.example.flixster.storage.LibraryStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    MovieAdapter movieAdapter;
    List<Movie> movies;
    RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Library");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple)));

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
                Toast.makeText(getApplicationContext(), "Movie removed from your library", Toast.LENGTH_SHORT).show();
            }
        };

        // Create the adapter & set up recycler view
        movieAdapter = new MovieAdapter(this, movies, onClickListener);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    // Implement details on toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem switchItem = menu.findItem(R.id.action_switch);

        SearchView searchView = (SearchView) searchItem.getActionView();
        Switch mySwitch = switchItem.getActionView().findViewById(R.id.swSort);

        // Pass input search to filter handler in movieAdapter
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Implement sort switch: use DateComparator when not checked and RatingComparator when checked
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    Collections.sort(movies, new RatingComparator());
                    movieAdapter.notifyDataSetChanged();
                    movieAdapter.updateFullList(movies);
                } else {
                    Collections.sort(movies, new DateComparator());
                    movieAdapter.notifyDataSetChanged();
                    movieAdapter.updateFullList(movies);
                }
            }
        });

        return true;
    }
}