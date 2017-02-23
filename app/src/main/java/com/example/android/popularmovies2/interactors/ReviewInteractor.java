package com.example.android.popularmovies2.interactors;

import android.content.res.Resources;
import android.os.AsyncTask;

import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.model.Movie;
import com.example.android.popularmovies2.model.Review;
import com.example.android.popularmovies2.utils.NetworkUtils;
import com.example.android.popularmovies2.utils.ReviewJsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by mariona on 21/2/17.
 */

public class ReviewInteractor {

    private Callback mCallback;

    public ReviewInteractor(Callback callback) {
        mCallback = callback;
    }

    public void loadReviews(Movie movie) throws MalformedURLException {
        Resources resources = mCallback.getResources();
        String reviewsQuery =  String.format(resources.getString(R.string.themoviedb_query_root),
                String.format(resources.getString(R.string.themoviedb_query_reviews), movie.getId()),
                resources.getString(R.string.themoviedb_key));

        URL searchUrl = new URL(reviewsQuery);
        new ReviewInteractor.QueryTask().execute(searchUrl);
    }

    private class QueryTask extends AsyncTask<URL, Void, String> {
        private Throwable error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            error = null;
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            try {
                return NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                error = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == null) {
                mCallback.setError(error);
                return;
            }
            try {
                JSONObject jsonRootData = new JSONObject(s);
                List<Review> reviews = ReviewJsonUtils.getReviews(jsonRootData);
                mCallback.setReviews(reviews);
            } catch (JSONException e) {
                mCallback.setError(e);
            }
        }
    }

    public interface Callback {
        void setReviews(List<Review> reviews);
        void setError(Throwable error);
        Resources getResources();
    }
}
