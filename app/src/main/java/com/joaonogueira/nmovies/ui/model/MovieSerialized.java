package com.joaonogueira.nmovies.ui.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Joao on 11/04/2016.
 */
public class MovieSerialized{
    @SerializedName("results")
    ArrayList<Movie> movieList;

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }
}
