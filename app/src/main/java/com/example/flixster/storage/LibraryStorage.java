package com.example.flixster.storage;

import com.example.flixster.models.Movie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibraryStorage {

    public static List<Movie> movies = new ArrayList<>();
    public static HashMap<String, Movie> addedMovie = new HashMap<>();
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Library");

    public static boolean addNewMovie(Movie movie) {
        String movieId = movie.getId();
        if (addedMovie.containsKey(movieId)) {
            return false;
        } else {
            addedMovie.put(movieId, movie);
            movies.add(movie);
            mDatabase.push().setValue(movie);
        }
        return true;
    }

    public static boolean removeMovie(Movie movie) {
        String movieId = movie.getId();
        if (!addedMovie.containsKey(movieId)) {
            return false;
        } else {
            addedMovie.remove(movieId);
            movies.remove(movie);
            mDatabase.push().setValue(movie);
        }
        return true;
    }
}
