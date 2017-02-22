package com.example.android.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mariona on 20/2/17.
 */

/*
{
"id":14564,
"results":[
    {
        "id":"58766c159251416ed80005ab",
        "iso_639_1":"en",
        "iso_3166_1":"US",
        "key":"FkAndUJpyX0",
        "name":"Official Trailer 2",
        "site":"YouTube",
        "size":1080,
        "type":"Trailer"
     },{
        "id":"58766bfdc3a36806210005cc",
        "iso_639_1":"en",
        "iso_3166_1":"US",
        "key":"6MnwkusJyuE",
        "name":"Official Trailer International",
        "site":"YouTube",
        "size":1080,
        "type":"Trailer"
       }
   ]}

   https://www.youtube.com/watch?v=FkAndUJpyX0
 */
public class Trailer implements Parcelable {

    private String id;
    private String key;
    private String site;
    private String name;

    public Trailer() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(id);
        out.writeString(key);
        out.writeString(site);
        out.writeString(name);
    }

    public static final Creator<Trailer> CREATOR
            = new Creator<Trailer>() {
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    private Trailer(Parcel in) {
        id = in.readString();
        key = in.readString();
        site = in.readString();
        name = in.readString();
    }

    public String getUrl() {
        return "https://www.youtube.com/watch?v=" + getKey(); // TODO extract url, check is youtube
    }
}
