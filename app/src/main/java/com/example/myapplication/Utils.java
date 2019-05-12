package com.example.myapplication;

import static com.example.myapplication.presentation.ui.BaseToolbarActivity.ERROR;
import static com.example.myapplication.presentation.ui.BaseToolbarActivity.PROFILE;
import static com.example.myapplication.presentation.ui.BaseToolbarActivity.RECIPES;
import static com.example.myapplication.presentation.ui.BaseToolbarActivity.SEARCH;

public class Utils {

    public static String getStringFromResId(int id) {
        switch (id) {
            case R.id.navigation_recipes:
                return RECIPES;
            case R.id.navigation_serach:
                return SEARCH;
            case R.id.navigation_profile:
                return PROFILE;
            default:
                return ERROR;
        }
    }

    public static int getResId(String title){
        switch (title){
            case RECIPES:
                return R.id.navigation_recipes;
            case SEARCH:
                return R.id.navigation_serach;
            case PROFILE:
                return R.id.navigation_profile;
            default:
                return -1;  //ERROR
        }
    }
}
