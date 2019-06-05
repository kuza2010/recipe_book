package com.example.myapplication.framework.dagger.modules;

import com.example.myapplication.framework.retrofit.services.login.LoginServices;
import com.example.myapplication.framework.retrofit.services.recipe.RecipeServices;
import com.example.myapplication.presentation.presenter.login.LoginPresenter;
import com.example.myapplication.presentation.presenter.login.LoginPresenterImpl;
import com.example.myapplication.presentation.presenter.recipe.general_recipe.GeneralRecipePresenter;
import com.example.myapplication.presentation.presenter.recipe.general_recipe.GeneralRecipePresenterImpl;
import com.example.myapplication.presentation.presenter.recipe.RecipePresenter;
import com.example.myapplication.presentation.presenter.recipe.RecipePresenterImpl;
import com.example.myapplication.presentation.presenter.registration.RegistrationPresenter;
import com.example.myapplication.presentation.presenter.registration.RegistrationPresenterImpl;
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
    public RecipePresenter provideRecipePresenter(RecipeServices recipeService) {
        return new RecipePresenterImpl(recipeService);
    }

    @Provides
    @Singleton
    public GeneralRecipePresenter provideExpandableRecipePresenter(RecipeServices recipeService) {
        return new GeneralRecipePresenterImpl(recipeService);
    }

    @Provides
    @Singleton
    public RegistrationPresenter provideRegistrationPresenter(LoginServices services) {
        return new RegistrationPresenterImpl(services);
    }

    @Provides
    @Singleton
    public LoginPresenter provideLoginPresenter() {
        return new LoginPresenterImpl();
    }
}
