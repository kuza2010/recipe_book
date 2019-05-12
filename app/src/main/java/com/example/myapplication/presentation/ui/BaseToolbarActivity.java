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

public abstract class BaseToolbarActivity extends BaseFragmentActivity {

    //Menu items aka tags
    public static final String RECIPES = "Recipes";
    public static final String SEARCH = "Search";
    public static final String PROFILE = "Profile";
    public static final String ERROR = "ERROR";

    @BindView(R.id.navigation_bar)
    BottomNavigationView navigation;

    @Inject
    NavigationToolbarHelper navigationHelper;

    private ActionBar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        BaseApp.getComponent().inject(this);

        toolbar = getSupportActionBar();
        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int menuItemId = menuItem.getItemId();
                        String selectedItem = Utils.getStringFromResId(navigation.getSelectedItemId());

                        navigationHelper.addToHistory(Utils.getStringFromResId(menuItemId));

                        switch (menuItemId) {
                            case R.id.navigation_recipes:
                                toolbar.setTitle(RECIPES);
                                navigateFragment(RECIPES, selectedItem);
                                return true;
                            case R.id.navigation_serach:
                                toolbar.setTitle(SEARCH);
                                navigateFragment(SEARCH, selectedItem);
                                return true;
                            case R.id.navigation_profile:
                                toolbar.setTitle(PROFILE);
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

//    protected void createFragment(String tag) {
//        Fragment fragment;
//        switch (tag) {
//            case RECIPES:
//                fragment = new CategoryFeedFragment();
//                break;
//            case SEARCH:
//                fragment = new TestFragments();
//                break;
//            case PROFILE:
//                Bundle b = new Bundle();
//                b.putString("Q", PROFILE);
//                fragment = new TestFragments();
//                fragment.setArguments(b);
//                break;
//            default:
//                throw new IllegalStateException("Illegal fragment tag " + tag);
//        }
//
//        getSupportFragmentManager().
//                beginTransaction().
//                add(R.id.container, fragment, tag).
//                commitNow();
//
//        createdFragments.push(tag);
//    }
//
//    protected abstract void navigateFragment(String tag, String current);
//
//    protected abstract void showFragment(String navigateTo, String current);
}
