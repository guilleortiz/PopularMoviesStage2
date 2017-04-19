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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortorder) {

        final SQLiteDatabase db = mTaskDbHelper.getReadableDatabase();

        int match=sUriMatcher.match(uri);








        Cursor retcursor;

        switch (match){
            case FAVORITES:
                retcursor=db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortorder);
                break;
            default:
                throw  new UnsupportedOperationException("Unknow ur "+uri);
        }
        retcursor.setNotificationUri(getContext().getContentResolver(),uri);



        return retcursor;
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
        Uri returnUri=null;

        switch (match){

            case FAVORITES:
                long id=db.insert(TABLE_NAME,null,values);
                if (id>0){
                    returnUri= ContentUris.withAppendedId(TaskContract.TasKEntry.CONTENT_URI,id);
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


        final SQLiteDatabase db=mTaskDbHelper.getWritableDatabase();

        // TODO (2) Write the code to delete a single row of data
        // [Hint] Use selections to delete an item by its row ID

        int match =sUriMatcher.match(uri);
        int taskDeleted;

        switch (match){

            case FAVORITES_WITH_ID:
                //URI: content://<authoruty>/task/#(any nuber as id)
                String id = uri.getPathSegments().get(1);//grabs the id, get(0)woud get the path

                String mSelection=" id =?";//the string of th id colom of the table task follow by an = and?
                String[] mSelectionArgs=new String[]{id};//array that holds the value of the row id

                taskDeleted= db.delete(TABLE_NAME,mSelection,mSelectionArgs);


                break;
            default:
                throw  new UnsupportedOperationException("Unknown uri: "+uri);
        }


        // TODO (3) Notify the resolver of a change and return the number of items deleted

        if (taskDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return taskDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
