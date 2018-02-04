package com.insomniac.moviesnow;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.insomniac.moviesnow.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_INTERNET = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setSupportActionBar(activityMainBinding.toolbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.now_playing);
        NowPlayingFragment nowPlayingFragment = (NowPlayingFragment) fragmentManager.findFragmentByTag("data");
        requestPermissions(new String[]{Manifest.permission.INTERNET},REQUEST_INTERNET);
        if(nowPlayingFragment == null)
            switchFragment(new NowPlayingFragment());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_INTERNET : if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                                        switchFragment(new NowPlayingFragment());
                                    else
                                        Toast.makeText(this, "Not Granted Permission", Toast.LENGTH_SHORT).show();
                                    break;

        }
    }

    public void switchFragment(NowPlayingFragment nowPlayingFragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container,nowPlayingFragment,"data")
                .commit();
    }
}
