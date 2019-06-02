package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class RecepiesPreferences {

    private Context context;

    private SharedPreferences preferences;

    public RecepiesPreferences(SharedPreferences preferences, Context context) {
        this.context = context;
    }
}
