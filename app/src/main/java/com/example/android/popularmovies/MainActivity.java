package com.example.android.popularmovies;

/**
 * ( Android Scholarship )
 *
 * Created by Pantazis Chatzigiannis for the Associate Android Developer Fast Track Program.
 *
 * Project 2: Popular Movies, Stage 2.
 *
 * February / March 2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.popularmovies.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    // Reference to the TabLayout.
    private TabLayout tabLayout;

    // The ViewPager is used in combination with the TabLayout to allow
    // the user to swipe between List Categories ( 'Most Popular', 'Highest Rated' and 'Favorites' ).
    private ViewPager mViewPager;

    // The MovieListPagerAdapter is used to populate the ViewPager with Fragments.
    // The users is able to choose from the Settings menu which fragments will be included.
    private MovieListPagerAdapter mMovieListPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupApplicationPreferences();

        createMovieListPager();
    }

    /**
     * This method is used to setup the Preferences for this app
     * and get the Sort Criteria Preference.
     */
    private void setupApplicationPreferences() {

        // Set the default values for the preferences.
        // The preference values are set to default only the first time the app is executed.
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }

    public void createMovieListPager() {

        mMovieListPagerAdapter = new MovieListPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the MovieListPagerAdapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mMovieListPagerAdapter);

        // Setup the the TabLayout with the pager.
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
