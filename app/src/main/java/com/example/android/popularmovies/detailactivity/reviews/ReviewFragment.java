package com.example.android.popularmovies.detailactivity.reviews;

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

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.utilities.ExpListUtility;
import com.example.android.popularmovies.utilities.NetworkUtilities;

import java.util.ArrayList;

public class ReviewFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<Review>> {

    private String movie_id;

    private ArrayList<Review> reviews;

    private ReviewExpListAdapter expListAdapter;

    private ExpandableListView expListView;

    // This flag is used so we can tell if data from the TrailerLoader are expected to arrive.
    private boolean waitingForData = false;

    // This ProgressBar is used to indicate that data are expected to arrive from the TrailerLoader.
    private ProgressBar reviewLoadingBar;

    // The ID for the Review Loader.
    private static final int Review_Loader_ID = 101;

    private boolean isExpanded = false;


    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get the listview
        expListView = (ExpandableListView) getView().findViewById(R.id.review_list);

        // Gets a reference to the ProgressBar.
        reviewLoadingBar = (ProgressBar) getView().findViewById(R.id.pbReviewLoading);

        /**
         * If the fragment had already been created we restore the previous state.
         */
        if( savedInstanceState != null ) {
            reviews = savedInstanceState.getParcelableArrayList("reviews");

            waitingForData = savedInstanceState.getBoolean("waiting_for_data");

            isExpanded = savedInstanceState.getBoolean("isExpanded");

            setAdapterToExpList();

        } else {
            waitingForData = true;
            reviews = new ArrayList<Review>();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        if (isExpanded) {
            expListView.collapseGroup(0);
        }

        // We make the ProgressBar visible to the user.
        reviewLoadingBar.setVisibility(ProgressBar.VISIBLE);

        // We connect to the TrailerLoader.
        getActivity().getSupportLoaderManager().initLoader(Review_Loader_ID, null, this);
    }

    public void setMovieId(String id) {
        this.movie_id=id;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("reviews", reviews);

        outState.putBoolean("waiting_for_data", waitingForData);

        outState.putBoolean("isExpanded", expListView.isGroupExpanded(0));

        super.onSaveInstanceState(outState);

    }

    @Override
    public Loader<ArrayList<Review>> onCreateLoader(int id, Bundle args) {

        return new ReviewLoader(getContext(),NetworkUtilities.buildReviewsUrl(movie_id));

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Review>> loader, ArrayList<Review> reviews) {

        // If we are waiting for data to arrive and the data is not null ( in this case null means
        // that the movie has no trailers ) we create the adapter and present the trailers to the user.
        if (waitingForData && reviews!=null) {
            this.reviews = reviews;

            waitingForData=false;
            setAdapterToExpList();
        }

        // The ProgressBar becomes invisible.
        // At this point the user is either presented with a list of trailers
        // or he is informed that the specific movie has no trailers.
        reviewLoadingBar.setVisibility(ProgressBar.INVISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Review>> loader) {
        expListView.setAdapter(new ReviewExpListAdapter(null));
    }

    private void setAdapterToExpList(){
        expListAdapter = new ReviewExpListAdapter(reviews);

        // setting list adapter
        expListView.setAdapter(expListAdapter);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                ExpListUtility.setListViewHeight(parent, groupPosition);
                return false;
            }
        });
    }

}
