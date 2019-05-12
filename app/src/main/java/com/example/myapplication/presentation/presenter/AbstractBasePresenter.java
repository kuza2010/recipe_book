package com.example.myapplication.presentation.presenter;

public abstract class AbstractBasePresenter<View> implements BasePresenter<View> {
    protected View view;

    public void bind(View view) {
        this.view = view;
    }

    public void unbind() {
        this.view = null;
    }
}
