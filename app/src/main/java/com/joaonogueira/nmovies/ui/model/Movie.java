package com.joaonogueira.nmovies.ui.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.joaonogueira.nmovies.ui.ui.FragmentPosters;

/**
 * Created by Joao on 28/03/2016.
 */
public class Movie implements Parcelable {


    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("overview")
    private String mOverview;

    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("id")
    private int mMovieId;

    @SerializedName("original_title")
    private String mTitle;

    @SerializedName("backdrop_path")
    private String mBackdropPath;

    @SerializedName("popularity")
    private String mPopularity;

    @SerializedName("vote_count")
    private int mVoteCount;

    @SerializedName("vote_average")
    private float mVoteAverage;

    final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    final String IMAGE_SIZE = "w500";
    final String BACKDROP_SIZE = "w500";


    public Movie(String mPosterPath, String mOverview, String mReleaseDate,
                 int mMovieId, String mTitle, String mBackdropPath,
                 String mPopularity, int mVoteCount, float mVoteAverage) {
        this.mPosterPath = mPosterPath;
        this.mOverview = mOverview;
        this.mReleaseDate = mReleaseDate;
        this.mMovieId = mMovieId;
        this.mTitle = mTitle;
        this.mBackdropPath = mBackdropPath;
        this.mPopularity = mPopularity;
        this.mVoteCount = mVoteCount;
        this.mVoteAverage = mVoteAverage;
    }


    protected Movie(Parcel in) {
        mPosterPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mMovieId = in.readInt();
        mTitle = in.readString();
        mBackdropPath = in.readString();
        mPopularity = in.readString();
        mVoteCount = in.readInt();
        mVoteAverage = in.readFloat();
    }

    public Movie(Cursor cursor) {
        this.mMovieId = cursor.getInt(FragmentPosters.COLUMN_MOVIE_ID);
        this.mTitle = cursor.getString(FragmentPosters.COLUMN_TITLE);
        this.mPosterPath = cursor.getString(FragmentPosters.COLUMN_POSTER);
        this.mBackdropPath = cursor.getString(FragmentPosters.COLUMN_BACKDROP);
        this.mOverview = cursor.getString(FragmentPosters.COLUMN_OVERVIEW);
        this.mVoteAverage = cursor.getFloat(FragmentPosters.COLUMN_RATING);
        this.mReleaseDate = cursor.getString(FragmentPosters.COLUMN_DATE);
        this.mVoteCount= cursor.getInt(FragmentPosters.COLUMN_VOTES);
        this.mPopularity = cursor.getString(FragmentPosters.COLUMN_POPULARITY);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
        dest.writeInt(mMovieId);
        dest.writeString(mTitle);
        dest.writeString(mBackdropPath);
        dest.writeString(mPopularity);
        dest.writeInt(mVoteCount);
        dest.writeFloat(mVoteAverage);
    }

    public String getmPosterPath() {
        return IMAGE_URL + IMAGE_SIZE + mPosterPath;
    }

    public String getmOverview() {
        return mOverview;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public int getmMovieId() {
        return mMovieId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmBackdropPath() {
        return IMAGE_URL + BACKDROP_SIZE + mBackdropPath;
    }

    public String getmPopularity() {
        return mPopularity;
    }

    public int getmVoteCount() {
        return mVoteCount;
    }

    public float getmVoteAverage() {
        return mVoteAverage;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}