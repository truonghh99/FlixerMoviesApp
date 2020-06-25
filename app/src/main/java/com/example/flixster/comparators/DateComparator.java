package com.example.flixster.comparators;

import com.example.flixster.models.Movie;

import java.util.Comparator;

public class DateComparator implements Comparator<Movie> {

    @Override
    public int compare(Movie a, Movie b) {
        return a.getDate().compareTo(b.getDate());
    }
}
