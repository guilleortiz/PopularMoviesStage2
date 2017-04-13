package com.example.android.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Guille on 11/02/2017.
 */

public final class OpenMovieJsonUtils {


    public static ArrayList<Movie> getSimpleMovieStringsFromJson(String movieJsonStr) {


        ArrayList<Movie> movieArrayList=new ArrayList<>();


        try {

            //String[] parsedMovieData = null;

            JSONObject movieJson = null;

            movieJson = new JSONObject(movieJsonStr);



            JSONArray results = movieJson.getJSONArray("results");

            //parsedMovieData = new String[results.length()];



            for (int i = 0; i < results.length(); i++) {


                JSONObject oneMovieData=results.getJSONObject(i);

                String id=oneMovieData.getString("id");
                String originalTitle=oneMovieData.getString("original_title");
                String poster_path= "http://image.tmdb.org/t/p/w780"+oneMovieData.getString("poster_path");
                String overview = oneMovieData.getString("overview");
                String userRating = String.valueOf(oneMovieData.getDouble("vote_average"));
                String releaseDate = oneMovieData.getString("release_date");
                //String trailer=oneMovieData

                movieArrayList.add(new Movie(id,originalTitle,poster_path,overview,userRating,releaseDate));


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieArrayList;
    }


    /*https://api.themoviedb.org/3/movie/852/reviews?api_key=7436e9325f7283bdded6ec8c4db0a4a8&language=en-US%22*/

    public static String getReviewsStringFromJson(String movieJsonStr) {


        // ArrayList<Movie> movieArrayList=new ArrayList<>();
        String [] Reviews=null;
        String review="";


        try {

            //String[] parsedMovieData = null;

            JSONObject movieJson = null;

            movieJson = new JSONObject(movieJsonStr);



            JSONArray results = movieJson.getJSONArray("results");



            //parsedMovieData = new String[results.length()];



            for (int i = 0; i < results.length(); i++) {


                JSONObject oneMovieTrailer=results.getJSONObject(i);

                review=oneMovieTrailer.getString("content");

                // Toast.makeText(MovieDetail.class, key, Toast.LENGTH_SHORT).show();






            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return review;
    }


    public static String getTrailerStringFromJson(String movieJsonStr) {


       // ArrayList<Movie> movieArrayList=new ArrayList<>();
        String [] trailers=null;
        String key="";


        try {

            //String[] parsedMovieData = null;

            JSONObject movieJson = null;

            movieJson = new JSONObject(movieJsonStr);



            JSONArray results = movieJson.getJSONArray("results");

            //parsedMovieData = new String[results.length()];



            for (int i = 0; i < results.length(); i++) {


                JSONObject oneMovieTrailer=results.getJSONObject(i);

                key=oneMovieTrailer.getString("key");

               // trailers[i]=oneMovieTrailer.getString("key");







            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "https://www.youtube.com/watch?v="+key;
    }


}