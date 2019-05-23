package com.example.myapplication.presentation.ui;

import android.database.Cursor;
import android.support.v7.widget.SearchView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.Utils;
import com.example.myapplication.content_provider.SearchProvider;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.SUGGEST_LENGTH_MIN;
import static  com.example.myapplication.content_provider.SearchProvider.Type.INGREDIENT;
import static  com.example.myapplication.content_provider.SearchProvider.Type.RECIPE;

public class QueryTextListener implements SearchView.OnQueryTextListener {
    @Inject
    SearchProvider provider;

    private Listener listener;
    private SearchProvider.Type type;

    public QueryTextListener(Listener listener, SearchProvider.Type type) {
        BaseApp.getComponent().inject(this);
        this.listener = listener;
        this.type = type;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Timber.d("onQueryTextSubmit: %s", query);
        listener.onSubmit(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String suggestionPartText) {
        Timber.d("onQueryTextChange: new text is: %s", suggestionPartText);

        if (SearchProvider.Type.INGREDIENT.equals(type)) {
            String lastTextSegment = Utils.getLastSegmentText(suggestionPartText);
            Timber.d("onQueryTextChange: type is %s, last text segment %s", INGREDIENT, lastTextSegment);

            if (lastTextSegment.length() >= SUGGEST_LENGTH_MIN) {
                listener.showProgressbar();
                provider.getSuggestion(lastTextSegment, type, new SearchProvider.OnSuggestionCursorListener() {
                    @Override
                    public void onCursorReceived(Cursor cursor) {
                        listener.onCursorChanged(cursor);
                    }
                });
                return true;
            } else {
                Timber.d("onQueryTextChange: type is %s, last text segment %s -> SKIPPED suggestion", INGREDIENT, lastTextSegment);

                listener.onCursorChanged(null);
                return true;
            }
        } else if (suggestionPartText.length() >= SUGGEST_LENGTH_MIN && RECIPE.equals(type)) {
            Timber.d("onQueryTextChange: type is %s, last text segment %s", RECIPE, suggestionPartText);
            listener.showProgressbar();
            provider.getSuggestion(suggestionPartText, type, new SearchProvider.OnSuggestionCursorListener() {
                @Override
                public void onCursorReceived(Cursor cursor) {
                    listener.onCursorChanged(cursor);
                }
            });
            return true;
        }
        return false;
    }


    public interface Listener {
        void onCursorChanged(@Nullable Cursor cursor);

        void onSubmit(String toSearch);

        void showProgressbar();
    }
}
