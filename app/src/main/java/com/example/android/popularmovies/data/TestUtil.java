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
        //create a list of fake movies
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, "peli 1");
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_PLOT, "una peli mazzzzzzzzzzzzzzzzzzzzzzo bena con cosasss viste flcao");
        list.add(cv);


        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, "peli 2");
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_PLOT, "una peli mazzzzzzeeeeeeeeeeeeeeeeeeeeeeeesesese viste flcao");
        list.add(cv);

        //insert all movies in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (MovieContract.MovieEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(MovieContract.MovieEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }


}
