package com.example.flixster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie {

    String backdropPath;
    String posterPath;
    String title;
    String overview;
    String movieId;
    double popularity;
    double rating;
    Date date;

    public Movie() {
    }

    public Movie(JSONObject jsonObject) throws JSONException, ParseException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        popularity = jsonObject.getDouble("popularity");
        rating = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getString("id");
        String stringDate = jsonObject.getString("release_date");
        Log.e("Checking", stringDate);
        date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
    }

    // Extract movies from given JsonArray
    public static List<Movie> extractMoviesFromJsonArray(JSONArray movieJsonArray) throws JSONException, ParseException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); ++i) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public double getPopularity() {
        return popularity;
    }

    public double getRating() {
        return rating;
    }

    public String getId() {
        return movieId;
    }

    public Date getDate() {
        return date;
    }
}