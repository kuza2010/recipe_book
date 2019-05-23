package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.TypedValue;

import com.example.myapplication.framework.retrofit.model.recipe.Recipe;
import com.example.myapplication.framework.retrofit.model.recipe.Recipes;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.SPLIT_CHAR;
import static com.example.myapplication.presentation.ui.BaseBottomNavigationActivity.ERROR;
import static com.example.myapplication.presentation.ui.BaseBottomNavigationActivity.PROFILE;
import static com.example.myapplication.presentation.ui.BaseBottomNavigationActivity.RECIPES;
import static com.example.myapplication.presentation.ui.BaseBottomNavigationActivity.SEARCH;

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

    public static int getResId(String title) {
        switch (title) {
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

    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static List<Recipe> getNewRecipes(@NonNull List<Recipe> oldList, @NonNull List<Recipe> newList) {
        List<Recipe> different = new ArrayList<>();

        if (oldList.isEmpty()) {
            Timber.w("oldList is empty! Added %s record", newList.size());
            return newList;
        }

        for (Recipe each : newList) {
            if (!oldList.contains(each))
                different.add(each);
        }
        Timber.w("into old list added %s records", different.size());
        return different;
    }

    public static String getText(CharSequence charSequence, @NonNull String append) {
        String old = charSequence.toString();

        if (old.isEmpty()) {
            Timber.d("getText: old string is empty, return %s", append);
        }

        if (!old.contains(SPLIT_CHAR)) {
            return append + SPLIT_CHAR;
        }


        int i = old.lastIndexOf(SPLIT_CHAR);
        StringBuilder builder = new StringBuilder(old.substring(0, i + 1));

        builder.append(append);
        builder.append(SPLIT_CHAR);

        Timber.d("getText: new string is %s", builder.toString());

        return builder.toString();
    }

    public static String getLastSegmentText(@NonNull String searchedString) {
        if(!searchedString.contains(SPLIT_CHAR))
            return searchedString;

        int i = searchedString.lastIndexOf(SPLIT_CHAR);

        return searchedString.substring(i+1);
    }
}
