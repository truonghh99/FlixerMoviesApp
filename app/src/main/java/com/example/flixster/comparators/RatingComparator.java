package com.example.flixster.comparators;

import com.example.flixster.models.Movie;

import java.util.Comparator;

public class RatingComparator implements Comparator<Movie> {

    @Override
    public int compare(Movie a, Movie b) {
        return a.getRating() > b.getRating() ? -1 : a.getRating() == b.getRating() ? 0 : 1;
    }
}
