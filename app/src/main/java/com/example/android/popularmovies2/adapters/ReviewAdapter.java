package com.example.android.popularmovies2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.model.Review;

import java.util.List;

/**
 * Created by mariona on 21/2/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private List<Review> mReviews;

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_list_item, parent, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.mTvContent.setText(review.getContent());
        holder.mTvAuthor.setText(review.getAuthor());
    }

    @Override
    public int getItemCount() {
        if (mReviews == null)
            return 0;
        return mReviews.size();
    }

    protected class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        protected final TextView mTvContent;
        protected final TextView mTvAuthor;

        public ReviewAdapterViewHolder(View view) {
            super(view);
            mTvContent = (TextView) view.findViewById(R.id.tv_review_content);
            mTvAuthor = (TextView) view.findViewById(R.id.tv_review_author);
        }
    }

}
