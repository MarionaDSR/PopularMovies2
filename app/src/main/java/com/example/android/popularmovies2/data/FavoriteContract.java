package com.example.android.popularmovies2.data;

import android.provider.BaseColumns;

/**
 * Created by mariona on 27/2/17.
 */

public class FavoriteContract {
    public static final class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_TITLE = "title";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT NOT NULL" +
                "); ";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String SQL_DELETE_ROW = _ID + " = %s";
    }
}
