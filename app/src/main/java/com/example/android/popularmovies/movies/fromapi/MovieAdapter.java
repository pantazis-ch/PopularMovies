package com.example.android.popularmovies.movies.fromapi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.movies.Movie;
import com.example.android.popularmovies.utilities.NetworkUtilities;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    /**
     * The interface that is triggered when the last item
     * in the RecyclerView has been bound.
     */
    public interface MovieAdapterScrollHandler {
        void onScrolledToTheEnd();
    }

    /**
     * An onClickHandler handler that we have defined to make it easy for an Activity to interface with
     * our RecyclerView.
     */
    private final MovieAdapterOnClickHandler mClickHandler;

    private final MovieAdapterScrollHandler mScrollHandler;

    /**
     * An ArrayList that contains all the movie Objects that exist
     * in the adapter and the RecyclerView.
     */
    private ArrayList<Movie> movies = null;

    // Provide a suitable constructor (depends on the kind of dataset).
    public MovieAdapter(ArrayList<Movie> movies, MovieAdapterScrollHandler scrollHandler, MovieAdapterOnClickHandler clickHandler) {
        this.movies = movies;
        mClickHandler = clickHandler;
        mScrollHandler = scrollHandler;
    }

    /**
     * This method gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  The type of row layout. We have only one type, 'movie_list_item'.
     * @return A new MovieAdapterViewHolder that holds the View for each list item.
     */
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.movie_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new ViewHolder(view);

    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the
     * Movie Poster for this particular position, using the "position" argument.
     *
     * @param viewHolder The ViewHolder which should be updated to represent the
     *                   contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder viewHolder, int position) {

        URL posterUrl = NetworkUtilities.buildImageUrl(movies.get(position).getMoviePoster());

        Context ctx = viewHolder.mImageView.getContext();

        Picasso.with(ctx).load(String.valueOf(posterUrl)).into(viewHolder.mImageView);

        if (position == movies.size()-1 && (movies.size() != 0) ) {
            mScrollHandler.onScrolledToTheEnd();
        }

    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in the movies ArrayList.
     */
    @Override
    public int getItemCount() {
        if (movies == null) {
            return 0;
        }
        return movies.size();
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image_view_rv_main);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param view The View that was clicked
         */
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();

            mClickHandler.onClick(movies.get(adapterPosition));
        }

    }

}
