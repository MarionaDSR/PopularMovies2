package com.example.android.popularmovies2.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popularmovies2.model.Movie;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies2.data.FavoriteContract.FavoriteEntry;
/**
 * Created by mariona on 27/2/17.
 */

public class FavoriteDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "favorite.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 2;
    // Constructor
    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(FavoriteEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL(FavoriteEntry.SQL_DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public static List<Movie> getMoviesFromCursor(Cursor cursor) {
        List<Movie> movies = new ArrayList<>();
        int idPos = cursor.getColumnIndex(FavoriteEntry._ID);
        int titlePos = cursor.getColumnIndex(FavoriteEntry.COLUMN_TITLE);
        int posterPos = cursor.getColumnIndex(FavoriteEntry.COLUMN_POSTER);
        int synopsysPos = cursor.getColumnIndex(FavoriteEntry.COLUMN_SYNOPSYS);
        int userRatingPos = cursor.getColumnIndex(FavoriteEntry.COLUMN_USER_RATING);
        int releaseDatePos = cursor.getColumnIndex(FavoriteEntry.COLUMN_RELEASE_DATE);

        while (cursor.moveToNext()) {
            Movie m = new Movie();
            m.setId(cursor.getInt(idPos));
            m.setOriginalTitle(cursor.getString(titlePos));
            m.setPosterPath(cursor.getString(posterPos));
            m.setOverview(cursor.getString(synopsysPos));
            m.setVoteAverage(cursor.getDouble(userRatingPos));
            m.setReleaseDate(cursor.getString(releaseDatePos));
            m.setFavorite(true);
            movies.add(m);
        }
        cursor.close();
        return movies;
    }

    public boolean checkFavorite(Movie m) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns  = {FavoriteEntry._ID};
        String[] values = {m.getId() +  ""};
        Cursor cursor = db.query(FavoriteEntry.TABLE_NAME,
                columns,
                FavoriteEntry._ID + " =?",
                values,
                null,
                null,
                null);
        return cursor.moveToFirst();
    }
}