package com.example.myapplication;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.myapplication.framework.dagger.AppComponent;
import com.example.myapplication.framework.dagger.DaggerAppComponent;
import com.example.myapplication.framework.dagger.modules.ApplicationModule;
import com.example.myapplication.framework.dagger.modules.PresenterModule;

import timber.log.Timber;

public class BaseApp extends Application {

    public static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .presenterModule(new PresenterModule())
                .build();
    }
}
