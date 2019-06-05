package com.example.myapplication.framework.dagger;

import com.example.myapplication.content_provider.SearchProvider;
import com.example.myapplication.framework.dagger.modules.ApplicationModule;
import com.example.myapplication.framework.dagger.modules.DatabaseModule;
import com.example.myapplication.framework.dagger.modules.NetworkModule;
import com.example.myapplication.framework.dagger.modules.PresenterModule;
import com.example.myapplication.presentation.presenter.login.LoginPresenterImpl;
import com.example.myapplication.presentation.ui.BaseBottomNavigationActivity;
import com.example.myapplication.presentation.ui.QueryTextListener;
import com.example.myapplication.presentation.ui.fragments.RecipeAdapter;
import com.example.myapplication.presentation.ui.fragments.category_fragment.CategoryFeedFragment;
import com.example.myapplication.presentation.ui.fragments.preferences.PreferencesFragment;
import com.example.myapplication.presentation.ui.fragments.refrigerator_fragment.RefrigeratorFragment;
import com.example.myapplication.presentation.ui.fragments.search_fragment.SearchRecipeFragment;
import com.example.myapplication.presentation.ui.login.LogInActivity;
import com.example.myapplication.presentation.ui.product.AddProductActivity;
import com.example.myapplication.presentation.ui.recipe.MainRecipeActivity;
import com.example.myapplication.presentation.ui.recipe.RecipeActivity;
import com.example.myapplication.presentation.ui.registration.RegistrationActivity;
import com.example.myapplication.presentation.ui.search.SearchByIngredientActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        PresenterModule.class,
        ApplicationModule.class,
        NetworkModule.class,
        DatabaseModule.class})
public interface AppComponent {

    void inject (BaseBottomNavigationActivity activity);
    void inject (LogInActivity activity);
    void inject (RecipeActivity activity);
    void inject (SearchByIngredientActivity activity);
    void inject (AddProductActivity addProductActivity);
    void inject (MainRecipeActivity mainRecipeActivity);
    void inject (RegistrationActivity registrationActivity);

    void inject(CategoryFeedFragment categoryFeedFragment);
    void inject(SearchRecipeFragment searchRecipeFragment);
    void inject(RefrigeratorFragment refrigeratorFragment);
    void inject(PreferencesFragment preferencesFragment);

    void inject(SearchProvider provider);

    void inject(QueryTextListener listener);

    void inject(RecipeAdapter adapter);

    void inject(LoginPresenterImpl loginPresenter);
}
