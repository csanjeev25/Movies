package com.insomniac.movies;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.insomniac.movies.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setSupportActionBar(activityMainBinding.toolbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.now_playing);
        NowPlayingFragment nowPlayingFragment = (NowPlayingFragment) fragmentManager.findFragmentByTag("data");
        if(nowPlayingFragment == null)
            switchFragment(new NowPlayingFragment());
    }

    public void switchFragment(NowPlayingFragment nowPlayingFragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container,nowPlayingFragment,"data")
                .commit();
    }
}
