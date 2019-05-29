package com.example.myapplication.presentation.ui;

import android.database.Cursor;
import android.os.Handler;
import android.support.v7.widget.SearchView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.content_provider.SearchProvider;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.DELAY_CHARACTER_ENTERED;

public class QueryTextListener implements SearchView.OnQueryTextListener {
    @Inject
    SearchProvider provider;

    private Listener listener;
    private SearchProvider.Type type;
    private Handler handler = new Handler();
    private SearchDelayRunnable runnable;

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

        if (suggestionPartText.length() >= 3) {
            if (runnable != null)
                handler.removeCallbacks(runnable);
            runnable = new SearchDelayRunnable(suggestionPartText);
            handler.postDelayed(runnable, DELAY_CHARACTER_ENTERED);
            return true;
        }
        return false;
    }

    class SearchDelayRunnable implements Runnable {

        private String query;

        SearchDelayRunnable(String query) {
            this.query = query;
        }

        @Override
        public void run() {
            listener.showProgressbar();

            provider.getSuggestion(query, type, new SearchProvider.OnSuggestionCursorListener() {
                @Override
                public void onCursorReceived(Cursor cursor) {
                    listener.onCursorChanged(cursor);
                }
            });
        }
    }

    public interface Listener{
        void onCursorChanged(@Nullable Cursor cursor);
        void onSubmit(String toSearch);
        void showProgressbar();
    }
}
