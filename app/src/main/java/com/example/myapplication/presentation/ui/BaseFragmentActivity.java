package com.example.myapplication.presentation.ui;

import java.util.LinkedList;

public abstract class BaseFragmentActivity extends BaseToolbarActivity {

    protected LinkedList<String> createdFragments = new LinkedList<>();

    protected boolean isFragmentCreated(String createdTag) {
        return createdFragments.contains(createdTag);
    }

    protected abstract void createFragment(String tag);

    protected abstract void navigateFragment(String navigable, String current);

    protected abstract void showFragment(String navigable, String current);
}
