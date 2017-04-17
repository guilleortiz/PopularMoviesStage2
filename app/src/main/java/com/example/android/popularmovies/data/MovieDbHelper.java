package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Guille on 14/04/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="movies.db";

    private static final int DATABASE_VERSION=3;

    public MovieDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE="CREATE TABLE "+
                MovieContract.MovieEntry.TABLE_NAME+"("+
                MovieContract.MovieEntry.COLUMN_MOVIE_ID+ " INTEGER PRIMARY KEY ,"+
                MovieContract.MovieEntry.COLUMN_MOVIE_NAME+" TEXT NOT NULL,"+
                MovieContract.MovieEntry.COLUMN_MOVIE_POSTER+" TEXT,"+
                MovieContract.MovieEntry.COLUMN__MOVIE_RELEASE_DATE+" TEXT,"+
                MovieContract.MovieEntry.COLUMN_MOVIE_RATING+" TEXT,"+
                MovieContract.MovieEntry.COLUMN_MOVIE_PLOT+" TEXT,"+
                MovieContract.MovieEntry.COLUMN_MOVIE_REVIEWS+" TEXT,"+
                MovieContract.MovieEntry.COLUMN_MOVIE_TRAILER+" TEXT"+
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
