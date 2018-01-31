package com.insomniac.movies;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.insomniac.movies.databinding.ActivityDetailMovieBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailMovieActivity extends AppCompatActivity {

    private static final String ARG_MOVIE = "selectedMovie";
    private Movie mMovie;
    private MovieAPI mMovieAPI;
    private List<Video> mVideos = new ArrayList<>();

    public static Intent newIntent(Context context,Movie movie){
        Intent intent = new Intent(context,DetailMovieActivity.class);
        intent.putExtra(ARG_MOVIE,movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieAPI = RetrofitClient.getRetofitClient().create(MovieAPI.class);
        if(getIntent() != null){
            Bundle bundle = getIntent().getExtras();
            mMovie = bundle.getParcelable(ARG_MOVIE);
        }
        ActivityDetailMovieBinding activityDetailMovieBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail_movie);
        setContentView(activityDetailMovieBinding.getRoot());
        activityDetailMovieBinding.setMovie(mMovie);
        activityDetailMovieBinding.executePendingBindings();
        setSupportActionBar(activityDetailMovieBinding.toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mMovieAPI.getVideos(mMovie.getId())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(videoWrapper -> {
                    if(videoWrapper.getResults().size() > 0){
                        return Observable.fromArray(videoWrapper.getResults());
                    }else
                        return Observable.empty();
                }).firstElement()
                .subscribe(videos -> {
                    if(videos != null){
                        activityDetailMovieBinding.plyOverlay.setVisibility(View.VISIBLE);
                        activityDetailMovieBinding.plyOverlay.setOnClickListener(view -> {
                            int size = videos.size();
                            String url = "http://www.youtube.com/watch?v=" + videos.get(size - 1).getKey();
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        });
                    }
                },Throwable::printStackTrace);
    }
}
