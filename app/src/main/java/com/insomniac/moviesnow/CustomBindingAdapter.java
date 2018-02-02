package com.insomniac.moviesnow;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Sanjeev on 1/25/2018.
 */

public class CustomBindingAdapter {

    @BindingAdapter({"poster"})
    public static void loadImage(ImageView imageView,String path){
        String url = String.format("%s%s%s",BuildConfig.IMAGE_BASE_URL,"w342",path);
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.ic_movie_24dp)
                .fit()
                .into(imageView);
    }

    @BindingAdapter({"posterback"})
    public static void loadImageB(ImageView imageView,String path){
        String url = String.format("%s%s%s",BuildConfig.IMAGE_BASE_URL,"original",path);
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.ic_movie_24dp)
                .fit()
                .into(imageView);
    }

    @BindingAdapter({"posterRounded"})
    public static void loadBackdropImageRoundedCorner(ImageView view, String path) {
        String url = String.format("%s/%s/%s", BuildConfig.IMAGE_BASE_URL, "original", path);
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.ic_movie_24dp)
                .fit()
                .transform(new RoundedCornerTransformation())
                .into(view);
    }


}

