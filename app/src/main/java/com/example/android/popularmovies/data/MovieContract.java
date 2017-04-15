package com.example.android.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by Guille on 14/04/2017.
 */

public class MovieContract {

    public static final class MovieEntry implements BaseColumns{

        public static final String TABLE_NAME="favoriteMovies";
        public static final String COLUMN_MOVIE_ID="id";

        public static final String COLUMN_MOVIE_NAME="Name";

        public static final String COLUMN_MOVIE_FAVORITE="favorite";

        public static final String COLUMN_MOVIE_POSTER="poster";

        public static final String COLUMN__MOVIE_RELEASE_DATE="releaseDate";

        public static final String COLUMN_MOVIE_RATING="rating";

        public static final String COLUMN_MOVIE_PLOT="plot";

        public static final String COLUMN_MOVIE_TRAILER="trailer";

        public static final String COLUMN_MOVIE_REVIEWS="reviews";


    }

}
