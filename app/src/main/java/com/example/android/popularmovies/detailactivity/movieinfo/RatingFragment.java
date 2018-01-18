package com.example.android.popularmovies.detailactivity.movieinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;


public class RatingFragment extends Fragment {

    // The Rating for this movie.
    private String rating;

    private TextView ratingTextView;

    public RatingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rating, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ratingTextView = (TextView) getView().findViewById(R.id.tv_detail_rating);

        ratingTextView.setText("( " + rating);
    }

    /**
     * Sets the Rating for this movie.
     *
     * @param voteAverage
     */
    public void setMovieRating(String voteAverage) {
        this.rating = voteAverage;
    }

}
