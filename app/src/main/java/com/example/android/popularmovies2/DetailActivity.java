package com.example.android.popularmovies2;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.adapters.ReviewAdapter;
import com.example.android.popularmovies2.adapters.TrailerAdapter;
import com.example.android.popularmovies2.data.FavoriteDbHelper;
import com.example.android.popularmovies2.model.Movie;
import com.example.android.popularmovies2.model.Review;
import com.example.android.popularmovies2.model.Trailer;
import com.example.android.popularmovies2.presenters.DetailPresenter;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.List;

import static com.example.android.popularmovies2.data.FavoriteContract.FavoriteEntry;

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
    private TextView mTvTrailersTitle;
    private RecyclerView mRvTrailers;
    private TextView mTvReviewsTitle;
    private RecyclerView mRvReviews;

    private ProgressBar mPvTrailers;
    private ProgressBar mPvReviews;
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
        mTvTrailersTitle = (TextView) findViewById(R.id.tv_trailers_title);
        mRvTrailers = (RecyclerView) findViewById(R.id.rv_trailers);
        mTvReviewsTitle = (TextView) findViewById(R.id.tv_reviews_title);
        mRvReviews = (RecyclerView) findViewById(R.id.rv_reviews);

        mPvTrailers = (ProgressBar) findViewById(R.id.pb_trailers);
        mPvReviews = (ProgressBar) findViewById(R.id.pb_reviews);
        mTvError = (TextView) findViewById(R.id.tv_error);

        mMovie = getIntent().getParcelableExtra("movie");
        if (!mMovie.isFavorite()) {
            FavoriteDbHelper dbHelper = new FavoriteDbHelper(this);
            mMovie.setFavorite(dbHelper.checkFavorite(mMovie));
        }
        mTvTitle.setText(mMovie.getOriginalTitle());
        mTvRating.setText(Double.toString(mMovie.getVoteAverage()));
        mTvDate.setText(mMovie.getReleaseDate());
        mTvOverview.setText(mMovie.getOverview());
        mIvFavorite.setImageResource(mMovie.isFavorite()?R.drawable.ic_favorite_true:R.drawable.ic_favorite_false);

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
        mPvTrailers.setVisibility(View.INVISIBLE);
        mTvTrailersTitle.setText(getString(R.string.detail_trailers_title) + " (" + trailers.size() + ")");
        mTrailerAdapter.setTrailers(trailers);
        mTrailerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setReviews(List<Review> reviews) {
        mPvReviews.setVisibility(View.INVISIBLE);
        mTvReviewsTitle.setText(getString(R.string.detail_reviews_title) + " (" + reviews.size() + ")");
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
            ContentValues cv = new ContentValues();
            cv.put(FavoriteEntry._ID, mMovie.getId());
            cv.put(FavoriteEntry.COLUMN_TITLE, mMovie.getOriginalTitle());
            cv.put(FavoriteEntry.COLUMN_POSTER, mMovie.getPosterPath());
            cv.put(FavoriteEntry.COLUMN_SYNOPSIS, mMovie.getOverview());
            cv.put(FavoriteEntry.COLUMN_USER_RATING, mMovie.getVoteAverage());
            cv.put(FavoriteEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate());

            //mDb.insert(FavoriteEntry.TABLE_NAME, null, cv);
            Uri uri = getContentResolver().insert(FavoriteEntry.CONTENT_URI, cv);
            if (uri != null) {
                Toast.makeText(this, "Saved favorite for " + mMovie.getOriginalTitle(), Toast.LENGTH_LONG).show();
            }
        } else {
            mIvFavorite.setImageResource(R.drawable.ic_favorite_false);
            //mDb.delete(FavoriteEntry.TABLE_NAME, String.format(FavoriteEntry.SQL_BY_ID, mMovie.getId()), null);
            Uri deleteUri = FavoriteEntry.CONTENT_URI.buildUpon().appendPath(mMovie.getId() + "").build();
            getContentResolver().delete(deleteUri, null, null);
            Toast.makeText(this, "Unsaved favorite for " + mMovie.getOriginalTitle(), Toast.LENGTH_LONG).show();
        }
    }

    public void showFullContent(View v) {
        TextView tv = (TextView) v;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(tv.getText());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
