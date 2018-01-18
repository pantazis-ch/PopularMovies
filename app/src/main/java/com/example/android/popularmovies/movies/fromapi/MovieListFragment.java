package com.example.android.popularmovies.movies.fromapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.detailactivity.DetailActivity;
import com.example.android.popularmovies.movies.Movie;

import java.util.ArrayList;

public class MovieListFragment extends Fragment implements MovieAdapter.MovieAdapterScrollHandler,
        MovieAdapter.MovieAdapterOnClickHandler, LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    // This ArrayList holds the movies we get from the Api.
    // We, continually, add new pages of movies to this list.
    private ArrayList<Movie> movies = null;

    // The next page we are going to get from the Api.
    private int nextPage = 1;

    // We use this flag so we can know when a new page of data
    // is expected to arrive from the MovieLoader.
    private boolean waitingForData = false;

    // The RecyclerView that will hold the movie posters.
    private RecyclerView mRecyclerView;

    // The LayoutManager for the RecyclerView.
    private RecyclerView.LayoutManager mLayoutManager;

    // The adapter that will populate the RecyclerView with movie posters.
    private MovieAdapter mAdapter;

    // Used to handle clicks on RecyclerView child views.
    private MovieAdapter.MovieAdapterOnClickHandler clickHandler = null;

    // Used to tell when the user has reached the end of the RecyclerView,
    // so we can make a request to the api for a new page of data.
    // In this case we assume that the user has reached the end of the list when
    // the last item has been bound.
    private MovieAdapter.MovieAdapterScrollHandler scrollHandler;

    // This progressbar is presented to the user until the first page of data has been loaded.
    private ProgressBar mLoadingIndicator;

    // This code is used to distinguish between the loaders.
    // In our case, we have two loaders, one for 'Most Popular' and one for 'Highest Rated'
    // We need this because we might have two instances of this fragment running at the same time.
    private int loaderCode;

    // Loader Code for 'Most Popular' movies.
    private static final int MOST_POPULAR_LOADER_CODE = 500;

    // Loader Code for 'Highest Rated' movies.
    private static final int HIGHEST_RATED_LOADER_CODE = 501;

    // The 'Try Again' button is used to reload the first page
    // when an error has occurred ( no network connectivity etc. )
    private Button mButton;

    private Context mContext;

    /**
     * Creates a new instance of MovieListFragment, initialized to
     * show either a list of the 'Most Popular' movies or the 'Highest Rated'.
     */
    public static MovieListFragment newInstance(String sortCriteriaKey) {
        MovieListFragment fragment = new MovieListFragment();

        // Supply search category input as an argument.
        Bundle args = new Bundle();
        args.putString("search_category", sortCriteriaKey);
        fragment.setArguments(args);

        return fragment;
    }

    private String getSearchCategory() {
        return getArguments().getString("search_category", "popular");
    }

    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getGuiReferences();

        setupListeners();

        determineLoaderCode();

        mContext = getContext();

        if (savedInstanceState!=null) {
            this.movies = savedInstanceState.getParcelableArrayList("movies");
            this.waitingForData = savedInstanceState.getBoolean("waiting_for_data");
            this.nextPage = savedInstanceState.getInt("next_page");
        } else {
            movies = new ArrayList<Movie>();
            waitingForData = true;
        }

        // We set up the RecyclerView.
        setupRecyclerView();

    }

    private void getGuiReferences() {
        mLoadingIndicator = (ProgressBar) getView().findViewById(R.id.pb_loading_indicator);

        mButton = (Button) getView().findViewById(R.id.btn_error_message_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAgain();
            }
        });
    }

    private void setupListeners() {
        clickHandler = this;

        scrollHandler = this;
    }


    /**
     * This method is used to determine the Loader Code for the loader
     * that we are going to use in order to fetch the data from the TMDB Api.
     * It can be either a code for 'Most Popular' movies or for 'Highest Rated'.
     */
    private void determineLoaderCode() {
        if (getSearchCategory().equals("popular")) {
            loaderCode = MOST_POPULAR_LOADER_CODE;
        } else if (getSearchCategory().equals("top_rated")) {
            loaderCode = HIGHEST_RATED_LOADER_CODE;
        }
    }

    /**
     * This method is used to setup the RecyclerView.
     */
    public void setupRecyclerView() {

        // Reference to the RecyclerView.
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.rv_movies);

        // Setting up the adapter.
        mAdapter = new MovieAdapter(movies, scrollHandler, clickHandler);
        mRecyclerView.setAdapter(mAdapter);

        // Setting up the Layout Manager, a GridLayoutManager with two columns.
        //mLayoutManager = new GridLayoutManager(getContext(),2);
        mLayoutManager = new GridLayoutManager(getContext(),calculateNoOfColumns());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

    }

    public int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Make the ProgressBar Visible to the user.
        // It indicates that data will be, shortly, show to the screen.
        mLoadingIndicator.setVisibility(View.VISIBLE);

        // Create or reconnect to the MovieLoader.
        getActivity().getSupportLoaderManager().initLoader(loaderCode, null, this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        // Saves the movie list.
        outState.putParcelableArrayList("movies",movies);

        // Saves the 'waitingForData' variable so we can know
        // if a new page of movies is expected.
        outState.putBoolean("waiting_for_data", waitingForData);

        outState.putInt("next_page", nextPage);

        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {

        return new MovieLoader(getContext(), getSearchCategory(), nextPage);

    }



    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> newMovies) {

        mLoadingIndicator.setVisibility(View.GONE);

        if (waitingForData) {

            if (newMovies != null) {
                movies.addAll(newMovies);
                mAdapter.notifyDataSetChanged();
                nextPage++;
            } else {
                if (movies.size() == 0) {
                    showErrorMessage();
                }
            }

            waitingForData = false;

        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        //mRecyclerView.setAdapter(new MovieAdapter(movies,scrollHandler,clickHandler,getContext()));
    }

    /**
     * This method will make the error message visible and hide the RecyclerView.
     */
    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.GONE);

        LinearLayout linear = (LinearLayout) getView().findViewById(R.id.panel_error_message);
        linear.setVisibility(View.VISIBLE);

    }

    /**
     * This method will make the error message visible and hide the RecyclerView.
     */
    private void hideErrorMessage() {

        mRecyclerView.setVisibility(View.VISIBLE);

        LinearLayout linear = (LinearLayout) getView().findViewById(R.id.panel_error_message);
        linear.setVisibility(View.GONE);

    }

    public void tryAgain() {

        hideErrorMessage();

        mLoadingIndicator.setVisibility(View.VISIBLE);

        waitingForData = true;

        getActivity().getSupportLoaderManager().restartLoader(loaderCode, null, this);

    }

    /**
     * When a RecyclerView child is clicked then the Movie Object, that is tied
     * to the specific child, is passed to the Detail Activity.
     * @param movie
     */
    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("movie", movie);
        intent.putExtra("getBitmapFromMemory", false);
        startActivity(intent);
    }

    @Override
    public void onScrolledToTheEnd() {

        waitingForData = true;

        getActivity().getSupportLoaderManager().restartLoader(loaderCode, null, this);

    }
}
