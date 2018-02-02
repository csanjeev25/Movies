package com.insomniac.moviesnow;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insomniac.moviesnow.databinding.ViewHolderBackdropBinding;
import com.insomniac.moviesnow.databinding.ViewHolderNowPlayingBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanjeev on 1/25/2018.
 */

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder> {
    private static final int OVERVIEW = 0;
    private static final int BACKDROP = 1;
    private List<Movie> movies;

    public NowPlayingAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public NowPlayingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == OVERVIEW) {
            ViewHolderNowPlayingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_holder_now_playing, parent, false);
            return new NowPlayingViewHolderOverview(binding);
        } else {
            ViewHolderBackdropBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_holder_backdrop, parent, false);
            return new NowPlayingViewHolderBackdrop(binding);
        }
    }

    @Override
    public void onBindViewHolder(NowPlayingViewHolder holder, int position) {
        holder.bindTo(movies.get(position));
        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            context.startActivity(DetailMovieActivity.newIntent(context, movies.get(position)));
        });
    }

    @Override public int getItemViewType(int position) {
        return movies.get(position).getVoteAverage() <= 5.0f ? OVERVIEW : BACKDROP;
    }

    @Override public int getItemCount() {
        return movies.size();
    }

    public class NowPlayingViewHolderOverview extends NowPlayingViewHolder {
        ViewHolderNowPlayingBinding binding;

        public NowPlayingViewHolderOverview(ViewHolderNowPlayingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Movie movie) {
            binding.setMovie(movie);
            binding.executePendingBindings();
        }
    }

    public class NowPlayingViewHolderBackdrop extends NowPlayingViewHolder {
        ViewHolderBackdropBinding binding;

        public NowPlayingViewHolderBackdrop(ViewHolderBackdropBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Movie movie) {
            binding.setMovie(movie);
            binding.executePendingBindings();
        }
    }


    public void setItems(List<Movie> items) {
        if (items == null) {
            return;
        }

        this.movies = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    abstract class NowPlayingViewHolder extends RecyclerView.ViewHolder {

        NowPlayingViewHolder(View view) {
            super(view);
        }

        abstract void bindTo(Movie movie);
    }

}
