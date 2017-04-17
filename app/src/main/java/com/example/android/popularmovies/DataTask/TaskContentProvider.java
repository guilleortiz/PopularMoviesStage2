package com.example.android.popularmovies.DataTask;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import static com.example.android.popularmovies.DataTask.TaskContract.TasKEntry.TABLE_NAME;

/**
 * Created by Guille on 17/04/2017.
 */

public class TaskContentProvider extends ContentProvider {


    public static final int FAVORITES=100;
    public static final int FAVORITES_WITH_ID=101;

    private static final UriMatcher sUriMatcher= buidUriMatcher();


    public static UriMatcher buidUriMatcher(){
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(TaskContract.AUTHORITY,TaskContract.PATH_FAVORITES,FAVORITES);
        uriMatcher.addURI(TaskContract.AUTHORITY,TaskContract.PATH_FAVORITES+"/#",FAVORITES_WITH_ID);

        return uriMatcher;

    }


    private TaskDbHelper mTaskDbHelper;

    @Override
    public boolean onCreate() {

        Context context=getContext();
        mTaskDbHelper=new TaskDbHelper(context);


        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db =mTaskDbHelper.getWritableDatabase();

        int match =sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){

            case FAVORITES:
                long id=db.insert(TABLE_NAME,null,values);
                if (id>0){
                    returnUri= ContentUris.withAppendedId(TaskContract.TasKEntry.CONTENT_URI,id);



                }else{
                throw  new android.database.SQLException("Failed to insert into "+uri);
                }



                break;
            default:
                throw new UnsupportedOperationException("Unknow uri "+uri);

        }

        getContext().getContentResolver().notifyChange(uri,null);


        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
