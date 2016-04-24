# Nmovies
# INSTALLATION:

Create an account on https://www.themoviedb.org/ and request an api key. Use this api key in your Constants file as:

MOVIE_DB_API_KEY = "YOUR_TMDB_API_KEY_HERE"

#App Specifications:

App uses TMDB Api to fetch the data.

The main layout shows recent popular movies.

There is toggle option to see "Popular Movies" , "Top Rated Movies" and "Favorite Movies".

Favorite Movies are movies saved offline using Content Providers.

The detail view shows: Movie Synopsis,. Poster,. Rating, Trailer and Reviews.

A button to mark a movie favorite is present in detail View.

#Libraries used:

Picasso for handling download and caching of images.

Retrofit as Rest-Client for fetching data from TMDB Api.

ButterKnife for view binding.
