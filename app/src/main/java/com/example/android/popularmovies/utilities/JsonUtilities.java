package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.movies.Movie;
import com.example.android.popularmovies.detailactivity.reviews.Review;
import com.example.android.popularmovies.detailactivity.trailers.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public final class JsonUtilities {

    final static String TMDB_PAGE = "page";
    final static String TMDB_RESULTS = "results";
    final static String TMDB_TOTAL_RESULTS = "total_results";
    final static String TMDB_TOTAL_PAGES = "total_pages";

    final static String TMDB_TITLE = "title";
    final static String TMDB_ORIGINAL_TITLE = "original_title";
    final static String TMDB_MOVIE_ID = "id";
    final static String TMDB_MOVIE_POSTER = "poster_path";
    final static String TMDB_PLOT_SYNOPSIS = "overview";
    final static String TMDB_Rating = "vote_average";
    final static String TMDB_RELEASE_DATE = "release_date";

    public static String getPageNumber(String moviesJsonStr) throws JSONException {

        JSONObject responseJsonObject = new JSONObject(moviesJsonStr);

        String pageNumber = responseJsonObject.getString(TMDB_PAGE);

        return pageNumber;
    }

    /**
     * This method parses JSON from a web response and returns an ArrayList of Movie Objects
     * that hold information (Title, Poster Path, Overview, Rating and Release Date) about movies.
     *
     * @param moviesJsonStr JSON response from server.
     * @return ArrayList of Movie Objects.
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static ArrayList<Movie> getMovieInfo(String moviesJsonStr) throws JSONException {

        ArrayList<Movie> movies = new ArrayList<Movie>();

        JSONObject responseJsonObject = new JSONObject(moviesJsonStr);

        JSONArray moviesJSONArray = responseJsonObject.getJSONArray(TMDB_RESULTS);

        String title = null;
        String original_title = null;
        String movie_id = null;
        String moviePoster = null;
        String plot_synopsis = null;
        String rating = null;
        String release_date = null;

        Movie movieObject = null;

        for (int i = 0; i < moviesJSONArray.length(); i++) {

            JSONObject movieJSONObject = moviesJSONArray.getJSONObject(i);

            title = movieJSONObject.getString(TMDB_TITLE);
            original_title = movieJSONObject.getString(TMDB_ORIGINAL_TITLE);
            movie_id = movieJSONObject.getString(TMDB_MOVIE_ID);
            moviePoster = movieJSONObject.getString(TMDB_MOVIE_POSTER);
            plot_synopsis = movieJSONObject.getString(TMDB_PLOT_SYNOPSIS);
            rating = movieJSONObject.getString(TMDB_Rating);
            release_date = movieJSONObject.getString(TMDB_RELEASE_DATE);

            movieObject = new Movie(title,original_title,movie_id,moviePoster,plot_synopsis,rating,release_date);

            movies.add(movieObject);

        }

        return movies;

    }

    /**
     * This method parses JSON from a web response and returns an ArrayList of Review Objects.
     *
     * @param reviewsJsonStr) JSON response from server.
     * @return ArrayList of Review Objects.
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static ArrayList<Review> getReviews(String reviewsJsonStr) throws JSONException {

        JSONObject responseJsonObject = new JSONObject(reviewsJsonStr);

        JSONArray reviewsJSONArray = responseJsonObject.getJSONArray("results");

        String author = null;
        String content = null;
        String url = null;

        ArrayList<Review> reviews = new ArrayList<Review>();

        Review reviewObject = null;

        for (int i = 0; i < reviewsJSONArray.length(); i++) {

            JSONObject reviewsJSONObject = reviewsJSONArray.getJSONObject(i);

            author = reviewsJSONObject.getString("author");
            content = reviewsJSONObject.getString("content");
            url = reviewsJSONObject.getString("url");

            reviewObject = new Review(author,content,url);

            reviews.add(reviewObject);

        }

        return reviews;

    }

    /**
     * This method parses JSON from a web response and returns an ArrayList of Trailer Objects.
     *
     * @param trailersJsonStr) JSON response from server.
     * @return ArrayList of Trailer Objects.
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static ArrayList<Trailer> getTrailers(String trailersJsonStr) throws JSONException {

        JSONObject responseJsonObject = new JSONObject(trailersJsonStr);

        JSONArray reviewsJSONArray = responseJsonObject.getJSONArray("youtube");

        String name = null;
        String size = null;
        String source = null;
        String type = null;

        ArrayList<Trailer> trailers = new ArrayList<Trailer>();

        Trailer trailerObject = null;

        for (int i = 0; i < reviewsJSONArray.length(); i++) {

            JSONObject trailersJSONObject = reviewsJSONArray.getJSONObject(i);

            name = trailersJSONObject.getString("name");
            size = trailersJSONObject.getString("size");
            source = trailersJSONObject.getString("source");
            type = trailersJSONObject.getString("type");

            trailerObject = new Trailer(name,size,source,type);

            trailers.add(trailerObject);

        }

        return trailers;

    }


    public static String getTotalResults(String moviesJsonStr) throws JSONException {

        JSONObject responseJsonObject = new JSONObject(moviesJsonStr);

        String totalResults = responseJsonObject.getString(TMDB_TOTAL_RESULTS);

        return totalResults;
    }

    public static String getTotalPages(String moviesJsonStr) throws JSONException {

        JSONObject responseJsonObject = new JSONObject(moviesJsonStr);

        String totalPages = responseJsonObject.getString(TMDB_TOTAL_PAGES);

        return totalPages;
    }


}
