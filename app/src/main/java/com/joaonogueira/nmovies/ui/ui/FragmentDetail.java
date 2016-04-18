package com.joaonogueira.nmovies.ui.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.joaonogueira.nmovies.R;
import com.joaonogueira.nmovies.ui.model.Movie;
import com.joaonogueira.nmovies.ui.tasks.CheckFavoriteTask;
import com.joaonogueira.nmovies.ui.tasks.SetFavoriteTask;
import com.joaonogueira.nmovies.ui.utils.Constants;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.text.ParseException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joao on 28/03/2016.
 */
public class FragmentDetail extends Fragment {

    @Bind(R.id.textOverview)TextView textOverview;
    @Bind(R.id.posterImageView)ImageView posterImageView;
    @Bind(R.id.releaseDate)TextView releaseDate;
    @Bind(R.id.rating)RatingBar ratingBar;
    @Bind(R.id.ratingTextView)TextView ratingTV;

    public Movie mMovie;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static FragmentDetail newInstance(int sectionNumber) {
        FragmentDetail fragment = new FragmentDetail();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);
        //Binding views
        ButterKnife.bind(this, rootView);

        if(getActivity().getIntent()!=null) {
            mMovie = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        }

        //Release date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        SimpleDateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd");
        String releasedDate;
        try {
            releasedDate = dateFormat.format(dateFormatInput.parse(mMovie.getmReleaseDate()));
        } catch (ParseException e){
            e.printStackTrace();
            releasedDate = mMovie.getmReleaseDate();
        }
        TextView mTextReleaseDate = (TextView) rootView.findViewById(R.id.releaseDate);
        mTextReleaseDate.setText(releasedDate);

        bindData(mMovie);

        return rootView;
    }

    //Binds data to views
    private void bindData(Movie movie){
        textOverview.setText(movie.getmOverview());
        Picasso.with(getContext()).load(movie.getmPosterPath()).into(posterImageView);
        //releaseDate.setText(movie.getmReleaseDate());
        ratingBar.setRating(movie.getmVoteAverage() / 2f);
        ratingTV.setText((float) Math.round(movie.getmVoteAverage()*10d)/10d + "/10");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mMovie = getArguments().getParcelable("mMovie");
    }

}
