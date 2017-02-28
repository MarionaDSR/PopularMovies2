package com.example.android.popularmovies2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.popularmovies2.data.FavoriteContract.FavoriteEntry;
/**
 * Created by mariona on 27/2/17.
 */

public class FavoriteDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "favorite.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;
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
}