package com.joaonogueira.nmovies.ui.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Joao on 11/04/2016.
 */
public class ReviewList {

    @SerializedName("results")
    List<MovieReview> reviewsList;

    public void setReviewsList(List<MovieReview> reviewsList) {
        this.reviewsList = reviewsList;
    }

    public List<MovieReview> getReviewsList() {
        return reviewsList;
    }

    public static class MovieReview{

        @SerializedName("id")
        private String mId;

        @SerializedName("author")
        private String mAuthor;

        @SerializedName("content")
        private String mContent;

        @SerializedName("url")
        private String mUrl;

        public void setmId(String mId) {
            this.mId = mId;
        }

        public void setmAuthor(String mAuthor) {
            this.mAuthor = mAuthor;
        }

        public void setmContent(String mContent) {
            this.mContent = mContent;
        }

        public void setmUrl(String mUrl) {
            this.mUrl = mUrl;
        }

        public String getId() {
            return mId;
        }

        public String getUrl() {
            return mUrl;
        }

        public String getContent(){
            return mContent;
        }

        public String getAuthor(){
            return  mAuthor;
        }


    }
}
