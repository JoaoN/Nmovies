package com.joaonogueira.nmovies.ui.ui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.joaonogueira.nmovies.R;

public class MainActivity extends AppCompatActivity {

    public static boolean TWO_PANE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            TWO_PANE = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                // Add fragment
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.movie_detail_container, new FragmentMain())
//                        .commit();
            }
        }else{
            TWO_PANE = false;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new FragmentMain())
                    .commit();
        }
    }

//    getSupportFragmentManager().beginTransaction().
//    add(R.id.container, new FragmentMain()).
//    commit();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentMain fragment = FragmentMain.instance;

        if (id == R.id.menu_orderTopRated) {
            FragmentMain.orderParam = "top_rated";
            fragment.updateMovies();
        }
        else if (id == R.id.menu_orderPopular) {
            FragmentMain.orderParam = "popular";
            fragment.updateMovies();
        }
        else if (id == R.id.menu_orderFavorite) {
            FragmentMain.orderParam = "favorite";
            fragment.updateMovies();
        }

        return super.onOptionsItemSelected(item);
    }


    /////////Teste////////

}
