package com.example.android.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mariona on 20/2/17.
 */

/*
{
    "id":37257,
    "page":1,
    "results":[{
        "id":"50b3b6ab760ee30eda0000f2",
        "author":"Andres Gomez",
        "content":"Great thriller with superb classical interpretations. The plot moves sometimes too lazily.",
        "url":"https://www.themoviedb.org/review/50b3b6ab760ee30eda0000f2"}],
    "total_pages":1,
    "total_results":1}
 */

public class Review implements Parcelable {
    private String id;
    private String author;
    private String content;

    public Review() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(id);
        out.writeString(author);
        out.writeString(content);
    }

    public static final Creator<Review> CREATOR
            = new Creator<Review>() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    private Review(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
    }
}
