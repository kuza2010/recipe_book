package com.example.myapplication.framework.dagger.modules;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.example.myapplication.RecepiesPreferences;
import com.example.myapplication.database.AppDataBase;

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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return new RecepiesPreferences(preferences, context);
    }

    @Provides
    @Singleton
    public AppDataBase provideDataBase(Context context){
        return Room
                .databaseBuilder(context,AppDataBase.class,"recipe_database")
                .build();
    }
}
