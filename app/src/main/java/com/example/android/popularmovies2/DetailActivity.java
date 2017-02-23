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

import com.example.android.popularmovies2.adapters.ReviewAdapter;
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

    private Movie mMovie;

    private TextView mTvTitle;
    private TextView mTvRating;
    private ImageView mIvFavorite;
    private TextView mTvDate;
    private TextView mTvOverview;
    private ImageView mIvPoster;
    private RecyclerView mRvTrailers;
    private RecyclerView mRvReviews;

    private ProgressBar mLoadingIndicator;
    private TextView mTvError;

    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;

    private DetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvRating = (TextView) findViewById(R.id.tv_rating);
        mIvFavorite = (ImageView) findViewById(R.id.iv_favorite);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvOverview = (TextView) findViewById(R.id.tv_overview);
        mIvPoster = (ImageView) findViewById(R.id.iv_poster);
        mRvTrailers = (RecyclerView) findViewById(R.id.rv_trailers);
        mRvReviews = (RecyclerView) findViewById(R.id.rv_reviews);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mTvError = (TextView) findViewById(R.id.tv_error);

        mMovie = getIntent().getParcelableExtra("movie");
        mTvTitle.setText(mMovie.getOriginalTitle());
        mTvRating.setText(Double.toString(mMovie.getVoteAverage()));
        mTvDate.setText(mMovie.getReleaseDate());
        mTvOverview.setText(mMovie.getOverview());

        String pictureUrl = getResources().getString(R.string.imagedb_url_root)
                + mMovie.getPosterPath();
        Picasso.with(this)
                .load(pictureUrl)
                .fit()
                .into(mIvPoster);

        mPresenter = new DetailPresenter(this, mMovie);
        try {
            mPresenter.getTrailers();
        } catch (MalformedURLException e) {
            showError(e);
        }

        try {
            mPresenter.getReviews();
        } catch (MalformedURLException e) {
            showError(e);
        }

        LinearLayoutManager trailerLLM = new LinearLayoutManager(this);
        mRvTrailers.setHasFixedSize(true);
        mRvTrailers.setLayoutManager(trailerLLM);
        mTrailerAdapter = new TrailerAdapter(this);
        mRvTrailers.setAdapter(mTrailerAdapter);

        LinearLayoutManager reviewLLM = new LinearLayoutManager(this);
        mRvReviews.setHasFixedSize(true);
        mRvReviews.setLayoutManager(reviewLLM);
        mReviewAdapter = new ReviewAdapter();
        mRvReviews.setAdapter(mReviewAdapter);
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
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mReviewAdapter.setReviews(reviews);
        mReviewAdapter.notifyDataSetChanged();
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

    public void setFavorite(View v) {
        mMovie.setFavorite(!mMovie.isFavorite());
        if (mMovie.isFavorite()) {
            mIvFavorite.setImageResource(R.drawable.ic_favorite_true);
        } else {
            mIvFavorite.setImageResource(R.drawable.ic_favorite_false);
        }
    }
}
