package com.example.myapplication.presentation.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.RecipesPreferences;
import com.example.myapplication.Utils;
import com.example.myapplication.presentation.ui.login.LogInActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseBottomNavigationActivity extends BaseFragmentActivity {
    protected static final String IS_REGISTER = "is_register";

    //Menu items aka tags
    public static final String RECIPES = "Recipes";
    public static final String SEARCH = "Search";
    public static final String PROFILE = "Profile";
    public static final String PRODUCT = "Product";
    public static final String ERROR = "ERROR";

    @BindView(R.id.navigation_bar)
    BottomNavigationView navigation;

    @Inject
    NavigationToolbarHelper navigationHelper;
    @Inject
    RecipesPreferences preferences;

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

                        switch (menuItemId) {
                            case R.id.navigation_recipes:
                                setTitle(RECIPES);
                                navigateFragment(RECIPES, selectedItem);
                                break;
                            case R.id.navigation_serach:
                                setTitle(SEARCH);
                                navigateFragment(SEARCH, selectedItem);
                                break;
                            case R.id.navigation_profile:
                                //TODO: занести в Bundle
                                //getIntent().getBooleanExtra(IS_REGISTER,false);
                                boolean isRegister = preferences.getValue(RecipesPreferences.IS_REGISTRED_USER, false);
                                if (isRegister) {
                                    setTitle(PROFILE);
                                    navigateFragment(PROFILE, selectedItem);
                                } else {
                                    startActivity(LogInActivity.getInstance(BaseBottomNavigationActivity.this, true));
                                    return false;
                                }
                                break;
                            case R.id.navigation_fridge:
                                setTitle(PRODUCT);
                                navigateFragment(PRODUCT, selectedItem);
                                break;
                            default:
                                Timber.e("BREAK");
                                return false;
                        }

                        navigationHelper.addToHistory(Utils.getStringFromResId(menuItemId));
                        return true;
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

    @Override
    protected void onDestroy() {
        navigationHelper.clearHistory();
        super.onDestroy();
    }
}
