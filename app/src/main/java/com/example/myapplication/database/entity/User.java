package com.example.myapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    public long id;

    public String name;

    public String surname;

    public String login;
}
