package com.joaonogueira.nmovies.ui.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.joaonogueira.nmovies.R;
import com.joaonogueira.nmovies.ui.model.Movie;
import com.joaonogueira.nmovies.ui.utils.Constants;

public class MainActivity extends AppCompatActivity implements FragmentPosters.MovieClicked{

    public boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
        }else{
            mTwoPane = false;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new FragmentPosters())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentPosters fragment = FragmentPosters.instance;

        if (id == R.id.menu_orderTopRated) {
            FragmentPosters.orderParam = "top_rated";
            fragment.updateMovies();
        }
        else if (id == R.id.menu_orderPopular) {
            FragmentPosters.orderParam = "popular";
            fragment.updateMovies();
        }
        else if (id == R.id.menu_orderFavorite) {
            FragmentPosters.orderParam = "favorite";
            fragment.updateMovies();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        onItemClicked(movie);
    }

    //called when movie clicked
    private void onItemClicked(Movie movie) {
        if (mTwoPane) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.BUNDLE_CONSTANT, movie);
            FragmentDetail fragment = new FragmentDetail();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (!mTwoPane) {
            //Starting detail activity
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, movie);
            startActivity(intent);
        }
    }

}
