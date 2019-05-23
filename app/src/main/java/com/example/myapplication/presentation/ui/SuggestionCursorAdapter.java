package com.example.myapplication.presentation.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.content_provider.SearchProvider;

import timber.log.Timber;

public class SuggestionCursorAdapter extends CursorAdapter {

    private SearchView searchView;
    private LayoutInflater layoutInflater;
    private SearchProvider.Type type;

    public SuggestionCursorAdapter(Context context, Cursor c, android.support.v7.widget.SearchView searchView, SearchProvider.Type type) {
        super(context, c, false);
        this.searchView = searchView;
        this.type = type;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.custom_find_view_popup_list,parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //TODO: костыль для корректоно отображения серч вью
        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        autoComplete.setDropDownWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        final String fullName = cursor.getString(cursor.getColumnIndexOrThrow("suggest_text_1"));
        ((TextView)view.findViewById(R.id.name)).setText(fullName);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SearchProvider.Type.INGREDIENT.equals(type))
                    searchView.setQuery(Utils.getText(searchView.getQuery(), fullName), false);
                else
                    searchView.setQuery(fullName,true);
            }
        });
    }
}
