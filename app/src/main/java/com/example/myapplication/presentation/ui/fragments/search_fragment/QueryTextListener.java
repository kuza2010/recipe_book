package com.example.myapplication.presentation.ui.fragments.search_fragment;

import android.database.Cursor;
import android.support.v7.widget.SearchView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.content_provider.SearchProvider;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import timber.log.Timber;

public class QueryTextListener implements SearchView.OnQueryTextListener {
    @Inject
    SearchProvider provider;

    private Listener listener;

    public QueryTextListener(Listener listener) {
        this.listener = listener;
        BaseApp.getComponent().inject(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Timber.d("onQueryTextSubmit: %s", s);
        listener.onSubmit(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String suggestionPartText) {
        Timber.d("onQueryTextChange: new text is: %s", suggestionPartText);

        provider.getSuggestion(suggestionPartText, new SearchProvider.OnSuggestionCursorListener() {
            @Override
            public void onCursorReceived(Cursor cursor) {
                listener.onCursorChanged(cursor);
            }
        });
        return true;
    }

    interface Listener{
        void onCursorChanged(@Nullable Cursor cursor);
        void onSubmit(String toSearch);
        void clearSearchView();
    }
}
