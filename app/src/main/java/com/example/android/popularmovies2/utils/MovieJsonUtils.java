package com.example.android.popularmovies2.utils;

import com.example.android.popularmovies2.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariona on 26/1/17.
 */

public class MovieJsonUtils {
    private static final String LBL_RESULTS = "results";

    private static final String LBL_ID = "id";
    private static final String LBL_POSTER_PATH = "poster_path";
    private static final String LBL_ORIGINAL_TITLE = "original_title";
    private static final String LBL_OVERVIEW = "overview";
    private static final String LBL_RELEASE_DATE = "release_date";
    private static final String LBL_VOTE_AVERAGE = "vote_average";

    public static List<Movie> getMovies(JSONObject jsonData) throws JSONException{
        JSONArray moviesJsonArray = jsonData.getJSONArray(LBL_RESULTS);
        int length = moviesJsonArray.length();
        List<Movie> movies = new ArrayList<Movie>();
        for (int i = 0; i < length; i ++) {
            JSONObject jsonMovie = moviesJsonArray.getJSONObject(i);
            Movie m = new Movie();
            m.setId(jsonMovie.getInt(LBL_ID));
            m.setPosterPath(jsonMovie.getString(LBL_POSTER_PATH));
            m.setOriginalTitle(jsonMovie.getString(LBL_ORIGINAL_TITLE));
            m.setOverview(jsonMovie.getString(LBL_OVERVIEW));
            String releaseDate = jsonMovie.getString(LBL_RELEASE_DATE);
            m.setReleaseDate(releaseDate);
            m.setVoteAverage(jsonMovie.getDouble(LBL_VOTE_AVERAGE));
            movies.add(m);
        }
        return movies;
    }
}
