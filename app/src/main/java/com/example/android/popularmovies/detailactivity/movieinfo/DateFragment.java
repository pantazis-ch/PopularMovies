package com.example.android.popularmovies.detailactivity.movieinfo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.utilities.DateUtility;

public class DateFragment extends Fragment {

    // The Release Date of the movie.
    private String releaseDate;

    private TextView releaseDateTextView;

    /**
     * Preference variables for the Date Format that will be displayed.
     */
    private SharedPreferences sharedPref;
    private String dateFormatPref = null;
    private SharedPreferences.OnSharedPreferenceChangeListener dateFormatListener;

    public DateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupDateFormatPreferenceListener();

        releaseDateTextView = (TextView) getView().findViewById(R.id.tv_detail_release_date);

        releaseDateTextView.setText(DateUtility.formatDate(releaseDate, dateFormatPref));
    }

    /**
     * Set the Release Date for this movie.
     */
    public void setDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * This method is used to setup a listener for the Date Format Preference.
     */
    private void setupDateFormatPreferenceListener() {

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        dateFormatPref = sharedPref.getString("date_format","dd-mm-yyyy");

        // A Listener for knowing when the user changes the preference.
        // When the Date Format changes the corresponding TextView is updated.
        dateFormatListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                if (key.equals("date_format")) {

                    dateFormatPref = prefs.getString(key,"dd-mm-yyyy");

                    releaseDateTextView.setText(DateUtility.formatDate(releaseDate, dateFormatPref));

                }

            }
        };

        sharedPref.registerOnSharedPreferenceChangeListener(dateFormatListener);
    }

}
