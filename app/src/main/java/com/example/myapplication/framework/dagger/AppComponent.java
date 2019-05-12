package com.example.myapplication.framework.dagger;

import com.example.myapplication.framework.dagger.modules.ApplicationModule;
import com.example.myapplication.framework.dagger.modules.NetworkModule;
import com.example.myapplication.framework.dagger.modules.PresenterModule;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;
import com.example.myapplication.presentation.ui.fragments.CategoryFeedFragment;
import com.example.myapplication.presentation.ui.login.LogInActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PresenterModule.class,
        ApplicationModule.class,
        NetworkModule.class})
public interface AppComponent {

    void inject (BaseToolbarActivity activity);
    void inject (LogInActivity activity);

    void inject(CategoryFeedFragment categoryFeedFragment);
}
