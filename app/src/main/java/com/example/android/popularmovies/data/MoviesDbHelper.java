package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popularmovies.data.MoviesContract.MovieEntry;


public class MoviesDbHelper extends SQLiteOpenHelper {

    // The Database Name.
    public static final String DATABASE_NAME = "favorite_movies.db";

    // The Database Version.
    private static final int DATABASE_VERSION = 1;


    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold favorite movies.
        final String SQL_CREATE_FAVORITE_MOVIES_TABLE =
                "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                        MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
