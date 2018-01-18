package com.example.android.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.popularmovies.data.MoviesContract;
import com.example.android.popularmovies.data.MoviesDbHelper;

import java.util.Map;
import java.util.Set;


public class TestDB extends AndroidTestCase {

    public static final String LOG_TAG = TestDB.class.getSimpleName();

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new MoviesDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadDb() {

        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = createMovieValues();

        long costRowId;
        costRowId = db.insert(MoviesContract.MovieEntry.TABLE_NAME, null, testValues);

        assertTrue(costRowId != -1);
        Log.d(LOG_TAG, "New row id: " + costRowId);

        Cursor cursor = db.query(
                MoviesContract.MovieEntry.TABLE_NAME, // Table to Query
                null,
                null,
                null,
                null,
                null,
                null
        );

        validateCursor(cursor, testValues);

        dbHelper.close();
    }

    static ContentValues createMovieValues() {
        ContentValues testValues = new ContentValues();
        testValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, "72");
        testValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE, "Pirates of the Caribbean");
        testValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, "a story for a pirate of the Caribbean");
        testValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, "/gghsdhdghgg");
        testValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, "2003");
        testValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE, "Pirates of the Caribbean");
        testValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, "7.7");
        return testValues;
    }

    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {
        assertTrue(valueCursor.moveToFirst());
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int index = valueCursor.getColumnIndex(columnName);
            assertFalse(index == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(index));
        }
        valueCursor.close();
    }
}
