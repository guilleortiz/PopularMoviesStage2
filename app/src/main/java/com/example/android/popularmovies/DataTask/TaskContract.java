package com.example.android.popularmovies.DataTask;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Guille on 17/04/2017.
 */

public class TaskContract {

    public static  final String AUTHORITY="com.example.android.popularmovies";

    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+ AUTHORITY);

    public static final String PATH_FAVORITES="favorites";


    public static final class TasKEntry implements BaseColumns{

        public static  final  Uri CONTENT_URI=
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

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
