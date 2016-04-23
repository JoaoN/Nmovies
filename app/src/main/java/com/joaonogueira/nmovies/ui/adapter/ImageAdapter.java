package com.joaonogueira.nmovies.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joaonogueira.nmovies.R;
import com.joaonogueira.nmovies.ui.ui.FragmentPosters;
import com.joaonogueira.nmovies.ui.ui.MainActivity;
import com.joaonogueira.nmovies.ui.utils.Utility;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Joao on 30/03/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c){
        mContext = c;
    }


    @Override
    public int getCount(){
        return images.size();
    }

    @Override
    public Object getItem(int position){
        return images.get(position);
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if (!Utility.checkNetwork(mContext) && Objects.equals(FragmentPosters.orderParam, "favorite")) {
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.fragment_no_internet, null);
                TextView textView = (TextView) grid.findViewById(R.id.grid_text);
                ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
                textView.setText(images.get(position));
                imageView.setImageResource(R.drawable.nopicture);

            return grid;
        } else {
            ImageView imageview;
            if (convertView == null){
                imageview = new ImageView(mContext);
                imageview.setPadding(0, 0, 0, 0);
                imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageview.setAdjustViewBounds(true);
            } else {
                imageview = (ImageView) convertView;
            }

            Picasso.with(mContext)
                    .load(images.get(position))
                    //.placeholder(R.drawable.nopicture)
                    .error(R.drawable.nopicture)
                    .into(imageview);
            return imageview;
        }
    }

    public void addItem(String url){
        images.add(url);
    }

    public void clearItems() {
        images.clear();
    }

    public ArrayList<String> images = new ArrayList<String>();
}