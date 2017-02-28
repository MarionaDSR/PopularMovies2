package com.example.android.popularmovies2.data;

import android.provider.BaseColumns;

/**
 * Created by mariona on 27/2/17.
 */

public class FavoriteContract {
    public static final class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_SYNOPSYS = "synopsys";
        public static final String COLUMN_USER_RATING = "userRating";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER NOT NULL," +
                COLUMN_TITLE + " TEXT NOT NULL," +
                COLUMN_POSTER + " TEXT NOT NULL," +
                COLUMN_SYNOPSYS + " TEXT NOT NULL," +
                COLUMN_USER_RATING + " NUMBER NOT NULL," +
                COLUMN_RELEASE_DATE + " TEXT NOT NULL" +
                "); ";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String SQL_BY_ID = _ID + " = %s";
    }
}
