package com.example.flixster.storage;

import com.example.flixster.models.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibraryStorage {

    public static List<Movie> movies = new ArrayList<>();
    public static HashMap<String, Movie> addedMovie = new HashMap<>();

    public static boolean addNewMovie(Movie movie) {
        String movieId = movie.getId();
        if (addedMovie.containsKey(movieId)) {
            return false;
        } else {
            addedMovie.put(movieId, movie);
            movies.add(movie);
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
        }
        return true;
    }
}
