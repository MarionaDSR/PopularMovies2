package com.example.android.popularmovies2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.model.Trailer;

import java.util.List;

/**
 * Created by mariona on 21/2/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private List<Trailer> mTrailers;

    private final TrailerAdapterOnClickHandler mClickHandler;

    public TrailerAdapter(TrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public void setTrailers(List<Trailer> trailers) {
        mTrailers = trailers;
    }

    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder holder, int position) {
        Trailer trailer = mTrailers.get(position);
        holder.mTvTitle.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        if (mTrailers == null)
            return 0;
        return mTrailers.size();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected final TextView mTvTitle;

        public TrailerAdapterViewHolder(View view) {
            super(view);
            mTvTitle = (TextView) view.findViewById(R.id.tv_trailer_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Trailer trailer = mTrailers.get(adapterPosition);
            mClickHandler.onTrailerClick(trailer);
        }
    }

    public interface TrailerAdapterOnClickHandler {
        void onTrailerClick(Trailer trailer);
    }
}
