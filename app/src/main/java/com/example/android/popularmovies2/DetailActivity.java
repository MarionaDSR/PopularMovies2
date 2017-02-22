package com.example.android.popularmovies2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies2.adapters.TrailerAdapter;
import com.example.android.popularmovies2.model.Movie;
import com.example.android.popularmovies2.model.Review;
import com.example.android.popularmovies2.model.Trailer;
import com.example.android.popularmovies2.presenters.DetailPresenter;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements
        TrailerAdapter.TrailerAdapterOnClickHandler,
        DetailPresenter.DetailView {

    private static final String TAG = "DetailActivity";

    private TextView mTvTitle;
    private TextView mTvRating;
    private TextView mTvDate;
    private TextView mTvOverview;
    private ImageView mIvPoster;
    private RecyclerView mRvTrailers;
    private RecyclerView mRvReviews;

    private ProgressBar mLoadingIndicator;
    private TextView mTvError;

    private TrailerAdapter mTrailerAdapter;
//    private ReviewAdapter mReviewAdapter;

    private DetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvRating = (TextView) findViewById(R.id.tv_rating);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvOverview = (TextView) findViewById(R.id.tv_overview);
        mIvPoster = (ImageView) findViewById(R.id.iv_poster);
        mRvTrailers = (RecyclerView) findViewById(R.id.rv_trailers);
        mRvReviews = (RecyclerView) findViewById(R.id.rv_reviews);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mTvError = (TextView) findViewById(R.id.tv_error);

        Movie movie = getIntent().getParcelableExtra("movie");
        mTvTitle.setText(movie.getOriginalTitle());
        mTvRating.setText(Double.toString(movie.getVoteAverage()));
        mTvDate.setText(movie.getReleaseDate());
        mTvOverview.setText(movie.getOverview());

        String pictureUrl = getResources().getString(R.string.imagedb_url_root)
                + movie.getPosterPath();
        Picasso.with(this)
                .load(pictureUrl)
                .fit()
                .into(mIvPoster);

        mPresenter = new DetailPresenter(this, movie);
        try {
            mPresenter.getTrailers();
        } catch (MalformedURLException e) {
            showError(e);
        }

        LinearLayoutManager trailerLLM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvTrailers.setHasFixedSize(true);
        mRvTrailers.setLayoutManager(trailerLLM);
        mTrailerAdapter = new TrailerAdapter(this);
        mRvTrailers.setAdapter(mTrailerAdapter);
//        mRvReviews.setAdapter(mReviewAdapter);
    }

    @Override
    public void onTrailerClick(Trailer trailer) {
        String url = trailer.getUrl();
        Uri uri = Uri.parse(url);
        Intent trailerIntent = new Intent(Intent.ACTION_VIEW, uri);
        if (trailerIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(trailerIntent);
        }
    }

    @Override
    public void setTrailers(List<Trailer> trailers) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mTrailerAdapter.setTrailers(trailers);

        mTrailerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setReviews(List<Review> reviews) {

    }

    @Override
    public void setError(Throwable error) {
        showError(error);
    }

    private void showError(Throwable error) {
        String errorMsg = (error != null) ? error.getMessage() : getResources().getString(R.string.error_unknown);
        mTvError.setText(String.format(getResources().getString(R.string.error_pattern), errorMsg));
        mTvError.setVisibility(View.VISIBLE);
        Log.e(TAG, errorMsg, error);
    }

}
