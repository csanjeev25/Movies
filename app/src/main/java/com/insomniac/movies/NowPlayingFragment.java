package com.insomniac.movies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insomniac.movies.databinding.FragmentNowPlayingBinding;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sanjeev on 1/25/2018.
 */

public class NowPlayingFragment extends Fragment{

    private FragmentNowPlayingBinding mFragmentNowPlayingBinding;
    private List<Movie> mMovieList = Collections.emptyList();
    private MovieAPI mMovieAPI;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieAPI = RetrofitClient.getRetofitClient().create(MovieAPI.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentNowPlayingBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_now_playing,container,false);
        return mFragmentNowPlayingBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentNowPlayingBinding.nowPlayingList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mMovieAPI.nowPlaying()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieWrapper -> {
                    this.mMovieList = movieWrapper.getResults();
                    mFragmentNowPlayingBinding.nowPlayingList.setAdapter(new NowPlayingAdapter(mMovieList));
                });
    }
}
