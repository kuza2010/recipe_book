package com.example.myapplication.framework.dagger.modules;

import com.example.myapplication.presentation.presenter.LoginPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    public LoginPresenter provideLoginPresenter(){
        return new LoginPresenter();
    }
}
