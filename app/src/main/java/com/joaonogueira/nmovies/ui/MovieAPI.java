package com.joaonogueira.nmovies.ui;

import com.joaonogueira.nmovies.ui.model.MovieSerialized;
import com.joaonogueira.nmovies.ui.model.ReviewList;
import com.joaonogueira.nmovies.ui.model.TrailerList;
import com.joaonogueira.nmovies.ui.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Joao on 11/04/2016.
 */
    public interface MovieAPI{

        // To get all the movies and store them as MovieModel objects
        @GET("movie/{sort}?api_key=" + Constants.MOVIE_DB_API_KEY)
        Call<MovieSerialized> getMovies(
                @Path("sort") String sortCriteria
        );

        // To get the trailers and store them as MovieTrailer objects
        @GET("movie/{id}/videos?api_key=" + Constants.MOVIE_DB_API_KEY)
        Call<TrailerList> getTrailers(
                @Path("id") long movieID
        );

        // To get the trailers and store them as MovieTrailer objects
        @GET("movie/{id}/reviews?api_key=" + Constants.MOVIE_DB_API_KEY)
        Call<ReviewList> getReviews(
                @Path("id") long movieID
        );

    }
