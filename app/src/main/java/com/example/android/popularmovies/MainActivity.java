package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieDbHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    //String url="";

    private RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    String defaultOrder="popular";
    String topRatedOrder="top_rated";

    private SQLiteDatabase mDb;

    /*
    *
    * pluging parcelable =Android Parcelable code generator
    *
    *
    *
    *
    * */





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_Movie);


        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        MovieDbHelper dbHelper= new MovieDbHelper(this);
        mDb=dbHelper.getWritableDatabase();

        int mNoOfColumns = calculateNoOfColumns(getApplicationContext());


       GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, mNoOfColumns);

        /*


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                 mRecyclerView.setLayoutManager(layoutManager);

                */

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRecyclerView.setHasFixedSize(true);


        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);


        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);


        loadMovieData(defaultOrder);


    }



    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }


    private void loadMovieData(String orderBy) {
        showMovieDataView();


        new FetchMovieTask().execute(orderBy);
    }


    private void showMovieDataView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(Movie detailMovieInfo) {
        Context context=this;
        Class destinationClass= MovieDetail.class;

        Intent intentToStartMovieDetail= new Intent(context,destinationClass);

        /*////////Paso mas cosas
        Bundle extras= new Bundle();
        extras.putString("titulo",detailMovieInfo.);
        extras.putString("plot",detailMovieInfo.);

        intentToStartMovieDetail.putExtra(Intent.EXTRA_TEXT,detailMovieInfo);
        startActivity(intentToStartMovieDetail);
        *////////////////////////////////////////////



        /////////////////////////Paso solo un string con el plot
        intentToStartMovieDetail.putExtra("titulo",detailMovieInfo.mMovieTitle);
        intentToStartMovieDetail.putExtra("plot",detailMovieInfo.mMovieOverview);
        intentToStartMovieDetail.putExtra("nota",detailMovieInfo.mUserRating);
        intentToStartMovieDetail.putExtra("fecha",detailMovieInfo.mReleaseDate);
        intentToStartMovieDetail.putExtra("img",detailMovieInfo.getPosterPath());
        intentToStartMovieDetail.putExtra("id",detailMovieInfo.getMovieId());



        //intentToStartMovieDetail.putExtra(Intent.EXTRA_TEXT,detailMovieInfo);
        startActivity(intentToStartMovieDetail);





    }

    public ArrayList<Movie> CursorToArrayList (Cursor c){


        ArrayList<Movie> favoriteMovies = new ArrayList<Movie>();
        int number=c.getCount();

        c.moveToFirst();
        while (!c.isAfterLast()){

            Movie OneFavMovie=new Movie(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5));

            favoriteMovies.add(OneFavMovie);

            c.moveToNext();
        }




        return favoriteMovies;

    }




    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {


            if (params.length == 0) {
                return null;
            }

            String query = params[0];

            String searchOrderBy=null;
            URL movieRequestUrl = null;

            if (query=="top_rated"){
                searchOrderBy="top_rated";

            }else if(query=="popular"){
                searchOrderBy="popular";


            }
            try {
                movieRequestUrl = new URL("http://api.themoviedb.org/3/movie/"+searchOrderBy+"?api_key=7436e9325f7283bdded6ec8c4db0a4a8");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                ArrayList<Movie> simpleJsonMovieData = OpenMovieJsonUtils
                        .getSimpleMovieStringsFromJson(jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMovieDataView();
                mMovieAdapter.setMovieData(movies);
            } else {
               // showErrorMessage();
                Toast.makeText(MainActivity.this, "Showing favorite movies", Toast.LENGTH_SHORT).show();
                mMovieAdapter.setMovieData(null);
                mMovieAdapter.setMovieData(FavoriteQueries());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_Popular) {
            mMovieAdapter.setMovieData(null);
            loadMovieData(defaultOrder);
            return true;
        }else if(id==R.id.action_top_rated){
            mMovieAdapter.setMovieData(null);
            loadMovieData(topRatedOrder);

        }else if(id==R.id.action_Favorites){

            mMovieAdapter.setMovieData(null);
            mMovieAdapter.setMovieData(FavoriteQueries());

        }else if (id==R.id.action_Delete){
            mDb.execSQL("delete  from "+ MovieContract.MovieEntry.TABLE_NAME);
            Toast.makeText(this, "all favorites deleted", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    public  ArrayList<Movie> FavoriteQueries() {
        ArrayList<Movie> favoriteMovies=new ArrayList<Movie>();


        //MovieDbHelper dbHelper = new MovieDbHelper(this);
        // SQLiteDatabase mDb = dbHelper.getWritableDatabase();


        Cursor c = getAllMovies();

        c.moveToFirst();
       // Toast.makeText(this, DatabaseUtils.dumpCursorToString(c), Toast.LENGTH_LONG).show();

        while (!c.isAfterLast()){
            //int id,String movieTitle, String moviePosterLink, String movieOverview, String userRating, String releaseDate) {


            Movie OneFavMovie=new Movie(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5));

            favoriteMovies.add(OneFavMovie);

            c.moveToNext();
        }
        return favoriteMovies;
    }

    private Cursor getAllMovies() {
        //  Inside, call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP
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