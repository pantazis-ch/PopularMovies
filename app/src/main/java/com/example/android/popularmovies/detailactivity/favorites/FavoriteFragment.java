package com.example.android.popularmovies.detailactivity.favorites;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MoviesContract;
import com.example.android.popularmovies.movies.Movie;
import com.example.android.popularmovies.utilities.FileUtility;


public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    // The movie that is displayed in the Detail Activity.
    private Movie movie = null;

    // The Image View that is used to indicate if the movie is included in favorites or not.
    // The user can toggle the status of the movie ( Favorite or Not Favorite )
    // by touching the ( Heart Shaped ) ImageView.
    private ImageView favoriteImageView = null;

    private String MOVIE_ID_COLUMN = MoviesContract.MovieEntry.COLUMN_MOVIE_ID;

    // A "projection" defines the columns that will be returned for each row.
    // In our case we only need the MOVIE_ID_COLUMN in order to compare the current movie
    // with those in the Favorite Movies List.
    private String[] mProjection = {
            MoviesContract.MovieEntry.COLUMN_MOVIE_ID
    };

    // Defines a string to contain the selection clause.
    private String mSelectionClause = MOVIE_ID_COLUMN + " = ?";

    // Initializes an array to contain selection arguments.
    private String[] mSelectionArgs = {""};

    private static final int CHECK_IF_M0VIE_IS_IN_FAVORITES_LOADER = 15;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movie = getActivity().getIntent().getParcelableExtra("movie");

        favoriteImageView = (ImageView) getView().findViewById(R.id.image_favorite);

        favoriteImageView.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().getSupportLoaderManager().initLoader(
                CHECK_IF_M0VIE_IS_IN_FAVORITES_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri CONTENT_URI = MoviesContract.MovieEntry.CONTENT_URI;

        mSelectionArgs[0] = movie.getMovieId();

        CursorLoader cursorLoader = new CursorLoader(getContext(), CONTENT_URI, mProjection,
                mSelectionClause, mSelectionArgs, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data.getCount() != 0) {
            // The movie belongs in the Favorite Movies list.
            favoriteImageView.setImageResource(R.drawable.ic_favorite_red_24dp);
            favoriteImageView.setTag("favorite");
        } else {
            favoriteImageView.setTag("notFavorite");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * This method is called when the user touches the ImageView ( Heart Shape ).
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (favoriteImageView.getTag().equals("notFavorite")) {
            favoriteImageView.setImageResource(R.drawable.ic_favorite_red_24dp);
            favoriteImageView.setTag("favorite");
            addMovieToFavorites();
        } else {
            favoriteImageView.setImageResource(R.drawable.ic_favorite_border_red_24dp);
            favoriteImageView.setTag("notFavorite");
            deleteMovieFromFavorites();
        }
    }

    /**
     * This method is called when we want to add a movie to Favorites.
     */
    private void addMovieToFavorites() {

        // Creating an empty ContentValues object.
        ContentValues contentValues = new ContentValues();

        // Putting the movie information into the ContentValues.
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, movie.getMovieId());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE, movie.getOriginalTitle());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, movie.getMoviePoster());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, movie.getDescription());

        // Inserting the content values via a ContentResolver.
        Uri uri = getContext().getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, contentValues);

        ImageView imageView = (ImageView) getActivity().findViewById(R.id.img_detail_poster);

        FileUtility.saveImageToInternalStorage(movie.getMovieId(),imageView,getContext());

    }

    /**
     * This method is called when we want to delete a movie from Favorites.
     */
    private void deleteMovieFromFavorites() {

        mSelectionArgs[0] = movie.getMovieId();

        int rowsDeleted = 0;

        rowsDeleted = getContext().getContentResolver().delete(
                MoviesContract.MovieEntry.CONTENT_URI,
                mSelectionClause,
                mSelectionArgs
        );

        boolean deleted = FileUtility.deleteImageFromMemory(movie.getMovieId(),getContext());

    }

}
