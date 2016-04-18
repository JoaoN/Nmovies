package com.joaonogueira.nmovies.ui.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.joaonogueira.nmovies.R;
import com.joaonogueira.nmovies.ui.data.MovieContract;
import com.joaonogueira.nmovies.ui.model.Movie;

/**
 * Created by Joao on 14/04/2016.
 */
public class CheckFavoriteTask extends AsyncTask<Movie, Integer, Integer> {

    private Context mContext;
    private View rootView;
    private Movie mMovie;
    private int numRows;
    private int favCheck;
    private FloatingActionButton fab;

    public CheckFavoriteTask(Context context, FloatingActionButton view){
        mContext = context;
        fab = view;
    }


    @Override
    protected Integer doInBackground(Movie... params) {

        mMovie = params[0];

        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,   //projection
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " =?",
                new String[]{String.valueOf(mMovie.getmMovieId())},      // selectionArgs : gets the rows with this movieID
                null             // Sort order

        );

        if (cursor != null) {
            numRows = cursor.getCount();
            if(numRows == 1){
                favCheck = 1;
            } else
                favCheck = 0;
            cursor.close();
        }

        return favCheck;
    }

    @Override
    protected void onPostExecute(Integer favCheck) {
        super.onPostExecute(favCheck);

        if (favCheck == 1) {

            fab.setImageDrawable(ContextCompat.getDrawable(mContext, android.R.drawable.btn_star_big_on));
        } else if (favCheck == 0) {

            fab.setImageDrawable(ContextCompat.getDrawable(mContext, android.R.drawable.btn_star_big_off));
        }
    }
}
