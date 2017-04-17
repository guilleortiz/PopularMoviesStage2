package com.example.android.popularmovies;

/**
 * Created by Guille on 11/02/2017.
 */


    public class Movie {
        int mId;
        String mMovieTitle;
        String mMoviePosterLink;
        String mMovieOverview;
        String mUserRating;
        String mReleaseDate;
       // String mTrailer;

        public Movie(int id,String movieTitle, String moviePosterLink, String movieOverview, String userRating, String releaseDate) {

            mId=id;
            mMovieTitle = movieTitle;
            mMoviePosterLink = moviePosterLink;
            mMovieOverview = movieOverview;
            mUserRating = userRating;
            mReleaseDate = releaseDate;
            //mTrailer=trailer;
        }

        public String getPosterPath() {

            return mMoviePosterLink;
        }
        public int getMovieId(){

            return mId;
        }
    }

