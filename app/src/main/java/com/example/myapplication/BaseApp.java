package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.framework.dagger.AppComponent;
import com.example.myapplication.framework.dagger.DaggerAppComponent;
import com.example.myapplication.framework.dagger.modules.ApplicationModule;
import com.example.myapplication.framework.dagger.modules.PresenterModule;

public class BaseApp extends Application {

    public static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .presenterModule(new PresenterModule())
                .build();
    }
}
