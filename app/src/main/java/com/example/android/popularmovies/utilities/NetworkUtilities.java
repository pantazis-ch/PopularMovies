package com.example.android.popularmovies.utilities;

import android.net.Uri;

import com.example.android.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtilities {

    final static String TMDB_BASE_URL =
            "https://api.themoviedb.org";

    final static String TMDB_API_VERSION = "3";

    final static String API_KEY = "api_key";

    final static String TMDB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    final static String TMDB_IMAGE_SIZE = "w185";

    final static String TMDB_REVIEWS_BASE_URL = "https://api.themoviedb.org/3/movie";

    final static String TMDB_TRAILERS_BASE_URL = "https://api.themoviedb.org/3/movie";

    final static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch";

    /**
     * Builds the URL used to query The MovieDB Api.
     *
     * @param sortCriteriaPref Ask for the Most Popular or Top Rated movies.
     * @param page The page that will be retrieved.
     * @return The URL to use to query The Movie DB.
     */
    public static URL buildUrl(String sortCriteriaPref, String page) {

        Uri builtUri = null;

        builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath(sortCriteriaPref)
                .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                .appendQueryParameter("page",page)
                .build();


        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Builds the URL used to get a poster from The MovieDB Api.
     *
     * @param posterPath The path of the poster of a specific movie.
     * @return The URL that will be used to get the poster from The MovieDB.
     */
    public static URL buildImageUrl(String posterPath) {

        Uri builtUri = Uri.parse(TMDB_IMAGE_BASE_URL).buildUpon()
                .appendPath(TMDB_IMAGE_SIZE)
                .appendPath(posterPath.replace("/",""))
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Builds the URL used to get a list of trailers for a specific movie from The MovieDB Api.
     *
     * @param movieId The unique TMDB movie id.
     * @return The URL that will be used to get the trailers from The MovieDB.
     */
    public static URL buildTrailersUrl(String movieId) {

        Uri builtUri = Uri.parse(TMDB_TRAILERS_BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath("trailers")
                .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Builds the URL used to get a list of reviews for a specific movie from The MovieDB Api.
     *
     * @param movieId The unique TMDB movie id.
     * @return The URL that will be used to get the reviews from The MovieDB.
     */
    public static URL buildReviewsUrl(String movieId) {

        Uri builtUri = Uri.parse(TMDB_REVIEWS_BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath("reviews")
                .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Builds the URL used to get a poster from The MovieDB Api.
     *
     * @param source The path of the Youtube video.
     * @return The Youtube URL for the specific path..
     */
    public static URL buildYoutubeUrl(String source) {

        Uri builtUri = Uri.parse(YOUTUBE_BASE_URL).buildUpon()
                .appendQueryParameter("v", source)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading.
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

}
