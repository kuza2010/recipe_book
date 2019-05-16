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

public class SearchProvider extends ContentProvider {

    @Inject
    SearchServices services;

    private static String[] matrixColumns = {"_id", "suggest_text_1"};

    public static String AUTHORITY = "com.example.myapplication.content_provider.SearchProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/records");

    @Inject
    public SearchProvider(){
    }

    @Override
    public boolean onCreate() {
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Timber.d("Selection args: %s", (Object[]) selectionArgs);
        Timber.d("Selection: %s",selection);
        Timber.d("Last path segment: %s",uri.getLastPathSegment());

        return getData(Objects.requireNonNull(selectionArgs)[0]);
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
        if(services==null)
            BaseApp.getComponent().inject(this);

        MatrixCursor syggest = new MatrixCursor(matrixColumns);

        try {
            Timber.d("start getDishes name by part!");
            SearchedDishesName names = services.getDishesNameByPart(CACHE, partOfName, LIMIT_SUGGEST);
            Timber.d("get dishes names, size: %s", names.getDishes().size());

            Object[] mRow = new Object[2];
            int rowId = 0;

            for (String name : names.getDishes()) {
                mRow[0] = "" + rowId++;
                mRow[1] = name;

                Timber.d("added to matrix cursor row = %s", mRow);
                syggest.addRow(mRow);
            }
            Timber.d("suggest: %s",syggest);
            return syggest;
        } catch (Exception e) {
            Timber.e("some exception %s", e.getMessage());
            return null;
        }
    }
}
