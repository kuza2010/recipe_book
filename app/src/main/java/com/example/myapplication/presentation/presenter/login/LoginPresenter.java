package com.example.myapplication.presentation.presenter.login;

import com.example.myapplication.presentation.presenter.BasePresenter;

public interface LoginPresenter<T> extends BasePresenter<T> {
    void login();

    /**
     * Using for callback in activity.
     * Any activity was used LoginPresenter
     * has implemented this interface.
     */
    interface LoginContractView {
        void login();
    }
}
