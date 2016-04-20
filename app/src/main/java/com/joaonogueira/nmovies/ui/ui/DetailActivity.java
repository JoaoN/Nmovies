package com.joaonogueira.nmovies.ui.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;

import com.joaonogueira.nmovies.R;
import com.joaonogueira.nmovies.ui.model.Movie;
import com.joaonogueira.nmovies.ui.model.TrailerList;
import com.joaonogueira.nmovies.ui.tasks.CheckFavoriteTask;
import com.joaonogueira.nmovies.ui.tasks.SetFavoriteTask;
import com.joaonogueira.nmovies.ui.utils.AnimateFab;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    public FloatingActionButton fab;

    CollapsingToolbarLayout collapsingToolbar;
    Movie movie;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Getting the object
        movie = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        //Checking if the movie is favorite
        try {
            //CheckFavoriteTask isFavoriteTask = new CheckFavoriteTask(this, findViewById(android.R.id.content));
            CheckFavoriteTask isFavoriteTask = new CheckFavoriteTask(this, fab);
            isFavoriteTask.execute(movie);

        } catch (Exception e) {
            //do nothing
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFavoriteTask setFavoriteTask = new SetFavoriteTask(getBaseContext(), view);
                setFavoriteTask.execute(movie);

            }
        });

        initializeScreen(findViewById(android.R.id.content));
    }
    //Initialize the elements
    public void initializeScreen(View view) {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ImageView poster = (ImageView) findViewById(R.id.posterImage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //Setting the activity_detail to show the name and poster
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        assert collapsingToolbar != null;
        collapsingToolbar.setTitle(movie.getmTitle());
        Picasso.with(getApplicationContext()).load(movie.getmBackdropPath()).into(poster);
        /**
         * Create SectionPagerAdapter, set it as adapter to viewPager with setOffscreenPageLimit(2)
         **/
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        /**
         * Setup the mTabLayout with view pager
         */
        tabLayout.setupWithViewPager(viewPager);

        //Change Fab by fragment
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                AnimateFab.animateFab(tab.getPosition(),getBaseContext(), fab, movie);
                //animateFab(tab.getPosition());
                actionFab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    protected void actionFab(final int position) {
        if(position == 0){
            try {
//                CheckFavoriteTask isFavoriteTask = new CheckFavoriteTask(this, findViewById(android.R.id.content));
                CheckFavoriteTask isFavoriteTask = new CheckFavoriteTask(this, fab);
                isFavoriteTask.execute(movie);

            } catch (Exception e) {
                //do nothing
            }
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SetFavoriteTask setFavoriteTask = new SetFavoriteTask(getBaseContext(), view);
                    setFavoriteTask.execute(movie);
                }
            });
        }else if(position == 1){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(FragmentTrailer.trailers != null){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                .parse(TrailerList.MovieTrailer.getTrailerUrl(FragmentTrailer.trailers))));
                    }else{
                        Snackbar.make(view, "There is no trailer to display", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            /**
             * Set fragment to different fragments depending on position in ViewPager
             */
//            If you create the adapter in the same class then you can simply pass YourClassName.this or this.
//            but when you create an adapter outside the class. You have to pass activity_main reference
//            to adapter which tell it this associates to which activity.
            switch (position) {
                case 0:
                    fragment = Fragment.instantiate(getApplicationContext(), FragmentOverview.class.getName());
                    break;
                case 1:
                    fragment = Fragment.instantiate(getApplicationContext(), FragmentTrailer.class.getName());
                    break;
                case 2:
                    fragment = Fragment.instantiate(getApplicationContext(), FragmentReview.class.getName());
                    break;
                default:
                    fragment = Fragment.instantiate(getApplicationContext(), FragmentOverview.class.getName());
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Overview";
                case 1:
                    return "TrailerList";
                case 2:
                    return "ReviewList";
            }
            return null;
        }
    }
}
