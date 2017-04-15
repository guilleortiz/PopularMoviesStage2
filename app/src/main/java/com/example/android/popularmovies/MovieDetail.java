package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieDbHelper;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Guille on 11/02/2017.
 */

public class MovieDetail extends AppCompatActivity {

    private  String MovieId;

    private TextView mTitulo;
    private String titulo;

    private TextView mPlot;
    private String plot;

    private TextView mNota;
    private String nota;

    private TextView mfecha;
    private String fecha;

    private ImageView mPoster;
    private String poster;

    private TextView mReviewUser,mReview;

    private CheckBox favButton;
    private ImageView deleteButton;

   // private TextView mTrailer;
   // private String trailer;
   private Boolean linkReady=false;
    private Boolean click=false;

    private String YouLink="";

    private SQLiteDatabase mDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTitulo=(TextView)findViewById(R.id.tv_original_title);
        mPlot=(TextView)findViewById(R.id.tv_plot);
        mNota=(TextView)findViewById(R.id.tv_rating);

        mfecha=(TextView)findViewById(R.id.release_date);

        mPoster=(ImageView) findViewById(R.id.iv_cartel);

        mReview=(TextView)findViewById(R.id.tv_Review);
        mReviewUser=(TextView)findViewById(R.id.tv_reviewAuthor);

        favButton=(CheckBox)findViewById(R.id.favoriteStar);
        deleteButton=(ImageView)findViewById(R.id.delete);

       // mTrailer=(TextView)findViewById(R.id.tv_trailer);

        Intent intentThatStartedThisActivity=getIntent();

        if (intentThatStartedThisActivity!=null){
            if (intentThatStartedThisActivity.hasExtra("titulo")){

                titulo=intentThatStartedThisActivity.getStringExtra("titulo");
                mTitulo.setText(titulo);

                plot=intentThatStartedThisActivity.getStringExtra("plot");
                mPlot.setText(plot);


                nota=intentThatStartedThisActivity.getStringExtra("nota");

                mNota.setText("Rating "+nota);

                fecha=intentThatStartedThisActivity.getStringExtra("fecha");
                mfecha.setText("Release date "+fecha);

                poster=intentThatStartedThisActivity.getStringExtra("img");

                Picasso.with(this).load(poster)
                        .into(mPoster);

                MovieId=intentThatStartedThisActivity.getStringExtra("id");

               // addMovieToFavorites(22,"mititulo","12/87/84","8","historiaa de ","link","mu buena");





            }
        }

        new FetchTrailerTask().execute(MovieId);
        new FetchReviewsTask().execute(MovieId);
      //  Toast.makeText(this, MovieId, Toast.LENGTH_SHORT).show();

        LinearLayout app_layer = (LinearLayout) findViewById (R.id.Ll_trailer);
        app_layer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                 click=true;


                if (linkReady&& click){
                    startActivity(new Intent(
                            Intent.ACTION_VIEW, Uri.parse(YouLink)));
                   // Toast.makeText(MovieDetail.this, MovieId, Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MovieDetail.this, "Check internet conection", Toast.LENGTH_SHORT).show();
                }



            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                removeFavoriteMovie(Integer.parseInt(MovieId));

            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  status=String.valueOf(favButton.isChecked());
                Toast.makeText(MovieDetail.this, status, Toast.LENGTH_SHORT).show();




