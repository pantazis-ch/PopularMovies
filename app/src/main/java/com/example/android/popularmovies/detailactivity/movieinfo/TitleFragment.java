package com.example.android.popularmovies.detailactivity.movieinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;


public class TitleFragment extends Fragment {

    // The Title of this movie.
    private String title;

    // The Original Title for this movie.
    private String originalTitle;

    private TextView titleTextView;

    public TitleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_title, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        titleTextView = (TextView) getView().findViewById(R.id.tv_detail_title);

        titleTextView.setText(title);

        // If the Title and the Original Title are different...
        if(!title.equals(originalTitle)) {
            addOriginalTitleTextView();
        }

    }

    /**
     * Sets the Title and the Original Title of this movie.
     *
     * @param title
     * @param originalTitle
     */
    public void setMovieTitles(String title, String originalTitle) {
        this.title = title;
        this.originalTitle = originalTitle;
    }

    /**
     * This method is used to set the text and make visible the Original title TextView.
     */
    private void addOriginalTitleTextView() {

        TextView originalTitleTextView = (TextView) getView().findViewById(R.id.tv_detail_original_title);

        originalTitleTextView.setText(originalTitle);

        originalTitleTextView.setVisibility(View.VISIBLE);

    }

}
