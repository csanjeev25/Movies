package com.insomniac.movies;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
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
