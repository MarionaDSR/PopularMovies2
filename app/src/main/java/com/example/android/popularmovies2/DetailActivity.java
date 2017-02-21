package com.example.android.popularmovies2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView mTvTitle;
    private TextView mTvRating;
    private TextView mTvDate;
    private TextView mTvOverview;
    private ImageView mIvPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvRating = (TextView) findViewById(R.id.tv_rating);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvOverview = (TextView) findViewById(R.id.tv_overview);
        mIvPoster = (ImageView) findViewById(R.id.iv_poster);

        Movie movie = getIntent().getParcelableExtra("movie");
        mTvTitle.setText(movie.getOriginalTitle());
        mTvRating.setText(movie.getVoteAverage() + "");
        mTvDate.setText(movie.getReleaseDate());
        mTvOverview.setText(movie.getOverview());

        String pictureUrl = getResources().getString(R.string.imagedb_url_root)
                + movie.getPosterPath();
        Picasso.with(this)
                .load(pictureUrl)
                .fit()
                .into(mIvPoster);
    }
}
