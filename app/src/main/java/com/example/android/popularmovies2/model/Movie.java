package com.example.android.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariona on 26/1/17.
 */

public class Movie implements Parcelable {
    private int id;
    private String posterPath;
    private String originalTitle;
    private String overview;
    private String releaseDate;
    private double voteAverage;
    private List<Trailer> trailers = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    public Movie() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Trailer getTrailer(int pos) {
        return trailers.get(pos);
    }

    public void addTrailer(Trailer trailer) {
        trailers.add(trailer);
    }

    public Review getReview(int pos) {
        return reviews.get(pos);
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    // // // PARCELABLE // // //

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(posterPath);
        out.writeString(originalTitle);
        out.writeString(overview);
        out.writeString(releaseDate);
        out.writeDouble(voteAverage);
        out.writeTypedList(trailers);
        out.writeTypedList(reviews);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        id = in.readInt();
        posterPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
        in.readTypedList(trailers, Trailer.CREATOR);
        in.readTypedList(reviews, Review.CREATOR);
    }

}
