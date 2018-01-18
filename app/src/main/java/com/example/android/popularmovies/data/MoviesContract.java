package com.example.android.popularmovies.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public class MoviesContract {

    // The Content Authority of the Provider.
    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";

    // The base content URI = "content://" + <authority>.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // This is the path for the "movies" directory.
    public static final String PATH_MOVIES = "movies";


    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;


        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_MOVIE_POSTER_PATH = "poster_path";

        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "release_date";

        public static Uri buildCostUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
