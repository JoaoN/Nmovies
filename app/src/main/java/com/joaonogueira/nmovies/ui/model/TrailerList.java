package com.joaonogueira.nmovies.ui.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Joao on 11/04/2016.
 */
public class TrailerList {

    @SerializedName("results")
    public List<MovieTrailer> trailerList;

    public void setTrailerList(List<MovieTrailer> trailerList){
        this.trailerList = trailerList;
    }

    public List<MovieTrailer> getTrailerList() {
        return trailerList;
    }


    public static class MovieTrailer{
        @SerializedName("id")
        private String mId;

        @SerializedName("key")
        private String mKey;

        @SerializedName("name")
        private String mTrailerTitle;

        @SerializedName("site")
        private String mSite;

        //Uri to get trailer keys
        static final String TRAILER_URL = "http://api.themoviedb.org/3/movie";
        //Youtube uri
        static final String YOUTUBE_URL = "http://www.youtube.com/watch/";
        //TrailerList poster url
        static final String TRAILER_IMAGE_URL = "http://img.youtube.com/vi/";

        public String getId() {
            return mId;
        }

        public String getKey() {
            return mKey;
        }

        public String getTrailerTitle() {
            return mTrailerTitle;
        }

        public String getSite() {
            return mSite;
        }

        public static String getImageUrl(MovieTrailer trailer){
            return TRAILER_IMAGE_URL + trailer.getKey()+"/hqdefault.jpg";

        }
        public static String getTrailerUrl(MovieTrailer trailer){
            return YOUTUBE_URL + trailer.getKey();

        }

    }
}
