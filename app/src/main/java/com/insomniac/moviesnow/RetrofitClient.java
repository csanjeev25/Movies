package com.insomniac.moviesnow;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sanjeev on 1/24/2018.
 */

public class RetrofitClient {
    private static final String API_KEY = "api_key";

            private static Retrofit retrofitClient;

            private RetrofitClient() {

                   }

           public static Retrofit getInstance() {
               if (retrofitClient == null) {
                        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
                        okHttpClient.addInterceptor(chain -> {
                                Request request = chain.request();
                                HttpUrl url = request.url().newBuilder()
                                                .addQueryParameter(API_KEY, BuildConfig.API_KEY).build();
                                request = request.newBuilder().url(url).build();
                                return chain.proceed(request);
                            });
                        retrofitClient = new Retrofit.Builder()
                                        .baseUrl(BuildConfig.END_POINT)
                                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(okHttpClient.build())
                                        .build();
                    }
                return retrofitClient;
            }
    }