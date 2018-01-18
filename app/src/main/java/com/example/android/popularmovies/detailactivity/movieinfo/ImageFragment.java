package com.example.android.popularmovies.detailactivity.movieinfo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.movies.Movie;
import com.example.android.popularmovies.utilities.FileUtility;
import com.example.android.popularmovies.utilities.NetworkUtilities;
import com.squareup.picasso.Picasso;

public class ImageFragment extends Fragment {

    // The Poster Path for the movie.
    private String posterPath;

    private ImageView posterImageView;

    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        posterImageView = (ImageView) getView().findViewById(R.id.img_detail_poster);

        // If the movie belongs to favorites we prefer to get the bitmap from memory.
        boolean getBitmapFromMemory = getActivity().getIntent().getBooleanExtra("getBitmapFromMemory", false);

        if(!getBitmapFromMemory) {
            // Building a Poster Url.
            String posterUrl = String.valueOf(NetworkUtilities.buildImageUrl(posterPath));

            // Loading the Poster of the specific Url.
            // Because we use Picasso the Poster will be loaded from cache
            // and not from the network.
            Picasso.with(getContext())
                    .load(String.valueOf(posterUrl))
                    .into(posterImageView);

        } else {
            Movie movie = getActivity().getIntent().getParcelableExtra("movie");
            String movie_id = movie.getMovieId();

            Bitmap b = FileUtility.getImageFromInternalStorage(movie_id,getContext());
            if (b!=null) {
                posterImageView.setImageBitmap(b);
            }
        }




    }

    /**
     * Sets the Poster Path for this movie.
     *
     * @param moviePoster
     */
    public void setMoviePoster(String moviePoster) {
        this.posterPath = moviePoster;
    }

}
