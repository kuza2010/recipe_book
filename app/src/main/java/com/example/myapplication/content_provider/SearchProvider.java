package com.example.myapplication.content_provider;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.myapplication.BaseApp;
import com.example.myapplication.Utils;
import com.example.myapplication.framework.retrofit.model.search.SearchedDishesName;
import com.example.myapplication.framework.retrofit.model.search.SearchedIngredientName;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.search.SearchServices;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.internal.Util;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;
import static com.example.myapplication.RecepiesConstant.LIMIT_SUGGEST;

@Singleton
public class SearchProvider {
    private final static String[] matrixColumns = {"_id", "suggest_text_1"};

    private SearchServices services;

    @Inject
    public SearchProvider(SearchServices services) {
        this.services = services;
    }

    public MatrixCursor getData(String partOfName) {
        if (services == null)
            BaseApp.getComponent().inject(this);

        if (partOfName == null || partOfName.length() < 3) {   //Deny request
            Timber.d("deny request to get dishes, partOfName length is not correct.");
            return null;
        }

        MatrixCursor suggest = new MatrixCursor(matrixColumns);

        try {
            Timber.d("start getDishes name by part!");
            SearchedDishesName names = services.getRecipesNameByPart(CACHE, partOfName, LIMIT_SUGGEST);
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

    public void getSuggestion(String partOfName, Type type, final OnSuggestionCursorListener onSuggestionCursorListener) {
        String namePart;
        if (Type.RECIPE.equals(type)) namePart = partOfName;
        else namePart = Utils.getLastSegmentText(partOfName);

        if (namePart == null || namePart.length() < 3) {   //Deny request
            Timber.d("getSuggestion: deny request to get dishes, partOfName length is not correct.");
            return;
        }

        services.cancelOrSkip();
        Timber.d("getSuggestion: type is %s",type);

        if (Type.RECIPE.equals(type)) {
            services.getRecipesNameByPart(CACHE, namePart, LIMIT_SUGGEST, new NetworkCallback<SearchedDishesName>() {
                @Override
                public void onResponse(SearchedDishesName body) {
                    Timber.i("onResponse: get dish size: %s", body.getDishes().size());
                    onSuggestionCursorListener.onCursorReceived(createCursor(body.getDishes()));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Timber.e("onFailure: message %s", throwable.getMessage());
                }
            });
        } else {
            Handler handler = new Handler();
            services.getIngredientNameByPart(CACHE, namePart, LIMIT_SUGGEST, new NetworkCallback<SearchedIngredientName>() {
                @Override
                public void onResponse(SearchedIngredientName body) {
                    Timber.i("onResponse: get ingredient size: %s", body.getIngredients().size());
                    onSuggestionCursorListener.onCursorReceived(createCursor(body.getIngredients()));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Timber.e("onFailure: message %s", throwable.getMessage());
                }
            });
        }
    }

    private Cursor createCursor(List<String> names) {
        if(names.isEmpty())
            return null;

        MatrixCursor suggest = new MatrixCursor(matrixColumns);

        Object[] mRow = new Object[2];
        int rowId = 0;

        for (String name : names) {
            mRow[0] = "" + rowId++;
            mRow[1] = name;
            suggest.addRow(mRow);
        }
        Timber.d("create Cursor: %s", suggest);

        return suggest;
    }

    /**
     * This interface using to callback when
     * receiving a cursor
     */
    public interface OnSuggestionCursorListener {

        /**
         * Called when cursor received
         *
         * @param cursor - cursor with names
         *        null - no matches
         */
        void onCursorReceived(@Nullable Cursor cursor);
    }

    public enum Type{
        INGREDIENT, RECIPE
    }
}
