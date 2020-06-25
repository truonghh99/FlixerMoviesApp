package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Video {
    String videoKey;

    public Video(JSONObject jsonObject) throws JSONException {
        videoKey = jsonObject.getString("key");
    }

    // Extract videos from given JSON Array
    public static List<Video> extractVideosFromJsonArray(JSONArray videoJsonArray) throws JSONException {
        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < videoJsonArray.length(); ++i) {
            videos.add(new Video(videoJsonArray.getJSONObject(i)));
        }
        return videos;
    }

    public String getVideoKey() {
        return videoKey;
    }
}
