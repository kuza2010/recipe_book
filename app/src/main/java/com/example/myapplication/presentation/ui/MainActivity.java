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

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(MainActivity.class.getCanonicalName(), "onCreate: on crate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        toolbar =  getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("Feed");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_songs:
                    toolbar.setTitle("Feed");
                    return true;
                case R.id.navigation_albums:
                    toolbar.setTitle("Search");
                    return true;
                case R.id.navigation_artists:
                    toolbar.setTitle("Profile");
                    return true;
            }
            return false;
        }
    };


}
