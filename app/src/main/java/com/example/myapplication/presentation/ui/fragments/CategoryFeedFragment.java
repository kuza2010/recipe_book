package com.example.myapplication.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.CategoryServiceImpl;
import com.example.myapplication.framework.retrofit.model.Categories;

import javax.inject.Inject;

import timber.log.Timber;

public class CategoryFeedFragment extends Fragment {
    @Inject
    CategoryServiceImpl categoryService;

    Categories categories;

    public CategoryFeedFragment() {
        Timber.d("fragment constructor");
        BaseApp.getComponent().inject(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                feetch();
            }
        }).start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity_recepies, container, false);
        return view;
    }

    public void feetch() {
        Categories categories = categoryService.getCategories();
        Timber.e("categories download completed!");
    }

}
