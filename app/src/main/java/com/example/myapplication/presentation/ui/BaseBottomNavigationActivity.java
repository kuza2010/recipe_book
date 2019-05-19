package com.example.myapplication.presentation.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseBottomNavigationActivity extends BaseFragmentActivity {

    //Menu items aka tags
    public static final String RECIPES = "Recipes";
    public static final String SEARCH = "Search";
    public static final String PROFILE = "Profile";
    public static final String ERROR = "ERROR";

    @BindView(R.id.navigation_bar)
    BottomNavigationView navigation;

    @Inject
    NavigationToolbarHelper navigationHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        BaseApp.getComponent().inject(this);

        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int menuItemId = menuItem.getItemId();
                        String selectedItem = Utils.getStringFromResId(navigation.getSelectedItemId());

                        navigationHelper.addToHistory(Utils.getStringFromResId(menuItemId));

                        switch (menuItemId) {
                            case R.id.navigation_recipes:
                                setTitle(RECIPES);
                                navigateFragment(RECIPES, selectedItem);
                                return true;
                            case R.id.navigation_serach:
                                setTitle(SEARCH);
                                navigateFragment(SEARCH, selectedItem);
                                return true;
                            case R.id.navigation_profile:
                                setTitle(PROFILE);
                                navigateFragment(PROFILE, selectedItem);
                                return true;
                        }
                        return false;
                    }
                });
        navigation.setSelectedItemId(R.id.navigation_recipes);
    }

    @Override
    public void onBackPressed() {
        int resId = navigationHelper.getAndNavigateBack();
        navigation.setSelectedItemId(resId);
        if (resId == -1)
            super.onBackPressed();
    }

}
