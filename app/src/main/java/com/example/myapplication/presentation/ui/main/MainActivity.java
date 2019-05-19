package com.example.myapplication.presentation.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.presentation.ui.BaseBottomNavigationActivity;
import com.example.myapplication.presentation.ui.fragments.category_fragment.CategoryFeedFragment;
import com.example.myapplication.presentation.ui.fragments.search_fragment.SearchFragment;
import com.example.myapplication.presentation.ui.fragments.TestFragments;

import timber.log.Timber;

public class MainActivity extends BaseBottomNavigationActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void createFragment(String tag) {
        Fragment fragment;
        switch (tag) {
            case RECIPES:
                fragment = new CategoryFeedFragment();
                break;
            case SEARCH:
                fragment = new SearchFragment();
                break;
            case PROFILE:
                Bundle b = new Bundle();
                b.putString("Q", PROFILE);
                fragment = new TestFragments();
                fragment.setArguments(b);
                break;
            default:
                throw new IllegalStateException("Illegal fragment tag " + tag);
        }

        getSupportFragmentManager().
                beginTransaction().
                add(R.id.container, fragment, tag).
                commitNow();

        createdFragments.push(tag);
    }

    @Override
    protected void navigateFragment(String navigable, String current) {
        if (!isFragmentCreated(navigable))
            createFragment(navigable);

        showFragment(navigable, current);
    }

    @Override
    protected void showFragment(String navigable, String current) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(current);
        Fragment navigableFragment = getSupportFragmentManager().findFragmentByTag(navigable);

        if (navigableFragment == null) {
            Timber.e("showFragment: destination fragment is null");
            throw new IllegalStateException("destination fragment is null");
        } else if (currentFragment == null) {
            Timber.e("showNewFragment: current fragment is null");
            throw new IllegalStateException("current fragment is null");
        } else
            getSupportFragmentManager().
                    beginTransaction().
                    detach(currentFragment).
                    attach(navigableFragment).
                    commitNow();
    }
}
