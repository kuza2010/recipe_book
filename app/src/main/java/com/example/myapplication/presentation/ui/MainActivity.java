package com.example.myapplication.presentation.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.example.myapplication.presentation.ui.fragments.CategoryFeedFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation_bar)
    BottomNavigationView navigation;

    public static final String CATEGORIES = "Categories";
    public static final String SEARCH = "Search";
    public static final String PROFILE = "Profile";

    private ActionBar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        toolbar = getSupportActionBar();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_categories:
                        toolbar.setTitle(CATEGORIES);
                        setFragment(new CategoryFeedFragment());
                        return true;
                    case R.id.navigation_serach:
                        toolbar.setTitle(SEARCH);
                        setFragment(null);
                        return true;
                    case R.id.navigation_profile:
                        toolbar.setTitle(PROFILE);
                        setFragment(null);
                        return true;
                }
                return false;
            }
        });
        navigation.setSelectedItemId(R.id.navigation_categories);
    }



    private void setFragment(Fragment fragmentToSet){
        if(fragmentToSet == null){
            getSupportFragmentManager()
                    .popBackStack();
            return;
        }
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container,fragmentToSet)
                .addToBackStack("DE")
                .commit();
    }
}
