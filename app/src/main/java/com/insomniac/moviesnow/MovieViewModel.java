package com.insomniac.moviesnow;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Sanjeev on 1/26/2018.
 */

public class MovieViewModel {

    private int page = 1;
    private BehaviorSubject<List<Movie>> movies = BehaviorSubject.create();
    private BehaviorSubject<Boolean> isLoading = BehaviorSubject.create();
    private MovieAPI mAPI;

    @Inject
    public MovieViewModel(){
        mAPI = RetrofitClient.getInstance().create(MovieAPI.class);
    }

    public Observable<List<Movie>> loadMovies(){
        if(isLoading.getValue())
            return Observable.empty();

        isLoading.onNext(true);

        return mAPI.nowPlaying()
                .map(MovieWrapper::getResults)
                .doOnNext(movies1 -> {List<Movie> movieList = new ArrayList<>(movies.getValue());
                    movieList.addAll(movies1);
                    movies.onNext(movieList);
                    page++;})
                .doOnTerminate(() -> isLoading.onNext(false));
    }

    public BehaviorSubject<Boolean> getIsLoading(){
        return isLoading;
    }

    public Observable<List<Movie>> getMovies(){
        return movies;
    }
}
