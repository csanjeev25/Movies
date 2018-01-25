package com.insomniac.movies;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sanjeev on 1/24/2018.
 */

public class RetrofitClient {

    private static final String API_KEY = "api_key";
    private static Retrofit sRetofitClient;

    public static Retrofit getRetofitClient(){
        if(sRetofitClient == null){
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.addInterceptor(chain -> {
                Request request = chain.request();
                HttpUrl httpUrl = request.url().newBuilder().addQueryParameter(API_KEY,BuildConfig.API_KEY).build();
                request = request.newBuilder().url(httpUrl).build();
                return chain.proceed(request);
            });
            sRetofitClient = new Retrofit.Builder()
                    .baseUrl(BuildConfig.END_POINT)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return sRetofitClient;
    }
}
