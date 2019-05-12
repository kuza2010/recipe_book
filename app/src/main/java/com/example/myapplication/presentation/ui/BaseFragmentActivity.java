package com.example.myapplication.presentation.ui;

import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;

public abstract class BaseFragmentActivity extends AppCompatActivity {

    protected LinkedList<String> createdFragments = new LinkedList<>();

    protected boolean isFragmentCreated(String createdTag) {
        return createdFragments.contains(createdTag);
    }

    protected abstract void createFragment(String tag);

    protected abstract void navigateFragment(String navigable, String current);

    protected abstract void showFragment(String navigable, String current);
}
