package com.example.myapplication.presentation.presenter.login;

import com.example.myapplication.presentation.presenter.BasePresenter;

public interface LoginPresenter<T> extends BasePresenter<T> {
    void tryToLogin(String login, String password,boolean rememberMe);
    void checkRememberMeCredentials();

    /**
     * Using for callback in activity.
     * Any activity was used LoginPresenter
     * has implemented this interface.
     */
    interface LoginContractView {
        void login();
        void initBundle(boolean rmbme,boolean isLogOut,String login, String password);
        void showMessage(String message);
        void showSignInLayout();
    }
}
