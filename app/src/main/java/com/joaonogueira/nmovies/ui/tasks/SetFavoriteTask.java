package com.joaonogueira.nmovies.ui.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.joaonogueira.nmovies.R;
import com.joaonogueira.nmovies.ui.data.MovieContract;
import com.joaonogueira.nmovies.ui.model.Movie;

/**
 * Created by Joao on 14/04/2016.
 */
public class SetFavoriteTask extends AsyncTask<Movie, Integer, Integer> {

    private Context mContext;
    private View rootView;
    private Movie mMovie;
    private int numRows;
    private int favCheck;

    public SetFavoriteTask(Context context, View view){
        mContext = context;
        this.rootView = view;
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
            cursor.close();
        }

        if (numRows == 1) {    // Inside db so delete

            int delete = mContext.getContentResolver().delete(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{Long.toString(mMovie.getmMovieId())}
            );
            favCheck = 0;
        }else {

            ContentValues values = new ContentValues();

            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovie.getmMovieId());
            values.put(MovieContract.MovieEntry.COLUMN_TITLE, mMovie.getmTitle());
            values.put(MovieContract.MovieEntry.COLUMN_POSTER, mMovie.getmPosterPath());
            values.put(MovieContract.MovieEntry.COLUMN_BACKDROP, mMovie.getmBackdropPath());
            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, mMovie.getmOverview());
            values.put(MovieContract.MovieEntry.COLUMN_RATING, mMovie.getmVoteAverage());
            values.put(MovieContract.MovieEntry.COLUMN_DATE, mMovie.getmReleaseDate());
            values.put(MovieContract.MovieEntry.COLUMN_VOTES, mMovie.getmVoteCount());
            values.put(MovieContract.MovieEntry.COLUMN_POPULARITY, mMovie.getmPopularity());

            mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);

            favCheck = 1;
        }

        return favCheck;
    }

    @Override
    protected void onPostExecute(Integer favCheck) {
        super.onPostExecute(favCheck);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        if (favCheck == 1) {
            Snackbar.make(rootView, "Movie set to favorite", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            fab.setImageDrawable(ContextCompat.getDrawable(mContext, android.R.drawable.btn_star_big_on));
        } else if (favCheck == 0) {
            Snackbar.make(rootView, "Removed favorite movie", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            fab.setImageDrawable(ContextCompat.getDrawable(mContext, android.R.drawable.btn_star_big_off));
        }

    }
}
