package com.example.myapplication.presentation.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class BaseToolbarActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getSupportActionBar();
    }


    protected void setTitle(@NonNull String title) {
        if (!title.isEmpty() && toolbar != null)
            toolbar.setTitle(title);
    }
}
