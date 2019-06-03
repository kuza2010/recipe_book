package com.example.myapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.myapplication.database.dao.UserDao;
import com.example.myapplication.database.entity.User;

@Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userdao();
}
