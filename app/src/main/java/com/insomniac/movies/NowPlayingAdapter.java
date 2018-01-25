package com.insomniac.movies;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

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

    }
}
