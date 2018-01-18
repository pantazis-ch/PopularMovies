package com.example.android.popularmovies.movies.favorites;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MoviesContract;
import com.example.android.popularmovies.detailactivity.DetailActivity;
import com.example.android.popularmovies.movies.Movie;

import java.util.ArrayList;

public class FavoriteMoviesFragment extends Fragment implements MovieCursorAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;

    private MovieCursorAdapter mAdapter;

    private MovieCursorAdapter.MovieAdapterOnClickHandler clickHandler;

    private static final int GET_ALL_FAVORITE_MOVIES_LOADER = 600;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        clickHandler = this;

        // Reference to the RecyclerView.
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.rv_favorite_movies);

        // Setting up the adapter.
        mAdapter = new MovieCursorAdapter(getContext(), clickHandler);
        mRecyclerView.setAdapter(mAdapter);

        // Setting up the Layout Manager, a GridLayoutManager with two columns.
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

        getLoaderManager().initLoader(GET_ALL_FAVORITE_MOVIES_LOADER, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri CONTENT_URI = MoviesContract.MovieEntry.CONTENT_URI;

        CursorLoader cursorLoader = new CursorLoader(getContext(), CONTENT_URI, null,
                null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        TextView tv = (TextView) getView().findViewById(R.id.tv_no_favorite_movies);

        if (data.getCount()==0) {
            mRecyclerView.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);

            mAdapter.swapCursor(data);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("movie", movie);
        intent.putExtra("getBitmapFromMemory", true);
        startActivity(intent);
    }

}
