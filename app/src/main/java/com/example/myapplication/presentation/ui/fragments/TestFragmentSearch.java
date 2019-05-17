package com.example.myapplication.presentation.ui.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

import timber.log.Timber;

public class TestFragmentSearch extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        SearchView searchView = view.findViewById(R.id.search_view);

        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
        return view;
    }

    @Override
    public void onStart() {
        Timber.d("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Timber.d("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Timber.d("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Timber.d("onStop");
        super.onStop();
    }
}
