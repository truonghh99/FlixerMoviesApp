package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.comparators.DateComparator;
import com.example.flixster.comparators.RatingComparator;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Headers;


public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=cd0afcef53a10158136ff1781c5e5e0d";
    public static final String TAG = "MainActivity";
    public static final String KEY_ITEM_POSITION = "item_position";

    public static List<Movie> movies = new ArrayList<>();
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView rvMovies = binding.rvMovies;
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple)));

        // Start information activity customized for clicked item
        MovieAdapter.OnClickListener onClickListener= new MovieAdapter.OnClickListener() {
            @Override
            public void onClickListener(int position) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra(KEY_ITEM_POSITION, position);
                startActivity(intent);
            }
        };

        // Create the adapter & set up recycler view
        movieAdapter = new MovieAdapter(this, movies, onClickListener);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // Request movies' information from now playing url
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    if (movies.size() == 0) movies.addAll(Movie.extractMoviesFromJsonArray(results));
                    Collections.sort(movies, new RatingComparator());
                    movieAdapter.notifyDataSetChanged();
                    movieAdapter.updateFullList(movies);
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException | ParseException e) {
                    Log.e(TAG, "Failed to extract JSONArray");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    public static Movie getMovie(int position) {
        return movies.get(position);
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
