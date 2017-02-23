package com.example.android.popularmovies2.presenters;

import android.content.res.Resources;

import com.example.android.popularmovies2.interactors.ReviewInteractor;
import com.example.android.popularmovies2.interactors.TrailerInteractor;
import com.example.android.popularmovies2.model.Movie;
import com.example.android.popularmovies2.model.Review;
import com.example.android.popularmovies2.model.Trailer;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by mariona on 21/2/17.
 */

public class DetailPresenter implements TrailerInteractor.Callback, ReviewInteractor.Callback {

    private DetailView mView;
    private Movie mMovie;
    private TrailerInteractor mTrailerInteractor;
    private ReviewInteractor mReviewInteractor;

    public DetailPresenter(DetailView view, Movie movie) {
        mView = view;
        mMovie = movie;
        mTrailerInteractor = new TrailerInteractor(this);
        mReviewInteractor = new ReviewInteractor(this);
    }

    public void getTrailers() throws MalformedURLException {
        mTrailerInteractor.loadTrailers(mMovie);
    }

    @Override
    public void setTrailers(List<Trailer> trailers) {
        mView.setTrailers(trailers);
    }

    public void getReviews() throws MalformedURLException {
        mReviewInteractor.loadReviews(mMovie);
    }

    @Override
    public void setReviews(List<Review> reviews) {
        mView.setReviews(reviews);
    }

    @Override
    public void setError(Throwable error) {
        mView.setError(error);
    }

    @Override
    public Resources getResources() {
        return mView.getResources();
    }

    public interface DetailView {
        void setTrailers(List<Trailer> trailers);
        void setReviews(List<Review> reviews);
        void setError(Throwable error);
        Resources getResources();
    }
}
