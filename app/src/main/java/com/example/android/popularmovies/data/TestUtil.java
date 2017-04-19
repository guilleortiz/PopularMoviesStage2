package com.example.android.popularmovies.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guille on 14/04/2017.
 */

public class TestUtil
{

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }

        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, "peli 1");
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_PLOT, "una peli");
        list.add(cv);


        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, "peli 2");
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_PLOT, "una peli  ");
        list.add(cv);


        try
        {
            db.beginTransaction();

            db.delete (MovieContract.MovieEntry.TABLE_NAME,null,null);

            for(ContentValues c:list){
                db.insert(MovieContract.MovieEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {

        }
        finally
        {
            db.endTransaction();
        }

    }


}
