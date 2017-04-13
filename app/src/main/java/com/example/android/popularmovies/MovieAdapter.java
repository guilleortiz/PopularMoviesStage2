package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Guille on 11/02/2017.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private ArrayList<Movie> mMovieData = new ArrayList<>();


    private final MovieAdapterOnClickHandler mClickHandler;


    public interface MovieAdapterOnClickHandler {
        void onClick(Movie detailMovieInfo);



    }



    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {

        mClickHandler=clickHandler;


    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public  final ImageView mMovieImage;



        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieImage=(ImageView)view.findViewById(R.id.foto);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition= getAdapterPosition();

            mClickHandler.onClick(mMovieData.get(adapterPosition));

        }
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieAdapterViewHolder MovieAdapterViewHolder, int position) {
        //String MovieForThisDay = mMovieData[position];
        //forecastAdapterViewHolder.mMovieTextView.setText(MovieForThisDay);

        Movie movieObject=mMovieData.get(position);

       // MovieAdapterViewHolder.mMovieTextView.setText(movieObject.mMovieTitle);
        String imageLink=movieObject.getPosterPath();

        Context context= MovieAdapterViewHolder.mMovieImage.getContext();


        Picasso.with(context).load(imageLink)
                .into(MovieAdapterViewHolder.mMovieImage);

    }


    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.size();
    }


    public void setMovieData(ArrayList<Movie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}