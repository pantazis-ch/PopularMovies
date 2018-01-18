package com.example.android.popularmovies.movies.fromapi;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.popularmovies.movies.Movie;
import com.example.android.popularmovies.utilities.JsonUtilities;
import com.example.android.popularmovies.utilities.NetworkUtilities;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>>  {

    // An ArrayList that holds a page of movies.
    // More specifically, it holds the last page that has been fetched from the api.
    private ArrayList<Movie> movies = null;

    // The page that is going to be fetched next.
    // It starts from 0 and at every forceLoad() it is incremented by 1.
    // If the movies ArrayList equals null in the deliverResult(),
    // then it is decreased by 1 because we didn't get a new page successfully.
    private int nextPage = 0;

    // Used to indicate whether the Loader will query for the 'Most Popular' movies
    // or the 'Highest Rated'.
    private String sortCriteria;


    public MovieLoader(Context context, String sortCriteria, int pageCounter) {
        super(context);

        this.sortCriteria = sortCriteria;

        nextPage = pageCounter;

    }

    @Override
    public void onStartLoading() {
        super.onStartLoading();

        if( movies != null) {
            deliverResult(movies);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        movies = null;
        nextPage = 0;
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {

        // Building the Url that we are going to use in order to fetch data from The MovieDb Api.
        // The sortCriteria parameter defines the category that we are going to query,
        // while the nextPage defines the page in that category that we are going to fetch.
        URL TMDBQueryUrl = NetworkUtilities.buildUrl(sortCriteria, String.valueOf(nextPage));

        // The string that will host the response from the network.
        String TMDBQueryResult = null;

        // The ArrayList that will hold the Movie Objects after the JSON parsing.
        ArrayList<Movie> movies = null;

        try {
            // Query the network.
            TMDBQueryResult = NetworkUtilities.getResponseFromHttpUrl(TMDBQueryUrl);

            // Parse the response and get a list of Movie Obects.
            movies = JsonUtilities.getMovieInfo(TMDBQueryResult);

        } catch (IOException e) {
            // Network Error.
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON Parsing Error.
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    public void deliverResult(ArrayList<Movie> newMovies) {

        // We cache the last page of movies in case we need it ( Orientation change etc. ).
        this.movies = newMovies;

        // We deliver the new movies.
        // the MovieListFragment knows how to handle all scenarios ( new page or no page ).
        super.deliverResult(newMovies);

    }

}
