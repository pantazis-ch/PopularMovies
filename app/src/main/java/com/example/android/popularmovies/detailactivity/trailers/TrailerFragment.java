package com.example.android.popularmovies.detailactivity.trailers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.utilities.ExpListUtility;
import com.example.android.popularmovies.utilities.NetworkUtilities;

import java.net.URL;
import java.util.ArrayList;


public class TrailerFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<Trailer>>, TrailerExpListAdapter.ClickHandler {

    // The TMDB movie_id that will be used in order to fetch Trailers for this movie.
    private String movie_id;

    // In this ArrayList we are going to store the trailers that are going to be fetched.
    private ArrayList<Trailer> trailers;

    // This ExpandableListView is used to present a list of Movie Trailers to the user.
    private ExpandableListView expListView;

    // This adapter is used so we can populate the ExpandableListView with data.
    private TrailerExpListAdapter expListAdapter;

    // This click handler is used so we can tell when the user wants to watch, or share,
    // a movie trailer.
    private TrailerExpListAdapter.ClickHandler clickHandler = null;

    // This flag is used so we can tell if data from the TrailerLoader are expected to arrive.
    private boolean waitingForData = false;

    // This ProgressBar is used to indicate that data are expected to arrive from the TrailerLoader.
    private ProgressBar trailerLoadingBar;

    // The ID for the Trailer Loader.
    private static final int Trailer_Loader_ID = 100;

    private boolean isExpanded = false;

    public TrailerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trailer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Gets a reference to the ExpListView.
        expListView = (ExpandableListView) getView().findViewById(R.id.trailer_list);

        // Gets a reference to the ProgressBar.
        trailerLoadingBar = (ProgressBar) getView().findViewById(R.id.pbTrailerLoading);

        clickHandler = this;

        /**
         * If the fragment had already been created we restore the previous state.
         */
        if( savedInstanceState != null ) {
            trailers = savedInstanceState.getParcelableArrayList("trailers");

            waitingForData = savedInstanceState.getBoolean("waiting_for_data");

            isExpanded = savedInstanceState.getBoolean("isExpanded");
            setAdapterToExpList();


        } else {
            waitingForData = true;
            trailers = new ArrayList<Trailer>();
        }
    }

    /**
     * Sets the movie_id for this fragment.
     * @param movie_id
     */
    public void setMovieId(String movie_id) {
        this.movie_id=movie_id;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (isExpanded) {
            expListView.collapseGroup(0);
        }

        // We make the ProgressBar visible to the user.
        trailerLoadingBar.setVisibility(ProgressBar.VISIBLE);

        // We connect to the TrailerLoader.
        getActivity().getSupportLoaderManager().initLoader(Trailer_Loader_ID, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("trailers", trailers);

        outState.putBoolean("waiting_for_data", waitingForData);

        outState.putBoolean("isExpanded", expListView.isGroupExpanded(0));

        super.onSaveInstanceState(outState);

    }

    @Override
    public Loader<ArrayList<Trailer>> onCreateLoader(int id, Bundle args) {

        // We build the URL that will be used to fetch the trailers from TheMovieDB Api.
        URL trailersUrl = NetworkUtilities.buildTrailersUrl(movie_id);

        // Returns a new TrailerLoader for the Trailer Url that we provided.
        return new TrailerLoader(getContext(), trailersUrl);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Trailer>> trailerLoader, ArrayList<Trailer> trailers) {

        // If we are waiting for data to arrive and the data is not null ( in this case null means
        // that the movie has no trailers ) we create the adapter and present the trailers to the user.
        if (waitingForData && trailers!=null) {
            this.trailers = trailers;

            waitingForData=false;
            setAdapterToExpList();
        }

        // The ProgressBar becomes invisible.
        // At this point the user is either presented with a list of trailers
        // or he is informed that the specific movie has no trailers.
        trailerLoadingBar.setVisibility(ProgressBar.INVISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Trailer>> loader) {
        expListView.setAdapter(new TrailerExpListAdapter(null, clickHandler));
    }

    private void setAdapterToExpList(){
        expListAdapter = new TrailerExpListAdapter(trailers, clickHandler);

        // Setting list adapter
        expListView.setAdapter(expListAdapter);

        // Setting an onGroupClickListener().
        // When the group is clicked we calculate and allocate the new height of the list.
        // This is done because we have an ExpandableListView inside of a ScrollView.
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                ExpListUtility.setListViewHeight(parent, groupPosition);
                return false;
            }
        });
    }

    /**
     * In this method we handle a user click on a specific trailer in the child ListView
     * of the ExpandableListView.
     * The user might click to watch a trailer or share a trailer link.
     *
     * @param trailer --> The Trailer that corresponds to the ListView item that was clicked.
     * @param action --> The action that the user selected ( 'watch' or 'share' ).
     */
    @Override
    public void onClick(Trailer trailer, String action) {
        switch (action){
            case "watch":
                createWatchIntent(trailer);
                break;
            case "share":
                createShareIntent(trailer);
                break;
            default:
                Toast toast = Toast.makeText(getContext(),"Error...", Toast.LENGTH_SHORT);
                toast.show();
        }

    }

    /**
     * Creates an intent for watching a youtube video.
     *
     * @param trailer The trailer that the user selected.
     */
    private void createWatchIntent(Trailer trailer) {

        String trailerLink = String.valueOf(NetworkUtilities.buildYoutubeUrl(trailer.getSource()));

        Uri trailerUri = Uri.parse(trailerLink);

        Intent watchIntent = new Intent(Intent.ACTION_VIEW);

        watchIntent.setData(trailerUri);

        Intent watchIntentChooser = Intent.createChooser(watchIntent, "Watch Trailer...");

        if (watchIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(watchIntentChooser);
        }
    }

    /**
     * Creates an Intent for sharing the YouTube video link.
     *
     * @param trailer The trailer that the user selected.
     */
    public void createShareIntent(Trailer trailer) {

        String trailerLink = String.valueOf(NetworkUtilities.buildYoutubeUrl(trailer.getSource()));

        Uri trailerUri = Uri.parse(trailerLink);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.putExtra(Intent.EXTRA_TEXT, trailerUri.toString());

        shareIntent.setType("text/plain");

        Intent shareIntentChooser = Intent.createChooser(shareIntent, "Share Trailer Link...");

        if (shareIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(shareIntentChooser);
        }
    }

}
