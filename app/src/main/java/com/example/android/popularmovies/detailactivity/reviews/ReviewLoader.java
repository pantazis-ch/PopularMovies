package com.example.android.popularmovies.detailactivity.reviews;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.popularmovies.utilities.JsonUtilities;
import com.example.android.popularmovies.utilities.NetworkUtilities;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class ReviewLoader extends AsyncTaskLoader<ArrayList<Review>> {

    private ArrayList<Review> reviews = null;

    private URL TMDBQueryUrl;

    public ReviewLoader(Context context, URL url) {
        super(context);
        this.TMDBQueryUrl = url;
    }

    @Override
    public void onStartLoading() {
        if (reviews != null) {
            deliverResult(reviews);
        } else {
            forceLoad();
        }
    }

    @Override
    public ArrayList<Review> loadInBackground() {

        // The string that will host the response from the network.
        String reviewsQueryResult = null;

        // The ArrayList that will hold the Review Objects after the JSON parsing.
        ArrayList<Review> reviews = null;

        try {

            // Query the network.
            reviewsQueryResult = NetworkUtilities.getResponseFromHttpUrl(TMDBQueryUrl);

            // Parse the response and get a list of Review Objects.
            reviews = JsonUtilities.getReviews(reviewsQueryResult);

        } catch (IOException e) {
            // Network Error.
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON Parsing Error.
            e.printStackTrace();
        }

        return reviews;
    }

    @Override
    public void deliverResult(ArrayList<Review> reviews) {

        // Caching the data for later retrieval.
        this.reviews = reviews;

        super.deliverResult(reviews);

    }
}
