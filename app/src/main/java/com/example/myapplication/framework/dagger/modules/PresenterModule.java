package com.example.myapplication.framework.dagger.modules;

import com.example.myapplication.framework.retrofit.services.recipe.RecipeServices;
import com.example.myapplication.presentation.presenter.login.LoginPresenter;
import com.example.myapplication.presentation.presenter.login.LoginPresenterImpl;
import com.example.myapplication.presentation.presenter.recipe.ExpandableRecipePresenter;
import com.example.myapplication.presentation.presenter.recipe.ExpandableRecipePresenterImpl;
import com.example.myapplication.presentation.presenter.recipe.RecipePresenter;
import com.example.myapplication.presentation.presenter.recipe.RecipePresenterImpl;
import com.example.myapplication.presentation.ui.NavigationToolbarHelper;
import com.example.myapplication.presentation.ui.NavigationToolbarHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    public NavigationToolbarHelper provideNavigationToolbarHelper() {
        return new NavigationToolbarHelperImpl();
    }

    @Provides
    @Singleton
    public LoginPresenter provideLoginPresenter() {
        return new LoginPresenterImpl();
    }


    @Provides
    @Singleton
    public RecipePresenter provideRecipePresenter(RecipeServices recipeService) {
        return new RecipePresenterImpl(recipeService);
    }

    @Provides
    @Singleton
    public ExpandableRecipePresenter provideExpandableRecipePresenter(RecipeServices recipeService) {
        return new ExpandableRecipePresenterImpl(recipeService);
    }
}
