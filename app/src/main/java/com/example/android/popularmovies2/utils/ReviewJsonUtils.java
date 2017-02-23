package com.example.android.popularmovies2.utils;

import com.example.android.popularmovies2.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariona on 26/1/17.
 */

public class ReviewJsonUtils {
    private static final String LBL_RESULTS = "results";

    private static final String LBL_ID = "id";
    private static final String LBL_CONTENT = "content";
    private static final String LBL_AUTHOR = "author";

    public static List<Review> getReviews(JSONObject jsonData) throws JSONException{
        JSONArray reviewsJsonArray = jsonData.getJSONArray(LBL_RESULTS);
        int length = reviewsJsonArray.length();
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < length; i ++) {
            JSONObject jsonReview = reviewsJsonArray.getJSONObject(i);
            Review r = new Review();
            r.setId(jsonReview.getString(LBL_ID));
            r.setContent(jsonReview.getString(LBL_CONTENT));
            r.setAuthor(jsonReview.getString(LBL_AUTHOR));
            reviews.add(r);
        }
        return reviews;
    }
}
