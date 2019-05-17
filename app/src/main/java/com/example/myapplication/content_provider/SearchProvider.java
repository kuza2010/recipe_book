package com.example.myapplication.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myapplication.BaseApp;
import com.example.myapplication.framework.retrofit.model.search.SearchedDishesName;
import com.example.myapplication.framework.retrofit.services.AWSException;
import com.example.myapplication.framework.retrofit.services.search.SearchService;
import com.example.myapplication.framework.retrofit.services.search.SearchServices;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;
import static com.example.myapplication.RecepiesConstant.LIMIT_SUGGEST;
import static com.example.myapplication.RecepiesConstant.NO_CACHE;

public class SearchProvider extends ContentProvider {
    @Inject
    SearchServices services;

    private static String[] matrixColumns = {"_id", "suggest_text_1"};

    public static String AUTHORITY = "com.example.myapplication.content_provider.SearchProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/records");

    @Inject
    public SearchProvider() {
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Timber.d("Selection args: %s", (Object[]) selectionArgs);
        Timber.d("Selection: %s", selection);
        Timber.d("Last path segment: %s", uri.getLastPathSegment());

        if (selectionArgs == null)
            return null;
        return getData(selectionArgs[0]);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private MatrixCursor getData(String partOfName) {
        if (services == null)
            BaseApp.getComponent().inject(this);

        if (partOfName == null || partOfName.length() < 3) {   //Deny request
            Timber.d("deny request to get dishes, partOfName length is not correct.");
            return null;
        }

        MatrixCursor suggest = new MatrixCursor(matrixColumns);

        try {
            Timber.d("start getDishes name by part!");
            SearchedDishesName names = services.getDishesNameByPart(CACHE, partOfName, LIMIT_SUGGEST);
            Timber.d("get dishes names, size: %s", names.getDishes().size());

            Object[] mRow = new Object[2];
            int rowId = 0;

            for (String name : names.getDishes()) {
                mRow[0] = "" + rowId++;
                mRow[1] = name;

                suggest.addRow(mRow);
            }
            Timber.d("suggest: %s", suggest);
            return suggest;
        } catch (Exception e) {
            Timber.e("some exception %s", e.getMessage());
            return null;
        }
    }
}
