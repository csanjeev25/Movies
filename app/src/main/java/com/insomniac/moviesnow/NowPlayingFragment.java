package com.insomniac.moviesnow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.insomniac.moviesnow.databinding.FragmentNowPlayingBinding;

import java.util.ArrayList;
import java.util.List;


import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import rx.Observable;

/**
 * Created by Sanjeev on 1/25/2018.
 */

public class NowPlayingFragment extends Fragment{

    private FragmentNowPlayingBinding mFragmentNowPlayingBinding;
    private List<Movie> mMovieList = new ArrayList<>();
    private MovieAPI mMovieAPI;
    private CompositeDisposable mCompositeDisposable;
    private MovieViewModel mMovieViewModel;
    private static final String SAVE_MOVIE_LIST = "MOVIE_LIST";
    private NowPlayingAdapter mNowPlayingAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Handler mHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mMovieAPI = RetrofitClient.getInstance().create(MovieAPI.class);
        mMovieViewModel = new MovieViewModel();
        mCompositeDisposable = new CompositeDisposable();
        mHandler = new Handler();
        if(savedInstanceState != null)
            mMovieList = savedInstanceState.getParcelableArrayList(SAVE_MOVIE_LIST);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentNowPlayingBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_now_playing,container,false);
        mFragmentNowPlayingBinding.swipeRefreshLayout.setRefreshing(true);
        return mFragmentNowPlayingBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_MOVIE_LIST,new ArrayList<>(mMovieList));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mFragmentNowPlayingBinding.nowPlayingList.setLayoutManager(mLinearLayoutManager);
        mNowPlayingAdapter = new NowPlayingAdapter(mMovieList);
        mFragmentNowPlayingBinding.swipeRefreshLayout.setOnRefreshListener(this::loadMovies);
        mFragmentNowPlayingBinding.nowPlayingList.setAdapter(mNowPlayingAdapter);
        initBindings();
        if(mMovieList.isEmpty()){
            loadMovies();
        }else
            mNowPlayingAdapter.setItems(mMovieList);
    }

    private void initBindings(){
        Observable<Void> infiniteScrollObservable = Observable.create(subscriber -> {
            mFragmentNowPlayingBinding.nowPlayingList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int visibleItemCount = mLinearLayoutManager.getChildCount();
                    int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"onScrolled",Toast.LENGTH_SHORT).show();
                        }
                    });
                    if((visibleItemCount + firstVisibleItem) >= totalItemCount )
                        subscriber.onNext(null);
                }
            });

            mMovieViewModel.getMovies()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((movies) -> {
                        mMovieList = movies;
                        mNowPlayingAdapter.setItems(mMovieList);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"mMovieViewModel.getMovies()",Toast.LENGTH_SHORT).show();
                            }
                        });
                    });

            mMovieViewModel.getIsLoading().observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> {
                mFragmentNowPlayingBinding.progressBar.setVisibility(aBoolean ? View.VISIBLE: View.GONE);
            });
        });

        infiniteScrollObservable.subscribe(aVoid -> loadMovies());
    }

    public void loadMovies(){

        mCompositeDisposable.add(mMovieViewModel.loadMovies()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d("now playing fragment", "load movies error"))
                .subscribe());

        Toast.makeText(getActivity(),"loadMovies",Toast.LENGTH_SHORT).show();

    }

    public void setData(List<Movie> movies){
        mMovieList = movies;
    }

    public List<Movie> getData(){
        return mMovieList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}

