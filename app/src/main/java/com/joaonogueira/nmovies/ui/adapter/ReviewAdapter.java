package com.joaonogueira.nmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joaonogueira.nmovies.R;
import com.joaonogueira.nmovies.ui.model.ReviewList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joao on 13/04/2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewList.MovieReview> mReviews;
    private List<String> movieReview;
    private Context mContext;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textReview;
        private TextView authorReview;
        public String mBoundString;

        public ViewHolder(View view) {
            super(view);
            textReview = (TextView) view.findViewById(R.id.contentReview);
            authorReview = (TextView) view.findViewById(R.id.authorReview);
        }
    }
    //Set poster url
    public void setReviews(List<ReviewList.MovieReview> reviews){
        mReviews = reviews;
        notifyDataSetChanged();
    }

    public ReviewAdapter(Context context){
        mContext = context;
    }


    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        holder.textReview.setText(mReviews.get(position).getContent());
        holder.authorReview.setText( mReviews.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        if(mReviews != null){
            return mReviews.size();
        }
        else return 0;
    }
}
