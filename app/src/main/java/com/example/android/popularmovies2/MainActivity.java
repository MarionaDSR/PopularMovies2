package com.example.android.popularmovies2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies2.adapters.MovieAdapter;
import com.example.android.popularmovies2.data.FavoriteDbHelper;
import com.example.android.popularmovies2.model.Movie;
import com.example.android.popularmovies2.utils.MovieJsonUtils;
import com.example.android.popularmovies2.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.example.android.popularmovies2.data.FavoriteContract.FavoriteEntry;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;

    private MovieAdapter mMovieAdapter;

    private String mOrderOption;

    private ProgressBar mLoadingIndicator;
    private TextView mTvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.gv_main_grid);
        mMovieAdapter = new MovieAdapter(this);
        gridView.setAdapter(mMovieAdapter);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mOrderOption = mPreferences.getString(getResources().getString(R.string.settings_order_key),
                getResources().getString(R.string.settings_order_default));

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mTvError = (TextView) findViewById(R.id.tv_error);
        try {
            getMoviesList(mOrderOption);
        } catch (Exception e) {
            showError(e);
        }
    }

    private void getMoviesList(String order) {
        if (order.equals(getResources().getString(R.string.settings_favorites_key))) {
            Cursor cursor = getContentResolver().query(FavoriteEntry.CONTENT_URI, null, null, null, null, null);
            mMovieAdapter.setMoviesData(FavoriteDbHelper.getMoviesFromCursor(cursor));
        } else {
            String sQuery;
            Resources resources = getResources();
            if (order.equals(getResources().getString(R.string.settings_popular_key))) { // POPULAR
                sQuery = resources.getString(R.string.themoviedb_query_popular);
            } else { //if (order.equals(getResources().getString(R.string.settings_topRated_key))) { // TOP_RATED
                sQuery = resources.getString(R.string.themoviedb_query_toprated);
            }
            String popularMoviesQuery = String.format(resources.getString(R.string.themoviedb_query_root),
                    sQuery, resources.getString(R.string.themoviedb_key));
            try {
                URL searchUrl = new URL(popularMoviesQuery);
                new QueryTask().execute(searchUrl);
            } catch (MalformedURLException e) {
                showError(e);
            }
        }
    }

    public class QueryTask extends AsyncTask<URL, Void, String> {
        private Throwable error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
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
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (s == null) {
                showError(error);
                return;
            }
            try {
                JSONObject jsonRootData = new JSONObject(s);
                List<Movie> movies = MovieJsonUtils.getMovies(jsonRootData);
                mMovieAdapter.setMoviesData(movies);
            } catch (JSONException e) {
                showError(e);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        mOrderOption = mPreferences.getString(getResources().getString(R.string.settings_order_key),
                getResources().getString(R.string.settings_order_default));
        getMoviesList(mOrderOption);
        super.onResume();
    }

    private void showError(Throwable error) {
        String errorMsg = (error != null) ? error.getMessage() : getResources().getString(R.string.error_unknown);
        mTvError.setText(String.format(getResources().getString(R.string.error_pattern), errorMsg));
        mTvError.setVisibility(View.VISIBLE);
        Log.e("MainActivity", errorMsg, error);
    }

}
