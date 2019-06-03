package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.framework.retrofit.model.login.SignIn;

import timber.log.Timber;

public class RecipesPreferences {
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String USER_ID = "id_user";
    public static final String LOGIN = "tryToLogin";
    public static final String PASS = "pass";
    public static final String REMEMBER_ME = "rmbme";
    public static final String IS_LOG_OUT = "logout";
    public static final String TOKEN = "token";

    private Context context;
    private SharedPreferences preferences;

    public RecipesPreferences(SharedPreferences preferences, Context context) {
        this.context = context;
        this.preferences = preferences;
    }

    public void saveSingle(String key, String value) {
        preferences.
                edit().
                putString(key, value).
                apply();
    }

    public void saveSingle(String key, boolean value) {
        Timber.d("saveSingle: key - %s, value - %s", key, value);

        preferences.
                edit().
                putBoolean(key, value).
                apply();
    }

    public String getValue(String key, String defaultV) {
        return preferences.getString(key, defaultV);
    }

    public boolean getValue(String key,boolean value) {
        return preferences.getBoolean(key, value);
    }

    public int getValue(String key,int value) {
        return preferences.getInt(key, value);
    }

    public void saveCredentials(SignIn user, String login, String password, boolean rememberMe) {
        preferences.
                edit().
                putString(TOKEN,user.getToken()).
                putString(NAME, user.getName()).
                putString(SURNAME, user.getSurname()).
                putInt(USER_ID, user.getUserId()).
                putString(LOGIN, login).
                putString(PASS, password).
                putBoolean(IS_LOG_OUT,false).
                putBoolean(REMEMBER_ME,rememberMe).
                apply();

        Timber.d("saveCredentials: details save!");
    }

    public void logOut() {
        preferences.
                edit().
                remove(TOKEN).
                remove(NAME).
                remove(SURNAME).
                remove(USER_ID).
                remove(LOGIN).
                remove(PASS).
                putBoolean(IS_LOG_OUT,true).
                apply();

        Timber.d("remove: details removed!");
    }
}
