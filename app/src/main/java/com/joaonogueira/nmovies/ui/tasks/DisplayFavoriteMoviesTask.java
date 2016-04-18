package com.joaonogueira.nmovies.ui.tasks;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.joaonogueira.nmovies.ui.adapter.ImageAdapter;
import com.joaonogueira.nmovies.ui.data.MovieContract;
import com.joaonogueira.nmovies.ui.model.Movie;
import com.joaonogueira.nmovies.ui.ui.FragmentMain;

import java.util.ArrayList;

/**
 * Created by Joao on 14/04/2016.
 */
public class DisplayFavoriteMoviesTask extends AsyncTask<Void ,Void,ArrayList<Movie>> {

    private Context mContext;
    private Activity mActivity;

    private  ArrayList<Movie> mMovieList = new ArrayList<Movie>();
    private ImageAdapter imageAdapter;
    private  String[] Movie_COLUMNS;


    public DisplayFavoriteMoviesTask(Context context, Activity activity, String[] Movie_COLUMNS, ImageAdapter imageAdapter ) {
        this.mContext = context;
        this.mActivity = activity;
        this.Movie_COLUMNS = Movie_COLUMNS;

        this.imageAdapter = imageAdapter;
    }

    public ArrayList<Movie> getMovie() {
        return mMovieList;
    }


    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {
        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, Movie_COLUMNS, null,null,null);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Movie movie = new Movie(cursor);
                //Movie movie = new Movie(cursor);
                mMovieList.add(movie);
            }
        }
        cursor.close();

        return mMovieList;

    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);

        if(movies != null){
            //imageAdapter.clearItems();

            Movie movieHandle;

            for(int i = 0; i < movies.size(); i++){
                movieHandle = movies.get(i);
                imageAdapter.addItem(movieHandle.getmPosterPath());
            }
            imageAdapter.notifyDataSetChanged();
        }
    }
}
