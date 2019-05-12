package com.example.myapplication.framework.dagger.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.myapplication.RecepiesPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(@NonNull Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    public RecepiesPreferences providePreferences(Context context){
        return new RecepiesPreferences(context);
    }
}
