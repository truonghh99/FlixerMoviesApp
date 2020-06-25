package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.example.flixster.models.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    private final String VIDEO_REQUEST_URL_1 = "https://api.themoviedb.org/3/movie/";
    private final String VIDEO_REQUEST_URL_2 = "/videos?api_key=cd0afcef53a10158136ff1781c5e5e0d&language=en-US";
    private final String API_KEY = "AIzaSyAyFJBF17-aFYTBo5FjmiRH6LRPcBWM7wc";
    private final String TAG = "MovieTrailerActivity";
    private List<Video> videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_movie_trailer);

        String videoUrl = getUrl();
        getVideos(videoUrl);
        Log.e(TAG, "done");

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);
        // initialize with API key stored in secrets.xml
        playerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // Loop through all the given videos until found a valid one
                for (int i = 0; i < videos.size(); ++i) {
                    try {
                        String videoKey = videos.get(i).getVideoKey();
                        Log.e(TAG, videoKey);
                        youTubePlayer.setFullscreen(true);
                        youTubePlayer.loadVideo(videoKey);
                        break;
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to load video " + i + "/nError: " + e);
                    }
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }

    private String getUrl() {
        String movieId = getIntent().getExtras().getString(InfoActivity.KEY_ID);
        String videoUrl = VIDEO_REQUEST_URL_1 + movieId + VIDEO_REQUEST_URL_2;
        Log.e(TAG, videoUrl);
        return videoUrl;
    }

    private synchronized void getVideos(String videoUrl) {
        AsyncHttpClient client = new AsyncHttpClient();
        videos = new ArrayList<Video>();

        client.get(videoUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    videos = Video.extractVideosFromJsonArray(results);
                    Log.e(TAG, "Size " + String.valueOf(videos.size()));
                } catch (JSONException e) {
                    Log.e(TAG, "Failed to extract video key");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("TAG", "onFailure");
            }
        });
    }
}