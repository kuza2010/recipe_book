package com.example.myapplication.framework.dagger;

import com.example.myapplication.content_provider.SearchProvider;
import com.example.myapplication.framework.dagger.modules.ApplicationModule;
import com.example.myapplication.framework.dagger.modules.NetworkModule;
import com.example.myapplication.framework.dagger.modules.PresenterModule;
import com.example.myapplication.presentation.ui.BaseBottomNavigationActivity;
import com.example.myapplication.presentation.ui.fragments.RecipeAdapter;
import com.example.myapplication.presentation.ui.QueryTextListener;
import com.example.myapplication.presentation.ui.fragments.search_fragment.SearchRecipeFragment;
import com.example.myapplication.presentation.ui.fragments.category_fragment.CategoryFeedFragment;
import com.example.myapplication.presentation.ui.login.LogInActivity;
import com.example.myapplication.presentation.ui.recipe.RecipeActivity;
import com.example.myapplication.presentation.ui.search.SearchByIngredientActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PresenterModule.class,
        ApplicationModule.class,
        NetworkModule.class})
public interface AppComponent {

    void inject (BaseBottomNavigationActivity activity);
    void inject (LogInActivity activity);
    void inject (RecipeActivity activity);
    void inject (SearchByIngredientActivity activity);

    void inject(CategoryFeedFragment categoryFeedFragment);
    void inject(SearchRecipeFragment searchRecipeFragment);

    void inject(SearchProvider provider);

    void inject(QueryTextListener listener);

    void inject(RecipeAdapter adapter);
}
