package com.example.android.popularmovies.movies;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {

    private String title;
    private String originalTitle;
    private String movie_id;
    private String moviePoster;
    private String description;
    private String voteAverage;
    private String releaseDate;

    public Movie(String title, String originalTitle,String movie_id, String moviePoster, String description, String voteAverage, String releaseDate ) {
        this.title = title;
        this.originalTitle = originalTitle;
        this.movie_id = movie_id;
        this.moviePoster = moviePoster;
        this.description = description;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(originalTitle);
        parcel.writeString(movie_id);
        parcel.writeString(moviePoster);
        parcel.writeString(description);
        parcel.writeString(voteAverage);
        parcel.writeString(releaseDate);
    }

    protected Movie(Parcel in) {
        title = in.readString();
        originalTitle = in.readString();
        movie_id = in.readString();
        moviePoster = in.readString();
        description = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle(){
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getMovieId() {
        return movie_id;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getDescription() {
        return description;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
