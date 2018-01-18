package com.example.android.popularmovies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.android.popularmovies.data.MoviesContract;
import com.example.android.popularmovies.data.MoviesContract.MovieEntry;


public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    // Brings the database to an empty state.
    public void deleteAllRecords() {
        mContext.getContentResolver().delete(
                MovieEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(0, cursor.getCount());
        cursor.close();
    }

    // Each time the test starts all records are deleted.
    public void setUp() {
        deleteAllRecords();
    }

    public void testInsertReadProvider() {

        // Creating some test values ( One Movie Entry ).
        ContentValues testValues = TestDB.createMovieValues();

        // Inserting a Row to the provider.
        Uri movieUri = mContext.getContentResolver().insert(
                MovieEntry.CONTENT_URI,
                testValues);

        long movieRowId = ContentUris.parseId(movieUri);

        // Verify that we got a valid row id back.
        assertTrue(movieRowId != -1);

        // Query the provider...
        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        // Validate cursor with testValues.
        TestDB.validateCursor(cursor, testValues);

        // Query the provider for the specific id.
        cursor = mContext.getContentResolver().query(
                MovieEntry.buildCostUri(movieRowId),
                null,
                null,
                null,
                null
        );

        // Validate result.
        TestDB.validateCursor(cursor, testValues);
    }

    public void testGetType() {

        // content://com.example.android.popularmovies.app/movies/
        String type = mContext.getContentResolver().getType(MovieEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.popularmovies/movies
        assertEquals(MovieEntry.CONTENT_TYPE, type);

        // content://com.example.android.popularmovies.app/movies/1
        type = mContext.getContentResolver().getType(MovieEntry.buildCostUri(1L));
        // vnd.android.cursor.item/com.example.android.popularmovies/movie
        assertEquals(MovieEntry.CONTENT_ITEM_TYPE, type);
    }

    public void testUpdateMovie() {

        ContentValues values = TestDB.createMovieValues();

        Uri costUri = mContext.getContentResolver().insert(MovieEntry.CONTENT_URI, values);

        long locationRowId = ContentUris.parseId(costUri);

        assertTrue(locationRowId != -1);

        Log.d(LOG_TAG, "New row id: " + locationRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, "72");
        updatedValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE, "Pirates of the Caribbean");
        updatedValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, "a story for a pirate of the Caribbean");
        updatedValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, "/gghsdhdghgg");
        updatedValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, "2003");
        updatedValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE, "Pirates of the Caribbean");
        updatedValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, "7.7");

        int count = mContext.getContentResolver().update(
                MovieEntry.CONTENT_URI, updatedValues, MovieEntry._ID + "= ?",
                new String[] { Long.toString(locationRowId)});

        assertEquals(count, 1);


        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.buildCostUri(locationRowId),
                null,
                null,
                null,
                null
        );
        TestDB.validateCursor(cursor, updatedValues);
    }

    public void testDeleteRecordsAtEnd() {
        deleteAllRecords();
    }

}
