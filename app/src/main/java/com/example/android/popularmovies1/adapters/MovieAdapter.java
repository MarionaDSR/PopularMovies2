package com.example.android.popularmovies1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.popularmovies1.DetailActivity;
import com.example.android.popularmovies1.R;
import com.example.android.popularmovies1.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by mariona on 26/1/17.
 */

public class MovieAdapter extends BaseAdapter {

    private final Context mContext;
    private Movie[] mMovies;

    public MovieAdapter(Context c) {
        mContext = c;
    }

    @Override
    public Object getItem(int i) {
        return mMovies[i];
    }

    @Override
    public long getItemId(int i) {
        return mMovies[i].getId();
    }

    @Override
    public int getCount() {
        if (mMovies != null) {
            return mMovies.length;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }

        String pictureUrl = mContext.getResources().getString(R.string.imagedb_url_root)
                + mMovies[position].getPosterPath();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("movie", mMovies[position]);
                mContext.startActivity(intent);
            }
        });

        Picasso p = Picasso.with(mContext);
        p.load(pictureUrl)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image_error)
                .into(imageView);

        return imageView;
    }

    public void setMoviesData(Movie[] movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }
}
