package com.insomniac.movies;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Sanjeev on 1/25/2018.
 */

public class CustomBindingAdapter {

    @BindingAdapter({"app_poster"})
    public static void loadImage(ImageView imageView,String path){
        String url = String.format("%s%s%s",BuildConfig.IMAGE_BASE_URL,"w500",path);
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.ic_movie_24dp)
                .fit()
                .into(imageView);
    }

    @BindingAdapter("{backdrop}")
    public static void loadBackDropImage(ImageView imageView,String path){
        String url = String.format("%s%s%s",BuildConfig.IMAGE_BASE_URL,"original",path);
        Picasso.with(imageView.getContext())
                .load(url)
                .fit()
                .placeholder(R.drawable.ic_movie_24dp)
                .into(imageView);
    }
}
