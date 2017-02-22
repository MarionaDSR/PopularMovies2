package com.example.android.popularmovies2.utils;

import com.example.android.popularmovies2.model.Movie;
import com.example.android.popularmovies2.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariona on 26/1/17.
 */

public class TrailerJsonUtils {
    private static final String LBL_RESULTS = "results";

    private static final String LBL_ID = "id";
    private static final String LBL_KEY = "key";
    private static final String LBL_SITE = "site";
    private static final String LBL_NAME = "name";

    public static List<Trailer> getTrailers(JSONObject jsonData) throws JSONException{
        JSONArray trailersJsonArray = jsonData.getJSONArray(LBL_RESULTS);
        int length = trailersJsonArray.length();
        List<Trailer> trailers = new ArrayList<>();
        for (int i = 0; i < length; i ++) {
            JSONObject jsonTrailer = trailersJsonArray.getJSONObject(i);
            Trailer t = new Trailer();
            t.setId(jsonTrailer.getString(LBL_ID));
            t.setKey(jsonTrailer.getString(LBL_KEY));
            t.setSite(jsonTrailer.getString(LBL_SITE));
            t.setName(jsonTrailer.getString(LBL_NAME));
            trailers.add(t);
        }
        return trailers;
    }
}
