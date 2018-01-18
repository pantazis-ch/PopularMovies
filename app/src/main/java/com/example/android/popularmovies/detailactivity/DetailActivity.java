package com.example.android.popularmovies.detailactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.android.popularmovies.movies.Movie;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.detailactivity.movieinfo.ImageFragment;
import com.example.android.popularmovies.detailactivity.movieinfo.DateFragment;
import com.example.android.popularmovies.detailactivity.movieinfo.OverviewFragment;
import com.example.android.popularmovies.detailactivity.movieinfo.RatingFragment;
import com.example.android.popularmovies.detailactivity.movieinfo.TitleFragment;
import com.example.android.popularmovies.detailactivity.reviews.ReviewFragment;
import com.example.android.popularmovies.detailactivity.trailers.TrailerFragment;
import com.example.android.popularmovies.settings.SettingsActivity;


public class DetailActivity extends AppCompatActivity {

    // The movie object for the specific Activity.
    private Movie movie;

    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        scrollView = (ScrollView) findViewById(R.id.activity_detail_scrollview);

        // We use the two following commands because we don't want the children
        // of the scrollview to get focus when they are created.
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        // Getting a Movie Object from the Main Activity...
        movie = getIntent().getParcelableExtra("movie");

        // Setting up the information fragments ( Movie Title, rating etc. )
        setupInformationFragments();

        // Setting up the trailer fragment...
        setupTrailerFragment();

        // Setting up the review fragments...
        setupReviewFragment();

    }


    /**
     * In this method we pass information to the different information fragments.
     */
    private void setupInformationFragments() {

        /**
         * The ImageFragment is responsible for presenting the Movie Poster to the user.
         * The movie poster can be taken either from the network, when we fetch data from the api,
         * or from the internal storage when the movie belongs to favorites.
         */
        ImageFragment imageFragment = (ImageFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_poster);

        imageFragment.setMoviePoster(movie.getMoviePoster());


        /**
         * The TitleFragment is responsible for presenting the title to the user.
         */
        TitleFragment titleFragment = (TitleFragment)
                getSupportFragmentManager().findFragmentById(R.id.title);

        titleFragment.setMovieTitles(movie.getTitle(),movie.getOriginalTitle());


        /**
         * The RatingFragment is responsible for presenting the rating to the user.
         */
        RatingFragment ratingFragment = (RatingFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_rating);

        ratingFragment.setMovieRating(movie.getVoteAverage());

        /**
         * The DateFragment is responsible for presenting the Release Date to the user.
         */
        DateFragment dateFragment = (DateFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_date);

        dateFragment.setDate(movie.getReleaseDate());

        /**
         * The OverviewFragment is responsible for presenting the Overview to the user.
         */
        OverviewFragment overviewFragment = (OverviewFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_overview);

        overviewFragment.setOverview(movie.getDescription());

    }

    private void setupTrailerFragment() {

        /**
         * The TrailerFragment is responsible for presenting the Trailers to the user.
         */
        TrailerFragment trailerFragment = (TrailerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_trailers);

        trailerFragment.setMovieId(movie.getMovieId());

    }

    private void setupReviewFragment() {

        /**
         * The ReviewFragment is responsible for presenting the Reviews to the user.
         */
        ReviewFragment reviewFragment = (ReviewFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_reviews);

        reviewFragment.setMovieId(movie.getMovieId());

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
