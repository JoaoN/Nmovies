package com.joaonogueira.nmovies.ui.utils;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.joaonogueira.nmovies.R;
import com.joaonogueira.nmovies.ui.model.Movie;
import com.joaonogueira.nmovies.ui.tasks.CheckFavoriteTask;

/**
 * Created by Joao on 18/04/2016.
 */
public class AnimateFab {


    public static void animateFab(final int position, final Context mContext,
                                   final FloatingActionButton fab, final Movie movie) {

        final int[] colorIntArray = {R.color.colorPrimaryDark,R.color.colorPrimary, R.color.colorAccent};

        if(position == 2){
            fab.hide();
        }
        else {
            fab.show();
            fab.clearAnimation();
            // Scale down animation
            ScaleAnimation shrink = new ScaleAnimation(1f, 0.1f, 1f, 0.1f
                    , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            shrink.setDuration(100);     // animation duration in milliseconds
            shrink.setInterpolator(new AccelerateInterpolator());
            shrink.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Change FAB color and icon
                    fab.setBackgroundTintList(ContextCompat.getColorStateList(mContext,
                            colorIntArray[position]));
                    if(position == 0){
                        try {
//                            CheckFavoriteTask isFavoriteTask = new CheckFavoriteTask(mContext,
//                                    fab.findViewById(android.R.id.content));
                            CheckFavoriteTask isFavoriteTask = new CheckFavoriteTask(mContext,fab);
                            isFavoriteTask.execute(movie);
                        } catch (Exception e) {
                            //do nothing
                        }
                    }else if(position == 1){
                        fab.setImageDrawable(ContextCompat.getDrawable(mContext,
                                android.R.drawable.ic_media_play));
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri
//                                .parse("http://www.youtube.com/user/punjabiradiousa")));
                    }

                    // Rotate Animation
                    Animation rotate = new RotateAnimation(60.0f, 0.0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                            0.5f);
                    rotate.setDuration(150);
                    rotate.setInterpolator(new DecelerateInterpolator());

                    // Scale up animation
                    ScaleAnimation expand = new ScaleAnimation(0.1f, 1f, 0.1f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    expand.setDuration(150);     // animation duration in milliseconds
                    expand.setInterpolator(new DecelerateInterpolator());

                    // Add both animations to animation state
                    AnimationSet s = new AnimationSet(false); //false means don't share interpolators
                    s.addAnimation(rotate);
                    s.addAnimation(expand);
                    fab.startAnimation(s);
                }


                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            fab.startAnimation(shrink);
        }
    }

}
