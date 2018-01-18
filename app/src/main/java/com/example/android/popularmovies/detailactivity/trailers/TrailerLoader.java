package com.example.android.popularmovies.detailactivity.trailers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.popularmovies.utilities.JsonUtilities;
import com.example.android.popularmovies.utilities.NetworkUtilities;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class TrailerLoader extends AsyncTaskLoader<ArrayList<Trailer>>  {

    // The ArrayList that will hold the list of trailers
    // that we will get from the Api.
    private ArrayList<Trailer> trailers = null;

    // The Url that we will use in order to query the Api.
    private URL trailerQueryUrl = null;

    public TrailerLoader(Context context, URL url) {
        super(context);
        this.trailerQueryUrl = url;
    }

    @Override
    public void onStartLoading() {
        if (trailers != null) {
            deliverResult(trailers);
        } else {
            forceLoad();
        }
    }

    @Override
    public ArrayList<Trailer> loadInBackground() {

        // The string that will host the response from the network.
        String TrailerQueryResult = null;

        // The ArrayList that will hold the Trailer Objects after the JSON parsing.
        ArrayList<Trailer> trailers = null;

        try {

            // Query the network.
            TrailerQueryResult = NetworkUtilities.getResponseFromHttpUrl(trailerQueryUrl);

            // Parse the response and get a list of Trailer Objects.
            trailers = JsonUtilities.getTrailers(TrailerQueryResult);

        } catch (IOException e) {
            // Network Error.
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON Parsing Error.
            e.printStackTrace();
        }

        return trailers;
    }

    @Override
    public void deliverResult(ArrayList<Trailer> trailers) {

        // We save the trailer list in case we need it again ( Configuration Change etc. )
        this.trailers = trailers;

        // We deliver the Trailer List.
        super.deliverResult(trailers);
    }
}
