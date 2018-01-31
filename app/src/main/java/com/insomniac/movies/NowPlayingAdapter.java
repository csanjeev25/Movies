package com.insomniac.movies;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.insomniac.movies.databinding.ViewHolderNowPlayingBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanjeev on 1/25/2018.
 */

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>{

    private List<Movie> mMovieList;

    public NowPlayingAdapter(List<Movie> movies){
        mMovieList = movies;
    }

    @Override
    public NowPlayingAdapter.NowPlayingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderNowPlayingBinding viewHolderNowPlayingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.view_holder_now_playing,parent,false);
        return new NowPlayingViewHolder(viewHolderNowPlayingBinding);
    }

    @Override
    public void onBindViewHolder(NowPlayingAdapter.NowPlayingViewHolder holder, int position) {
        holder.bindTo(mMovieList.get(position));
        holder.itemView.setOnClickListener(view -> {
            Context context = holder.itemView.getContext();
            context.startActivity(DetailMovieActivity.newIntent(context,mMovieList.get(position)));
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class NowPlayingViewHolder extends RecyclerView.ViewHolder{

        ViewHolderNowPlayingBinding mViewHolderNowPlayingBinding;

        public NowPlayingViewHolder(ViewHolderNowPlayingBinding binding){
           super(binding.getRoot());
           mViewHolderNowPlayingBinding = binding;
        }

        public void bindTo(Movie movie){
            mViewHolderNowPlayingBinding.setMovie(movie);
            mViewHolderNowPlayingBinding.executePendingBindings();
        }
    }

    public void setItems(List<Movie> movieList){
        if(movieList == null)
            return;
        mMovieList = new ArrayList<>(movieList);
        notifyDataSetChanged();
    }
}
