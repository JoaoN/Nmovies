package com.joaonogueira.nmovies.ui.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Joao on 09/04/2016.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.joaonogueira.nmovies";

    public static final Uri BASE_CONTENT_URI =  Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";
    public static final String PATH_FAVORITE = "favorite";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +"/"
                +CONTENT_AUTHORITY +"/" +PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +"/"
                +CONTENT_AUTHORITY +"/" +PATH_MOVIE;

        public static final String TABLE_NAME = "movie";
        //Movie Id
        public static final String COLUMN_MOVIE_ID = "movie_id";
        //Movie Title
        public static final String COLUMN_TITLE = "title";
        //Movie Poster
        public static final String COLUMN_POSTER = "poster";
        //Movie Second Poster
        public static final String COLUMN_BACKDROP = "backdrop";
        //Movie Synopses
        public static final String COLUMN_OVERVIEW = "overview";
        //Movie Rating
        public static final String COLUMN_RATING  = "rating";
        //Movie Release Date
        public static final String COLUMN_DATE = "date";
        //Movie Vote Count
        public static final String COLUMN_VOTES = "votes";
        //Movie Vote Count
        public static final String COLUMN_POPULARITY = "popularity";

        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class FavoriteEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
    }
}
