package com.insomniac.moviesnow;

import io.reactivex.Observable;
import retrofit2.http.GET;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Sanjeev on 1/25/2018.
 */

public interface MovieAPI {

    @GET("movie/now_playing")
    Observable<MovieWrapper> nowPlaying();
    Observable<MovieWrapper> nowPlaying(@Query("page") int page);

    @GET("movie/{movieId}/videos")
    Observable<VideoWrapper> getVideos(@Path("movieId") int movieId);
}