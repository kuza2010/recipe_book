package com.example.myapplication.framework.dagger;

import com.example.myapplication.framework.dagger.modules.NetworkModule;
import com.example.myapplication.presentation.ui.LogInActivity;
import com.example.myapplication.framework.dagger.modules.ApplicationModule;
import com.example.myapplication.framework.dagger.modules.PresenterModule;
import com.example.myapplication.presentation.ui.MainActivity;
import com.example.myapplication.presentation.ui.fragments.CategoryFeedFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        PresenterModule.class,
        ApplicationModule.class,
        NetworkModule.class})
public interface AppComponent {

    void inject (MainActivity activity);
    void inject (LogInActivity activity);

    void inject(CategoryFeedFragment categoryFeedFragment);
}
