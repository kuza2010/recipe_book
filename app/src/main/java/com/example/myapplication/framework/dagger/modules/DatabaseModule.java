package com.example.myapplication.framework.dagger.modules;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.myapplication.database.AppDataBase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    
    @Provides
    @Singleton
    public AppDataBase provideDataBase(Context context){
        return Room
                .databaseBuilder(context,AppDataBase.class,"recipe_database")
                .build();
    }
}
