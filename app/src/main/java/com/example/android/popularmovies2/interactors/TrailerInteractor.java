package com.example.android.popularmovies2.interactors;

import android.content.res.Resources;
import android.os.AsyncTask;

import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.model.Movie;
import com.example.android.popularmovies2.model.Trailer;
import com.example.android.popularmovies2.utils.NetworkUtils;
import com.example.android.popularmovies2.utils.TrailerJsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by mariona on 21/2/17.
 */

public class TrailerInteractor {

    private Callback mCallback;

    public TrailerInteractor(Callback callback) {
        mCallback = callback;
    }

    public void loadTrailers(Movie movie) throws MalformedURLException {
        Resources resources = mCallback.getResources();
        String trailersQuery =  String.format(resources.getString(R.string.themoviedb_query_root),
                String.format(resources.getString(R.string.themoviedb_query_trailers), movie.getId()),
                resources.getString(R.string.themoviedb_key));

        URL searchUrl = new URL(trailersQuery);
        new TrailerInteractor.QueryTask().execute(searchUrl);
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
                List<Trailer> trailers = TrailerJsonUtils.getTrailers(jsonRootData);
                mCallback.setTrailers(trailers);
            } catch (JSONException e) {
                mCallback.setError(e);
            }
        }
    }

    public interface Callback {
        void setTrailers(List<Trailer> trailers);
        void setError(Throwable error);
        Resources getResources();
    }
}
