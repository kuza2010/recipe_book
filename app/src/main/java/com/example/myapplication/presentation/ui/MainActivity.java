package com.example.myapplication.presentation.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.myapplication.R;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation_bar)
    BottomNavigationView navigation;

    public static final String FEED = "Feed";
    public static final String SEARCH = "Search";
    public static final String PROFILE = "Profile";

    private ActionBar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        toolbar = getSupportActionBar();
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_songs:
                        toolbar.setTitle(FEED);
                        return true;
                    case R.id.navigation_albums:
                        toolbar.setTitle(SEARCH);
                        return true;
                    case R.id.navigation_artists:
                        toolbar.setTitle(PROFILE);
                        return true;
                }
                return false;
            }
        });
        toolbar.setTitle("Feed");
    }
}
