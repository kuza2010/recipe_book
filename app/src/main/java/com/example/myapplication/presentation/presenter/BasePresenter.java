package com.example.myapplication.presentation.presenter;

public abstract class BasePresenter <View> {
    protected View view;

    public void bind(View view) {
        if (this.view != null)
            this.view = view;
    }
    public void unbind() {
        this.view = null;
    }

}
