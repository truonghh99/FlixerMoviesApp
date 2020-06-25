package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    public final String VIDEO_REQUEST_URL_1 = "https://api.themoviedb.org/3/movie/";
    public final String VIDEO_REQUEST_URL_2 = "/videos?api_key=cd0afcef53a10158136ff1781c5e5e0d&language=en-US";
    public String videoKey;
    private final String API_KEY = "AIzaSyAyFJBF17-aFYTBo5FjmiRH6LRPcBWM7wc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_movie_trailer);

        String movieId = getIntent().getExtras().getString(InfoActivity.KEY_ID);
        getVideoId(VIDEO_REQUEST_URL_1 + movieId + VIDEO_REQUEST_URL_2);

        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        // initialize with API key stored in secrets.xml
        playerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                Log.e("Heyyy", videoKey);
                youTubePlayer.cueVideo(videoKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }

    private void getVideoId(String URL) {
        AsyncHttpClient client = new AsyncHttpClient();
        // Request movies' information from now playing url
        client.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject video = (JSONObject) results.get(0);
                    videoKey = video.getString("key");
                } catch (JSONException e) {
                    Log.e("TrailerActivity", "Failed to extract video key");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("TrailerActivity", "onFailure");
            }
        });
    }
}