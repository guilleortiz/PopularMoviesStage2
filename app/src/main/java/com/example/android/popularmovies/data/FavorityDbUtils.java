package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.Movie;

import java.util.ArrayList;

/**
 * Created by Guille on 15/04/2017.
 */

public class FavorityDbUtils extends AppCompatActivity {

    public static ArrayList<Movie> FavoriteQueries(Context context) {
        ArrayList<Movie> favoriteMovies=new ArrayList<Movie>();


        //MovieDbHelper dbHelper = new MovieDbHelper(this);
       // SQLiteDatabase mDb = dbHelper.getWritableDatabase();


        Cursor c = getAllMovies(context);
        c.moveToFirst();

        while (!c.isAfterLast()){

            Movie OneFavMovie=new Movie(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5));

            favoriteMovies.add(OneFavMovie);

            c.moveToNext();
        }
        return favoriteMovies;
    }








    public static Cursor getAllMovies(Context context) {
        //  Inside, call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP

        MovieDbHelper dbHelper= new MovieDbHelper(context);
        SQLiteDatabase mDb=dbHelper.getWritableDatabase();

        return mDb.query(
                MovieContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID
        );
    }



}
