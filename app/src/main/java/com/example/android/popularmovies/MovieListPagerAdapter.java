package com.example.android.popularmovies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.popularmovies.movies.favorites.FavoriteMoviesFragment;
import com.example.android.popularmovies.movies.fromapi.MovieListFragment;

public class MovieListPagerAdapter extends FragmentStatePagerAdapter {

    public MovieListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MovieListFragment popularMoviesFragment = MovieListFragment.newInstance("popular");
                return popularMoviesFragment;
            case 1:
                FavoriteMoviesFragment favoriteMoviesFragment = new FavoriteMoviesFragment();
                return favoriteMoviesFragment;
            case 2:
                MovieListFragment highestRatedFragment = MovieListFragment.newInstance("top_rated");
                return highestRatedFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Most Popular";
            case 1:
                return "Favorites";
            case 2:
                return "Highest Rated";
            default:
                return null;
        }

    }
}
