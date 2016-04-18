package com.joaonogueira.nmovies.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joaonogueira.nmovies.R;
import com.joaonogueira.nmovies.ui.MovieAPI;
import com.joaonogueira.nmovies.ui.model.Movie;
import com.joaonogueira.nmovies.ui.model.TrailerList;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joao on 12/04/2016.
 */
public class FragmentTrailer extends Fragment {

    @Bind(R.id.trailerPoster)
    ImageView mTrailerPoster;
    @Bind(R.id.trailerTitle)
    TextView mTrailerTitle;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int movieId;

    public Movie mMovie;
    //Variables for retrofit
    private MovieAPI movieAPI;
    private static final String API_URL = "http://api.themoviedb.org/3/";
    private Call<TrailerList> callTrailer;
    private TrailerList trailerList;
    private List<TrailerList.MovieTrailer> trailerElements;
    public static TrailerList.MovieTrailer trailers;

    public static FragmentDetail newInstance(int sectionNumber) {
        FragmentDetail fragment = new FragmentDetail();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trailer, container, false);
            //Binding views
            ButterKnife.bind(this, rootView);
            if (getActivity().getIntent() != null) {
                mMovie = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
            }
            getTrailer();

        return rootView;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mMovie = getArguments().getParcelable("mMovie");
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieAPI = retrofit.create(MovieAPI.class);

    }
    public void getTrailer(){

        callTrailer = movieAPI.getTrailers(mMovie.getmMovieId());

        //Call asynchronously
        callTrailer.enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                trailerList = response.body();
                trailerElements = trailerList.getTrailerList();

                if(trailerElements.size() != 0){
                    trailers = trailerElements.get(0);
                    bindData(trailers);
                }else{
                    trailers = null;
                }

            }
            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {

            }
        });
    }

    //Binds data to views
    private void bindData(TrailerList.MovieTrailer trailers){
        Picasso.with(getContext()).load(TrailerList.MovieTrailer.getImageUrl(trailers)).into(mTrailerPoster);
        mTrailerTitle.setText(mMovie.getmTitle()+" Trailer");
    }

}
