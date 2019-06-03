package com.example.myapplication.presentation.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

public abstract class BaseToolbarActivity extends AppCompatActivity {

    protected ActionBar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getSupportActionBar();
    }


    protected void setTitle(@NonNull String title) {
        if (!title.isEmpty() && toolbar != null)
            toolbar.setTitle(title);
    }

    public void popupToast(String message, int time){
        Toast toast = Toast.makeText(this,message,time);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }


    public void hideToolbar(){
        toolbar.hide();
    }

    public void showtoolbar(){
        toolbar.show();
    }
}
