package com.example.myapplication.presentation.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.RecipesPreferences;
import com.example.myapplication.presentation.ui.BaseBottomNavigationActivity;
import com.example.myapplication.presentation.ui.fragments.category_fragment.CategoryFeedFragment;
import com.example.myapplication.presentation.ui.fragments.preferences.PreferencesFragment;
import com.example.myapplication.presentation.ui.fragments.refrigerator_fragment.RefrigeratorFragment;
import com.example.myapplication.presentation.ui.fragments.search_fragment.SearchRecipeFragment;
import com.example.myapplication.presentation.ui.login.LogInActivity;

import javax.inject.Inject;

import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.USER_UNAUTHORIZED_ID;

public class MainActivity extends BaseBottomNavigationActivity {

    public static Intent getInstance(Context packageContext,boolean isRegister,int userId) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(IS_REGISTER, isRegister);
        intent.putExtra(USER_ID,userId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void createFragment(String tag) {
        //TODO: Изменить бандлы
        Fragment fragment;
        switch (tag) {
            case RECIPES:
                fragment = new CategoryFeedFragment();
                break;
            case SEARCH:
                fragment = new SearchRecipeFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(IS_REGISTER,getIntent().getBooleanExtra(IS_REGISTER,false));
                fragment.setArguments(bundle);
                break;
            case PRODUCT:
                fragment = new RefrigeratorFragment();
                bundle = new Bundle();
                bundle.putInt(USER_ID,getIntent().getIntExtra(USER_ID,USER_UNAUTHORIZED_ID));
                fragment.setArguments(bundle);
                break;
            case PROFILE:
                fragment = new PreferencesFragment();
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
        if(current.equals(navigable)) {
            Timber.d("navigable equals current -> skip");
            return;
        }

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
