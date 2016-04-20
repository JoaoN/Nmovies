package com.joaonogueira.nmovies.ui.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import com.joaonogueira.nmovies.R;
import com.joaonogueira.nmovies.ui.MovieAPI;
import com.joaonogueira.nmovies.ui.adapter.ImageAdapter;
import com.joaonogueira.nmovies.ui.data.MovieContract;
import com.joaonogueira.nmovies.ui.model.Movie;
import com.joaonogueira.nmovies.ui.model.MovieSerialized;
import com.joaonogueira.nmovies.ui.tasks.DisplayFavoriteMoviesTask;
import com.joaonogueira.nmovies.ui.utils.Constants;
import com.joaonogueira.nmovies.ui.utils.Utility;


import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joao on 28/03/2016.
 */
public class FragmentPosters extends Fragment {
    public ArrayList<Movie> mMovieElements = new ArrayList<Movie>();
    GridView gridView;
    public static String orderParam ="popular";
    static FragmentPosters instance;
    public ImageAdapter imageAdapter;
    private ProgressDialog mProgressBar;
    private MovieClicked mListener;


    //Variable for Retrofit
    private Call<MovieSerialized> callMovie;
    private MovieSerialized movieList;
    private MovieAPI movieAPI;


    public static final String MOVIE_LIST = "MoviesList";

    private static final String[] MOVIE_COLUMNS = {
            // In this case the id needs to be fully qualified with activity_main table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            MovieContract.MovieEntry.TABLE_NAME + "." + MovieContract.MovieEntry._ID,
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                    MovieContract.MovieEntry.COLUMN_TITLE,
                    MovieContract.MovieEntry.COLUMN_POSTER,
                    MovieContract.MovieEntry.COLUMN_BACKDROP,
                    MovieContract.MovieEntry.COLUMN_OVERVIEW,
                    MovieContract.MovieEntry.COLUMN_RATING,
                    MovieContract.MovieEntry.COLUMN_DATE,
                    MovieContract.MovieEntry.COLUMN_VOTES,
                    MovieContract.MovieEntry.COLUMN_POPULARITY
    };

    // These indices are tied to MOVIE_COLUMNS.  If MOVIE changes, these
    // must change.
    public static final int COL_ID = 0;
    public static final int COLUMN_MOVIE_ID = 1;
    public static final int COLUMN_TITLE = 2;
    public static final int COLUMN_POSTER = 3;
    public static final int COLUMN_BACKDROP = 4;
    public static final int COLUMN_OVERVIEW = 5;
    public static final int COLUMN_RATING = 6;
    public static final int COLUMN_DATE = 7;
    public static final int COLUMN_VOTES = 8;
    public static final int COLUMN_POPULARITY = 9;



    public FragmentPosters(){
        instance = this;
    }

    //Called when movie clicked
    public interface MovieClicked{
        void onMovieClicked(Movie movie);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null && savedInstanceState.containsKey(MOVIE_LIST)){
            mMovieElements = savedInstanceState.getParcelableArrayList(MOVIE_LIST);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieAPI = retrofit.create(MovieAPI.class);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
        imageAdapter = new ImageAdapter(getContext());
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);
        //Start DetailActivity with the movie details.
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                mListener.onMovieClicked(mMovieElements.get(position));
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener = (MovieClicked)getActivity();
    }


    public void favoriteMovies(){
        DisplayFavoriteMoviesTask displayFavoriteMoviesTask = new DisplayFavoriteMoviesTask(getContext(),getActivity()
                , MOVIE_COLUMNS, imageAdapter);
        //imageAdapter.notifyDataSetChanged();
        displayFavoriteMoviesTask.execute();
        mProgressBar.dismiss();
        mMovieElements = displayFavoriteMoviesTask.getMovie();
    }


    //Start AsyncTask
    public void updateMovies(){
        if(Objects.equals(orderParam, "favorite")){
            mMovieElements.clear();
            imageAdapter.clearItems();
            showProgressBar();
            favoriteMovies();
        }else {
            if(Utility.checkNetwork(getContext())) {
                mMovieElements.clear();
                imageAdapter.clearItems();
                showProgressBar();
                getMovies(orderParam);
            }else {
                //Handling no network condition
                Toast.makeText(getContext(), "No network connection, only favorite movies available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public void getMovies(String sortOrder){

        callMovie = movieAPI.getMovies(sortOrder);

        //Call asynchronously
        callMovie.enqueue(new Callback<MovieSerialized>() {
            @Override
            public void onResponse(Call<MovieSerialized> call, Response<MovieSerialized> response) {
                movieList = response.body();
                mMovieElements = movieList.getMovieList();
                //Check the array
                if(mMovieElements != null){
                    imageAdapter.clearItems();
                    Movie movieHandle;

                    for(int i = 0; i < mMovieElements.size(); i++){
                        movieHandle = mMovieElements.get(i);
                        imageAdapter.addItem(movieHandle.getmPosterPath());
                    }
                    //Set Adapter
                    imageAdapter.notifyDataSetChanged();
                    mProgressBar.dismiss();
                }
            }
            @Override
            public void onFailure(Call<MovieSerialized> call, Throwable t) {
            }
        });
    }

    //Showing progress bar
    private void showProgressBar(){
        mProgressBar = new ProgressDialog(getContext());
        mProgressBar.setMessage("Loading images...");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();
    }
}
