package com.insomniac.movies;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Sanjeev on 1/25/2018.
 */

public interface MovieAPI {

    @GET("/3/movie/now_playing")
    Observable<MovieWrapper> nowPlaying();
}


