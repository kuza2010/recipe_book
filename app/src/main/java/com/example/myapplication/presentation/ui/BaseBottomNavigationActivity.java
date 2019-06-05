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
    public static final String IS_REGISTER = "is_register";
    public static final String USER_ID = "user_id";

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
    protected RecipesPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        BaseApp.getComponent().inject(this);

        navigation.setOnNavigationItemSelectedListener(
                menuItem -> {
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
                            if (isRegister()) {
                                setTitle(PROFILE);
                                navigateFragment(PROFILE, selectedItem);
                            } else {
                                Timber.d("Unauthorized user attempt to open preferences -> redirect login");
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
                            throw new IllegalStateException("onNavigationItemSelected: navigate failed. Indefinite id!");
                }

                    navigationHelper.addToHistory(Utils.getStringFromResId(menuItemId));
                    return true;
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

    private boolean isRegister() {
        return getIntent().getBooleanExtra(IS_REGISTER, false);
    }
}
