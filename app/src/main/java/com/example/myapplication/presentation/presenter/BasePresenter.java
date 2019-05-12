package com.example.myapplication.presentation.presenter;

public interface BasePresenter<View> {
    void bind(View view);
    void unbind();
}
