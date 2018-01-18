package com.example.android.popularmovies.detailactivity.movieinfo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;

public class OverviewFragment extends Fragment {

    // The Overview of the Movie.
    private String overview;

    private TextView overviewTextView;

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        overviewTextView = (TextView) getView().findViewById(R.id.tv_detail_overview);

        overviewTextView.setText(overview);

    }

    /**
     * Sets the Overview for this movie.
     *
     * @param overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }
}
