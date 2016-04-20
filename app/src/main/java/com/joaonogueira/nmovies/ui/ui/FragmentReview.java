package com.joaonogueira.nmovies.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joaonogueira.nmovies.R;
import com.joaonogueira.nmovies.ui.MovieAPI;
import com.joaonogueira.nmovies.ui.adapter.ReviewAdapter;
import com.joaonogueira.nmovies.ui.model.Movie;
import com.joaonogueira.nmovies.ui.model.ReviewList;
import com.joaonogueira.nmovies.ui.utils.Constants;

import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joao on 12/04/2016.
 */
public class FragmentReview extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public Movie mMovie;
    private MovieAPI movieAPI;
    private static final String API_URL = "http://api.themoviedb.org/3/";
    private Call<ReviewList> callReview;
    private ReviewList reviewList;
    private List<ReviewList.MovieReview> reviewElements;
    private ReviewAdapter mReviewAdapter;

    public static FragmentReview newInstance(int sectionNumber) {
        FragmentReview fragment = new FragmentReview();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_review_list, container, false);
        ButterKnife.bind(this, recyclerView);
        //Review adapter
        mReviewAdapter = new ReviewAdapter(getContext());

        if(getActivity().getIntent()!=null) {
            mMovie = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        }
        if(getArguments()!=null){
            mMovie = getArguments().getParcelable(Constants.BUNDLE_CONSTANT);
        }
        setupRecyclerView(recyclerView);
        getReview();
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        //recyclerView.setAdapter(mReviewAdapter, reviewElements);
        recyclerView.setAdapter(mReviewAdapter);
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

    public void  getReview(){

        callReview = movieAPI.getReviews(mMovie.getmMovieId());

        //Call asynchronously
        callReview.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                reviewList = response.body();
                reviewElements = reviewList.getReviewsList();
                mReviewAdapter.setReviews(reviewElements);
                if(reviewElements !=null){

                }
            }
            @Override
            public void onFailure(Call<ReviewList> call, Throwable t) {

            }
        });
    }
}