               // mDb.execSQL("delete  from "+ MovieContract.MovieEntry.TABLE_NAME);
               addMovieToFavorites(Integer.parseInt(MovieId),titulo,fecha,nota,plot,YouLink,mReview.getText().toString());
              //  addMovieToFavorites(22,"mititulo","12/87/84","8","historiaa de ","link","mu buena");




            }
        });
        /*

        if (IsFavorite(Integer.parseInt(MovieId))){

            favButton.setChecked(true);

        }else {
            favButton.setChecked(false);
        }*/



        MovieDbHelper dbHelper= new MovieDbHelper(this);
        mDb=dbHelper.getWritableDatabase();

        //TestUtil.insertFakeData(mDb);

        Cursor cursor;
        cursor=getAllMovies();

        cursor.moveToFirst();
       // int number=cursor.getColumnCount();
       //
        //for (int i=1;i<number;i++){
        while(cursor.moveToNext()){


            int id=cursor.getInt(0);
            String titulo=cursor.getString(1);//

            Toast.makeText(this, id+" "+titulo, Toast.LENGTH_SHORT).show();

        }






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
    public boolean IsFavorite(int favId){

        Cursor cursor;
        cursor=getAllMovies();

        cursor.moveToFirst();

        while(cursor.moveToNext()){


            int id=cursor.getInt(0);

            if (favId==id){

                Toast.makeText(this,"isFavorite", Toast.LENGTH_SHORT).show();
                return true;

            }



        }

        return false;

    }

    private void addMovieToFavorites(int id,String name,String date, String rating,String plot,String trailer,String review){

        ContentValues cv = new ContentValues();

        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,id);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME,name);
        cv.put(MovieContract.MovieEntry.COLUMN__MOVIE_RELEASE_DATE,date);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_RATING,rating);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_PLOT,plot);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_TRAILER,trailer);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_REVIEWS,review);

        try {
            mDb.insert(MovieContract.MovieEntry.TABLE_NAME, null, cv);
        }catch (Exception e){
            Toast.makeText(this, "Error "+e , Toast.LENGTH_LONG).show();
        }


    }

    private boolean removeFavoriteMovie(int id){

        return mDb.delete(MovieContract.MovieEntry.TABLE_NAME,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID +" = "+id,null) > 0;//retunr the boolean result



    }


    public class FetchReviewsTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... param) {

            String movieId="nada";

            if (param.length==0){
                return null;
            }else {  movieId=param[0];}

            //Toast.makeText(MovieDetail.this, kk, Toast.LENGTH_LONG).show();
            URL reviewUrl=null;

            try{
                reviewUrl= new URL("https://api.themoviedb.org/3/movie/"+movieId+
                        "/reviews?api_key=7436e9325f7283bdded6ec8c4db0a4a8&language=en-US");

            }catch (MalformedURLException e){
                e.printStackTrace();
            }

            try{
                String jsonReviewResponse= NetworkUtils.getResponseFromHttpUrl(reviewUrl);

                String movieReview="";

                movieReview=OpenMovieJsonUtils.getReviewsStringFromJson(jsonReviewResponse);


                return movieReview;

            }catch (Exception e){
                Log.w("myApp", "WHATTTTT");
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {

          //  mReviewUser.setText();
            mReview.setText(s);

          //  Toast.makeText(MovieDetail.this, s, Toast.LENGTH_LONG).show();

        }
    }


    public class FetchTrailerTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... param) {

           String movieId="nada";

            if (param.length==0){
                return null;
            }else {  movieId=param[0];}

            //Toast.makeText(MovieDetail.this, kk, Toast.LENGTH_LONG).show();
            URL trailerUrl=null;

            try{
                trailerUrl= new URL("https://api.themoviedb.org/3/movie/"+movieId+
                        "/videos?api_key=7436e9325f7283bdded6ec8c4db0a4a8&language=en-US");

            }catch (MalformedURLException e){
                e.printStackTrace();
            }

            try{
                String jsonTrailerResponse= NetworkUtils.getResponseFromHttpUrl(trailerUrl);

                String videoLink="";
                videoLink=OpenMovieJsonUtils.getTrailerStringFromJson(jsonTrailerResponse);
                    String kk=videoLink;
                //Toast.makeText(MovieDetail.this, videoLink, Toast.LENGTH_SHORT).show();

                return videoLink;

            }catch (Exception e){
                //Log.w("myApp", "WHATTTTT");
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
           // Toast.makeText(MovieDetail.this, s, Toast.LENGTH_SHORT).show();
            linkReady=true;
            YouLink=s;

            /*startActivity(new Intent(
                    Intent.ACTION_VIEW, Uri.parse(s)));*/

        }
    }
}
